package com.aayush.ecommerse02.dto;

import com.aayush.ecommerse02.model.Product;

public class ProductMapper {
    
    public static ProductResponseDTO mapToProductResponseDTO(Product product){

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setBrand(product.getBrand());
        dto.setCategory(product.getCategory());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        

        return dto;
    }
}
