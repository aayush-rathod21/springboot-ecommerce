package com.aayush.ecommerse02.controller;

import com.aayush.ecommerse02.model.Cart;
import com.aayush.ecommerse02.model.Product;
import com.aayush.ecommerse02.service.Productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class Maincontroller {

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
    

