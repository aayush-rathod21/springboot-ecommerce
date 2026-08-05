package com.aayush.ecommerse02.controller;

import com.aayush.ecommerse02.exception.ResourceNotFoundException;
import com.aayush.ecommerse02.model.Product;
import com.aayush.ecommerse02.service.Productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private Productservice service;

    @GetMapping("/products")
    public Page<Product> getallproducts(Pageable pageable){
        return service.getallproducts(pageable);
    }
    
    @GetMapping("/products/search/")
    public Page<Product> searchProducts(@RequestParam(required = false) String name, 
                                        @RequestParam(required = false) String category,
                                        @RequestParam(required = false) String brand,
                                        @RequestParam(required = false) BigDecimal minprice,
                                        @RequestParam(required = false) BigDecimal maxprice,
                                     Pageable pageable
                                ){
        return service.searchProducts(name,category,brand,minprice,maxprice, pageable);
    }

    @GetMapping("/products/{id}")
    public Product getbyID(@PathVariable int id){
        
        Optional<Product> prod = service.getbyID(id);
        if(prod.isEmpty()) throw new ResourceNotFoundException("Product not available");
        return prod.get();
    }

}

