package com.aayush.ecommerse02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aayush.ecommerse02.model.Cart;
import com.aayush.ecommerse02.service.CartService;
import com.aayush.ecommerse02.service.Productservice;

@RestController
@RequestMapping("/cart")
public class CartController {

    
    @Autowired
    private Productservice productservice;

    @Autowired
    private CartService cartService;

    @PostMapping("/addtocart/{id}")
    public void addToCart(@PathVariable int id){
        cartService.addToCart(id);
    }

    @DeleteMapping("/removefromcart/{id}")
    public void removeFromCart(@PathVariable int id){
        cartService.removeFromCart(id);
    }

    @GetMapping("/view")
    public List<Cart> getCartItems(){
        return cartService.getCartItems();
    }

    @DeleteMapping("/delete")
    public void clearCart(){
        cartService.clearCart();
    }
}
