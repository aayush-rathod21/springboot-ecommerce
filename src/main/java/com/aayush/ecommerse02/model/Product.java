package com.aayush.ecommerse02.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private int id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;
    
    private String description;
    
    @NotBlank(message = "Brand is required")
    private String brand;
    
    @Positive(message = "Price must be a positive value")
    private BigDecimal price;
    
    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "category_id")
    private Category category;
    
    private LocalDate releaseDate;
    private boolean available;
    private int quantity;
}
