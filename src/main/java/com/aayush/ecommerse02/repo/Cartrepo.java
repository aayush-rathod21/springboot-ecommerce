package com.aayush.ecommerse02.repo;

import com.aayush.ecommerse02.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cartrepo extends JpaRepository<Cart, Integer> {
    List<Cart> findByUsername(String username);
    void deleteByUsername(String username);
}
