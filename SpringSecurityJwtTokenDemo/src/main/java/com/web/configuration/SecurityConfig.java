package com.web.configuration;

import static org.springframework.security.config.Customizer.*;

import com.web.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.web.service.MyUserDetailService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public UserDetailsService userDetailsService()
	{	
	    return new  MyUserDetailService();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/base/register","/base/token", "/base/validate")
                        .permitAll()
                        .requestMatchers("/base/**").authenticated()
                                .anyRequest().authenticated()
                )
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults())
                .build();
    }
	
	
	 @Bean
	    public AuthenticationManager authenticationManager(
	                                 AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }
	
}
