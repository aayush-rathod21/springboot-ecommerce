package com.aayush.ecommerse02.dto;

import lombok.Data;

@Data
public class ErrorResponse {

    private int status;
    private String message;

}
