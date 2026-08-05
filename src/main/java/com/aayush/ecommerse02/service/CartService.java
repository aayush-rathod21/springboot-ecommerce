package com.aayush.ecommerse02.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aayush.ecommerse02.exception.ResourceNotFoundException;
import com.aayush.ecommerse02.model.Cart;
import com.aayush.ecommerse02.model.Product;
import com.aayush.ecommerse02.repo.Cartrepo;
import com.aayush.ecommerse02.repo.Productrepo;

@Service
public class CartService {

    @Autowired
    private Cartrepo cartrepo;
    
    @Autowired
    private Productrepo productrepo;

    public void addToCart(int productid){

        Product prod = productrepo.findById(productid).
        orElseThrow(() -> new ResourceNotFoundException("Product doesnt exist cannot add to cart"));

        Cart cart = new Cart();
        cart.setProductid(prod.getId());
        cart.setName(prod.getName());
        cart.setPrice(prod.getPrice());
        cart.setQuantity(1);

        cartrepo.save(cart);
    }

    public List<Cart> getCartItems(){
        return cartrepo.findAll();
    }

    public void removeFromCart(int cartID){
        if(!cartrepo.existsById(cartID)){
            throw new ResourceNotFoundException("Cart item not found");
        }
        cartrepo.deleteById(cartID);
    }

    public void clearCart(){
        cartrepo.deleteAll();
    }
}
