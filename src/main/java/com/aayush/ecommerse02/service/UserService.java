package com.aayush.ecommerse02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aayush.ecommerse02.dto.UserRegistrationRequest;
import com.aayush.ecommerse02.exception.UserAlreadyExistsException;
import com.aayush.ecommerse02.model.User;
import com.aayush.ecommerse02.repo.UserRepo;


@Service
public class UserService {
    
    @Autowired
    private UserRepo UserRepo;
    
    @Autowired
    private PasswordEncoder PasswordEncoder;

    public User registerUser(UserRegistrationRequest request){

        User newUser = new User();
        if(UserRepo.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("User already exists");
        }
        else{
            
            newUser.setEmail(request.getEmail());
            newUser.setPassword(PasswordEncoder.encode(request.getPassword()));
        } 
        return UserRepo.save(newUser);
    }

}
