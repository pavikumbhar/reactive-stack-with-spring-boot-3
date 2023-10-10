package com.pavikumbhar.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Builder
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private int quantity;
    private double price;
    
}
