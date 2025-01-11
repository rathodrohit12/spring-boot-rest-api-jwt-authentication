package com.web.filter;

import com.web.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public JwtAuthFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("doFilterInternal****************************************");
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtTokenService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtTokenService.isTokenValid(token, username)) {
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
                }
            }
        }

        chain.doFilter(request, response);
    }
}
