package com.pavikumbhar.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Builder
public class ProductDto {

    private String id;
    private String name;
    private int quantity;
    private double price;
    
}
