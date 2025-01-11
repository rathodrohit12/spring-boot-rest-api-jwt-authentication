package com.web.exception;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, Object> errors = new HashMap<>();
	        errors.put("timestamp", LocalDateTime.now());
	        errors.put("status", HttpStatus.BAD_REQUEST.value());
	        errors.put("errors", ex.getBindingResult()
	                                .getFieldErrors()
	                                .stream()
	                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                                .collect(Collectors.toList()));
	        return errors;
	    }
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Handle generic exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage()+"\n");
    }
}
