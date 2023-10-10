package com.pavikumbhar.reactive.service;

import com.pavikumbhar.reactive.dto.ProductDto;
import com.pavikumbhar.reactive.mapper.ProductMapper;
import com.pavikumbhar.reactive.model.Product;
import com.pavikumbhar.reactive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final ProductMapper productMapper;

    public Flux<ProductDto> findAllProduct(){
        return productRepository.findAll()
                .map(productMapper::toProductDto);

    }

    public Mono<ProductDto> findProductById(String id){
        return productRepository.findById(id)
                .map(productMapper::toProductDto);
    }

    public Flux<ProductDto> findProductInRange(double min,double max){
        return productRepository.findByPriceBetween(Range.closed(min,max))
                .map(productMapper::toProductDto);

    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto){
        return productDto.map(productMapper::toProduct)
                .flatMap(productRepository::insert)
                .map(productMapper::toProductDto);
    }

    public  Mono<ProductDto> updateProduct (Mono<ProductDto> productDto, String id){
        return  productRepository.findById(id)
                .flatMap(p -> productDto.map(productMapper::toProduct).doOnNext(e->e.setId(id)))
                .flatMap(productRepository::save)
                .map(productMapper::toProductDto);
    }

    public Mono<Void> deleteProductById(String id){
        return productRepository.deleteById(id);
    }

    public Flux<ProductDto> findProductByCriteria(String name){
        Query query = new Query()
                .with(Sort.by(Collections.singletonList(Sort.Order.asc("id"))));
        query.addCriteria(Criteria.where("name").regex(name));
        return reactiveMongoTemplate.find(query, Product.class)
                .switchIfEmpty(Flux.error(() -> new RuntimeException(String.format("Data not found for name %s",name))))
                .map(productMapper::toProductDto);
    }



}
