package com.aayush.ecommerse02.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDTO {
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
}
