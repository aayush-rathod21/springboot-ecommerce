package com.aayush.ecommerse02.repo;

import com.aayush.ecommerse02.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
    public interface Cartrepo extends JpaRepository<Cart,Integer> { 
}
