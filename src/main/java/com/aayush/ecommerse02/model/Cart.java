package com.aayush.ecommerse02.model;

import java.math.BigDecimal;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    int productid;
    String name;
    BigDecimal price;
    int quantity;

}
