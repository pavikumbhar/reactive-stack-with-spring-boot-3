package com.pavikumbhar.reactive.controller;

import com.pavikumbhar.reactive.dto.ProductDto;
import com.pavikumbhar.reactive.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/***
 * @Author pavikumbhar
 */
@Slf4j
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private  final ProductService productService;

    @GetMapping
    public Flux<ProductDto> findAllProduct(){
        log.info("findAllProduct");
        return productService.findAllProduct();
    }
    @GetMapping("/{id}")
    public Mono<ProductDto> findProductById(@PathVariable String id){
        log.info("findProductById -id :{}",id);
        return productService.findProductById(id);
    }
    @GetMapping("price-range")
    public Flux<ProductDto> findProductInRange(@RequestParam double min,@RequestParam double max){
        log.info("findProductInRange - min: {} max :{}",min,max);
        return productService.findProductInRange(min,max);

    }
    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto){
        log.info("saveProduct - productDto: {}",productDto);
        return productService.saveProduct(productDto);
    }
    @PutMapping("/{id}")
    public  Mono<ProductDto> updateProduct (@RequestBody Mono<ProductDto> productDto, @PathVariable String id){
        log.info("updateProduct - id: {}",id);
        return  productService.updateProduct(productDto,id);
    }
    @DeleteMapping("/{id}")
    public Mono<Void> deleteProductById(@PathVariable String id){
        log.info("deleteProductById - id: {}",id);
        return productService.deleteProductById(id);
    }

    @GetMapping("/search")
    public Flux<ProductDto> searchUsers(@RequestParam("name") String name) {
        log.info("searchUsers - name: {}",name);
        return productService.findProductByCriteria(name);
    }

}
