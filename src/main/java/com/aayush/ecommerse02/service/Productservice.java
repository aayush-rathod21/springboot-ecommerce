package com.aayush.ecommerse02.service;

import com.aayush.ecommerse02.model.Product;
import com.aayush.ecommerse02.repo.Productrepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class Productservice {

    @Autowired
    private Productrepo repo;

    public Page<Product> getallproducts(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<Product> getbyID(int id){
        return repo.findById(id);
    }

    public Page<Product> searchProducts(String name, String category, String brand, BigDecimal minprice, BigDecimal maxprice, Pageable pageable) {
        return repo.searchProducts(name, category, brand, minprice, maxprice, pageable);
    }
}

