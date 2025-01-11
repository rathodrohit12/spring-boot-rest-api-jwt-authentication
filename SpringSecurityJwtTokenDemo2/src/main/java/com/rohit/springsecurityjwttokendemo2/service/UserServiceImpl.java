package com.rohit.springsecurityjwttokendemo2.service;

import com.rohit.springsecurityjwttokendemo2.entity.User;
import com.rohit.springsecurityjwttokendemo2.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthService jwtAuthService;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder
            , AuthenticationManager authenticationManager, JwtAuthService jwtAuthService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtAuthService = jwtAuthService;
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User loginUser(User user) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authenticate.isAuthenticated()) {

            return user;
        }
        return null;
    }

    @Override
    public String generateToken(User user) {
        return jwtAuthService.generateToken(user.getEmail());
    }

    @Override
    public void validateToken(String token) {
         jwtAuthService.validateToken(token);
    }
}
