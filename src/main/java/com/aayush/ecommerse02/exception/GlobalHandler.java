package com.aayush.ecommerse02.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aayush.ecommerse02.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalHandler {

    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse ResourceNotFoundException(ResourceNotFoundException ex){

        ErrorResponse err = new ErrorResponse();
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setMessage(ex.getMessage());

        return err;
    }

    @ResponseStatus(HttpStatus.IM_USED)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse UserAlreadyExistsException(UserAlreadyExistsException ex){

        ErrorResponse err = new ErrorResponse();
        err.setStatus(HttpStatus.IM_USED.value());
        err.setMessage(ex.getMessage());
        return err;

    }
}

