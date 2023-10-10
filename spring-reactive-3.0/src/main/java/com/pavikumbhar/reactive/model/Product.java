package com.pavikumbhar.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private int quantity;
    private double price;
}
