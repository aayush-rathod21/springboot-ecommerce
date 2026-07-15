package com.aayush.ecommerse02.repo;

import com.aayush.ecommerse02.model.Cart;
import com.aayush.ecommerse02.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Productrepo extends JpaRepository<Product, Integer> {

    List<Product> findByCategory(String category);
}
