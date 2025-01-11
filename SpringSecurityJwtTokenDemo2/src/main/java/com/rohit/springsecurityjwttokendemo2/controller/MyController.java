package com.rohit.springsecurityjwttokendemo2.controller;

import com.rohit.springsecurityjwttokendemo2.entity.User;
import com.rohit.springsecurityjwttokendemo2.service.JwtAuthService;
import com.rohit.springsecurityjwttokendemo2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyController {


    private final UserService userService;

    public MyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomepage() {
        return "homepage";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }



    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        User user1 = userService.registerUser(user);
        if (user1 != null) {
            return "User registered successfully";
        }
        return "User registration failed";
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        User user1 = userService.loginUser(user);
        if (user1 != null) {
            return userService.generateToken(user1);
        }
        return "User login failed";
    }


    @PostMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return "Token validated successfully";
    }






}


