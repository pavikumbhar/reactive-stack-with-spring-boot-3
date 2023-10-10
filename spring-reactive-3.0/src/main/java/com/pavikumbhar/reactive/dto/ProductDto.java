package com.pavikumbhar.reactive.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String id;
    private String name;
    private int quantity;
    private double price;
}
