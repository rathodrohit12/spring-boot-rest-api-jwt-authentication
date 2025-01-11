package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.web.entity.UserEntity;
import com.web.repository.UserRepo;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByEmail(username);

        return user.map(MyUserService::new)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    }
}

