package com.rohit.springsecurityjwttokendemo2.service;

import com.rohit.springsecurityjwttokendemo2.entity.User;

import java.util.Optional;

public interface UserService {

    public User registerUser(User user);
    public User loginUser(User user);
    public String generateToken(User user);
    public void validateToken(String token);


}
