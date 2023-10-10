package com.pavikumbhar.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pavikumbhar.dto.ProductDto;
import com.pavikumbhar.model.Product;
/**
 * @author pavikumbhar
 */
@Component
public class ProductMapper {


    public Product toProduct(ProductDto productDto){
        return Product.builder().id(productDto.getId())
        .name(productDto.getName())
        .price(productDto.getPrice())
        .quantity(productDto.getQuantity())
        .build();
    }

    public ProductDto toProductDto(Product product){
        return ProductDto.builder().id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .quantity(product.getQuantity())
        .build();
    }

    public List<ProductDto> toProductDtoList(List<Product> productList){
        return productList.stream().map(this::toProductDto).toList();
        
    }

    public List<Product> toProductList(List<ProductDto> productDtoList){
        return productDtoList.stream().map(this::toProduct).toList();

    }
    
}
