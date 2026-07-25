package com.aayush.ecommerse02.controller;

import com.aayush.ecommerse02.model.Cart;
import com.aayush.ecommerse02.model.Product;
import com.aayush.ecommerse02.service.Productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class Maincontroller {

    @Autowired
    private Productservice service;

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        // Beginner-friendly validation handling
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            Product savedProduct = service.addProduct(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            // Beginner-friendly generic exception handling
            return new ResponseEntity<>("Failed to create product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
    public Optional<Product> getbyID(@PathVariable int id){
        return service.getbyID(id);
    }

    @GetMapping("/products/addtocart/{id}")
    public boolean addToCart(@PathVariable int id){
        return service.addToCart(id);
    }

    @GetMapping("/products/removefromcart/{id}")
    public boolean removeFromCart(@PathVariable int id){
        return service.removeFromCart(id);
    }
    
    @GetMapping("/products/cart")
    public List<Cart> getCartItems(){
        return service.getCartItems();
    }

    @GetMapping("/products/clearcart")
    public String clearCart(){
        service.clearCart();
        return "Cart cleared successfully.";
    }

}
    

