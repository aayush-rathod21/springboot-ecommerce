package com.aayush.ecommerse02.exception;

public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String message){
    super(message);
    }
}
