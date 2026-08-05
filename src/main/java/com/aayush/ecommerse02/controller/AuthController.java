package com.aayush.ecommerse02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aayush.ecommerse02.dto.UserRegistrationRequest;
import com.aayush.ecommerse02.service.UserService;

@RestController
public class AuthController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest ex){

        userService.registerUser(ex);

        return new ResponseEntity<>("User registered",HttpStatus.CREATED);
    }

    @GetMapping("/login")
    

}
