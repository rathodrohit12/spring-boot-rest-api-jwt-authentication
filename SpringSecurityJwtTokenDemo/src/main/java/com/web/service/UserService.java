package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.entity.UserEntity;
import com.web.repository.UserRepo;

@Service
public class UserService {

	private JwtTokenService jwtTokenService;
    private UserRepo repo;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepo repo, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
		this.jwtTokenService = jwtTokenService;
    }

    public UserEntity registerUser(UserEntity entity) {
        entity.setPass(passwordEncoder.encode(entity.getPass()));
        return repo.save(entity);
    }


    public String generateToken(String email) {

        return jwtTokenService.generateToken(email);

    }

    public void validateToken(String token) {
		jwtTokenService.validateToken(token);

    }

}
