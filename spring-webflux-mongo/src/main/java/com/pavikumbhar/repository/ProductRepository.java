package com.pavikumbhar.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.pavikumbhar.model.Product;

import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product,String> {

    Flux<Product> findAllByIdNotNullOrderByIdAsc(Pageable pageable);  
}
