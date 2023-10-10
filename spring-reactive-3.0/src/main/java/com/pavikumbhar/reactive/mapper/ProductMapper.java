package com.pavikumbhar.reactive.mapper;

import com.pavikumbhar.reactive.dto.ProductDto;
import com.pavikumbhar.reactive.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductDto productDto){
        return Product.builder().id(productDto.getId())
                .name(productDto.getName())
                .quantity(productDto.getQuantity())
                .price(productDto.getPrice())
                .build();
    }


    public ProductDto toProductDto(Product product){
        return ProductDto.builder().id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
