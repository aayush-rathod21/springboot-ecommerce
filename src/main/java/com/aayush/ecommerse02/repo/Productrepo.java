package com.aayush.ecommerse02.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aayush.ecommerse02.model.Product;

@Repository
public interface Productrepo extends JpaRepository<Product, Integer> {

    Page<Product> findByCategory(String category, Pageable pageable);



    @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:category IS NULL OR LOWER(p.category) = LOWER(:category)) AND " +
           "(:brand IS NULL OR LOWER(p.brand) = LOWER(:brand)) AND " +
           "(:minprice IS NULL OR p.price >= :minprice) AND " +
           "(:maxprice IS NULL OR p.price <= :maxprice)")
           
    Page<Product> searchProducts(
            @Param("name") String name,
            @Param("category") String category,
            @Param("brand") String brand,
            @Param("minprice") BigDecimal minprice,
            @Param("maxprice") BigDecimal maxprice,
            Pageable pageable
    );
}