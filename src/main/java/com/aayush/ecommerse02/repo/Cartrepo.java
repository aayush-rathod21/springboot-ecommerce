package com.aayush.ecommerse02.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aayush.ecommerse02.model.Cart;

@Repository
public interface Cartrepo extends JpaRepository<Cart,Integer>{

}


