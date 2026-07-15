package com.aayush.ecommerse02.service;

import com.aayush.ecommerse02.model.Product;
import com.aayush.ecommerse02.model.Cart;
import com.aayush.ecommerse02.repo.Cartrepo;
import com.aayush.ecommerse02.repo.Productrepo;
import com.aayush.ecommerse02.repo.Cartrepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class Productservice {

    @Autowired
    private Productrepo repo;

    @Autowired
    private Cartrepo cartrepo;

    public List<Product> getallproducts() {
        return repo.findAll();
    }

    public Optional<Product> getbyID(int id){
        return repo.findById(id);
    }

    public List<Product> findByCategory(String category) {
        return repo.findByCategory(category);
    }

    public boolean addToCart(int id) {

        Optional<Product> product = repo.findById(id);

        if(product.isPresent()){
            Product p = product.get();

        Cart cart = new Cart();

        cart.setMain_id(p.getId());
        cart.setName(p.getName());
        cart.setDescription(p.getDescription());
        cart.setBrand(p.getBrand());
        cart.setPrice(p.getPrice());
        cart.setCategory(p.getCategory());
        cart.setReleaseDate(p.getReleaseDate());
        cart.setAvailable(p.isAvailable());
        cart.setQuantity(1);


        cartrepo.save(cart);
            return true;
    }
        return false;
    }

    public boolean removeFromCart(int id){
        if(cartrepo.existsById(id)){
            cartrepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Cart> getCartItems() {

        List<Cart> cartItems = cartrepo.findAll();

        if(cartItems.isEmpty()){
            return null;
        }
        else return cartItems;
    }

    public void clearCart() {
        cartrepo.deleteAll();
    }

}
