package com.aayush.ecommerse02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController   // Used to tell the code that this is a controller
public class HomeController {

    @GetMapping("/")   // Get requests
    public String home(){
        return "Welcome to our Ecommerce website";
    }

    @GetMapping("/about")
    public String about(){
        return "This is project 2 with external database";
            }
    @GetMapping("/name")
    public String name(){
        return "Aayush";
    }
    
    
    
}
