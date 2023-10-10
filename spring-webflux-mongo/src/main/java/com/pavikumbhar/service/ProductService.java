package com.pavikumbhar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pavikumbhar.dto.ProductDto;
import com.pavikumbhar.mapper.ProductMapper;
import com.pavikumbhar.model.Product;
import com.pavikumbhar.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * @author pavikumbhar
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;



    public Flux<ProductDto> findProducts(int page,int size){
        Pageable pageable = PageRequest.of(page, size );
        return productRepository.findAllByIdNotNullOrderByIdAsc(pageable)
        .map(productMapper::toProductDto);
        
    }


    public Mono<ProductDto> findProductById(String id){
        log.info("findProductById id:{}",id);
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(()->new RuntimeException(String.format("Data not found for id %s",id))))
                .map(productMapper::toProductDto);
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto){
        log.info("saveProduct");
        return productDto.map(productMapper::toProduct)
                .flatMap(productRepository::insert)
                .map(productMapper::toProductDto);
    }

    public  Mono<ProductDto> updateProduct (Mono<ProductDto> productDto, String id){
        return  productRepository.findById(id)
        .switchIfEmpty(Mono.error(()->new RuntimeException(String.format("Data not found for id %s",id))))
                .flatMap(p -> productDto.map(productMapper::toProduct).doOnNext(e->e.setId(id)))
                .flatMap(productRepository::save)
                .map(productMapper::toProductDto);
    }

    public Mono<Void> deleteProductById(String id){
        return productRepository.deleteById(id);
    }



    public Mono<Page<Product>> findAllProductPaged(Pageable pageable) {
        return this.productRepository.count()
                .flatMap(productCount -> {
                    return this.productRepository.findAll(pageable.getSort())
                            .buffer(pageable.getPageSize(),(pageable.getPageNumber() + 1))
                            .elementAt(pageable.getPageNumber(), new ArrayList<>())
                            .map(products -> new PageImpl<Product>(products, pageable, productCount));
                });
    }


    /**
     * @param name
     * @param page
     * @param size
     * @return
     */
    public Mono<Page<Product>> findByNamePageable(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        List<Criteria> criteria=new ArrayList<>();
        Query query = new Query().with(pageable);
        if(!ObjectUtils.isEmpty(name)){
            criteria.add(Criteria.where("name").regex(name,"i"));
        }
        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
        }
        Flux<Product> productFlux = reactiveMongoTemplate.find(query, Product.class, "product");
        Mono<Long> countMono = reactiveMongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class);
        return Mono.zip(productFlux.collectList(),countMono).map(tuple -> {
            return PageableExecutionUtils.getPage( tuple.getT1(),pageable,tuple::getT2);
        });   
    }


    public Mono<Page<ProductDto>> findByCriteria(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        List<Criteria> criteria=new ArrayList<>();
        Query query = new Query().with(pageable);
        if(!ObjectUtils.isEmpty(name)){
            criteria.add(Criteria.where("name").regex(name,"i"));
        }
        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
        }
        Flux<Product> productFlux = reactiveMongoTemplate.find(query, Product.class, "product");
        Mono<List<ProductDto>> productListMono = productFlux.collectList().map(productMapper::toProductDtoList);
        Mono<Long> countMono = reactiveMongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class);
        return Mono.zip(productListMono,countMono).map(tuple -> {
            return PageableExecutionUtils.getPage( tuple.getT1(),pageable,tuple::getT2);
        });   
    }



}
