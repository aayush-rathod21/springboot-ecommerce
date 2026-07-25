package com.aayush.ecommerse02.service;

import com.aayush.ecommerse02.model.Product;
import com.aayush.ecommerse02.model.Cart;
import com.aayush.ecommerse02.repo.Cartrepo;
import com.aayush.ecommerse02.repo.Productrepo;
import com.aayush.ecommerse02.repo.Cartrepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class Productservice {

    @Autowired
    private Productrepo repo;

    @Autowired
    private Cartrepo cartrepo;

    // Helper method to get the current logged in user
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public Page<Product> getallproducts(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<Product> getbyID(int id){
        return repo.findById(id);
    }

    public Page<Product> findByCategory(String category, Pageable pageable) {
        return repo.findByCategory_Name(category, pageable);
    }

    public boolean addToCart(int id) {

        Optional<Product> product = repo.findById(id);

        if(product.isPresent()){
            Product p = product.get();

        Cart cart = new Cart();

        cart.setUsername(getCurrentUsername()); // Tie this cart item to the logged in user
        cart.setMain_id(p.getId());
        cart.setName(p.getName());
        cart.setDescription(p.getDescription());
        cart.setBrand(p.getBrand());
        cart.setPrice(p.getPrice());
        cart.setCategory(p.getCategory() != null ? p.getCategory().getName() : null);
        cart.setReleaseDate(p.getReleaseDate());
        cart.setAvailable(p.isAvailable());
        cart.setQuantity(1);

        cartrepo.save(cart);
            return true;
    }
        return false;
    }

    public boolean removeFromCart(int id){
        // Make sure the item actually exists before deleting
        if(cartrepo.existsById(id)){
            cartrepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Cart> getCartItems() {
        // Only return the cart items for the currently logged in user!
        List<Cart> cartItems = cartrepo.findByUsername(getCurrentUsername());

        if(cartItems.isEmpty()){
            return null;
        }
        else return cartItems;
    }

    @Transactional
    public void clearCart() {
        // Only delete the cart items for the currently logged in user!
        cartrepo.deleteByUsername(getCurrentUsername());
    }

    public Page<Product> searchProducts(String name, String category, String brand, BigDecimal minprice, BigDecimal maxprice, Pageable pageable) {
        return repo.searchProducts(name, category, brand, minprice, maxprice, pageable);
    }
}

