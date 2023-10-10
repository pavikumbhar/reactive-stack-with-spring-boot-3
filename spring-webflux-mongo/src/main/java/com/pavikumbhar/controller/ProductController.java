package com.pavikumbhar.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavikumbhar.dto.ProductDto;
import com.pavikumbhar.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author pavikumbhar
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("product")
@RestController
public class ProductController {

    private final ProductService productService;
    /**
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public Flux<ProductDto> findProducts(@RequestParam(value = "page",defaultValue = "0") int page,
                                        @RequestParam(value="size",defaultValue = "10") int size){
        log.info("findProducts page:{} size:{}",page,size);
        return productService.findProducts(page,size);
    }
    /**
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Mono<ProductDto> findProductById(@PathVariable String id){
        log.info("findProductById id:{}",id);
        return productService.findProductById(id);

    }
    /**
     * @param productDto
     * @return
     */
    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto ) {
        log.info("saveProduct");
        return productService.saveProduct(productDto);
    }
    /**
     * @param productDto
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public  Mono<ProductDto> updateProduct (@RequestBody Mono<ProductDto> productDto, @PathVariable String id){
        log.info("updateProduct - id: {}",id);
        return  productService.updateProduct(productDto,id);
    }
    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<Void> deleteProductById(@PathVariable String id){
        log.info("deleteProductById - id: {}",id);
        return productService.deleteProductById(id);
    }

    /**
     * @param name
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search")
    public Mono<Page<ProductDto>> findByCriteria(@RequestParam(value = "name" ,required = false) String name, 
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value="size",defaultValue = "10") int size) {
        log.info("findByNamePageable name :{} page:{} size:{}",name,page,size);
        return productService.findByCriteria(name, page, size);
        
    }
    
    
}
