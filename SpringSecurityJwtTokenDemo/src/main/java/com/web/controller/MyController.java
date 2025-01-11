package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.web.entity.UserEntity;
import com.web.service.UserService;

@RestController
@RequestMapping(value = "/base")
public class MyController {
	
UserService userServer;
	
MyController(UserService userServer)
	{
		this.userServer=userServer;
	}


@Autowired
private AuthenticationManager authenticationManager;
	


	@GetMapping(value = "/home")
	public String home() {
		
		return "successfully";
		
    
	}



      
	@PostMapping(value = "/register" )
	public ResponseEntity<UserEntity> userRegister(@RequestBody UserEntity entity)
	{
		userServer.registerUser(entity);	
		return new ResponseEntity<UserEntity>(HttpStatus.CREATED);		
		
	}
	
	@PostMapping(value = "/token")
	public String getToken(@RequestBody UserEntity entity)
	{
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                entity.getEmail(), entity.getPass()));

	        //SecurityContextHolder.getContext().setAuthentication(authentication);
	        if (authentication.isAuthenticated()) {

                return userServer.generateToken(entity.getEmail());
			}else {
				 throw new UsernameNotFoundException("Invalid username and password");
			}

	}


	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token)
	{
		userServer.validateToken(token);
		return "token is valid";
	}




}

