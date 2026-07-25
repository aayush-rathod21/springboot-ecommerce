package com.aayush.ecommerse02.model;

import lombok.Data;

// Simple DTO just for logging in
@Data
public class LoginRequest {
    private String username;
    private String password;
}
