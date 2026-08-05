package com.aayush.ecommerse02.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private int id;
    private String name;
    private String description;

    private String brand;
    private String category;

    private BigDecimal price;
    private LocalDate releaseDate;
    private boolean available;
    private int quantity;
}
