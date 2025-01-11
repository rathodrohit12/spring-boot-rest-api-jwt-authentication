package com.rohit.springsecurityjwttokendemo2.service;

import com.rohit.springsecurityjwttokendemo2.entity.User;
import com.rohit.springsecurityjwttokendemo2.repo.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private  UserRepo userRepo;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(username);
        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    }
}

