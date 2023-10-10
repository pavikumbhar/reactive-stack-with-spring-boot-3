package com.pavikumbhar.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavikumbhar.dto.CustomerDto;
import com.pavikumbhar.mapper.PageSupport;
import com.pavikumbhar.model.Customer;
import com.pavikumbhar.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * 
 * @author pavikumbhar
 *
 */
@Slf4j
@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

   
    @PostMapping
    public Mono<ResponseEntity<CustomerDto>> saveCustomer(@RequestBody Mono<CustomerDto> customerDto) {
    	 log.info("CustomerController :: Start saveCustomer ");
        return customerService.saveCustomer(customerDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{customerId}")
    public Mono<ResponseEntity<CustomerDto>> findCustomerById(@PathVariable Long customerId) {
        log.info("CustomerController :: findCustomerById - customerId: {} ", customerId);
        return customerService.findCustomerById(customerId)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<Page<Customer>> findCustomer(@RequestParam(value = "page", defaultValue = "0") int page,
            								 @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("CustomerController :: findCustomer - page: {} size : {}", page, size);
        return customerService.findCustomer(PageRequest.of(page, size));
    }

  
    @GetMapping(value = "/search")
    public Mono<PageSupport<Customer>> searchCustomer(@RequestParam(required = false) String firstName,
											           @RequestParam(required = false) String lastName,
											           @RequestParam(required = false) String email,
											           @RequestParam(value = "page", defaultValue = "0") int page,
											           @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("CustomerController :: searchCustomer - page: {} size : {}", page, size);
        return customerService.searchCustomer(firstName, lastName, email, PageRequest.of(page, size));
    }


    @GetMapping(value = "/search-by-id")
	 public  Flux<CustomerDto> searchCountryById(@RequestParam(required = false) long customerId) {
	        log.info("CustomerController :: searchCountryById - customerId: {} ", customerId);
	        return  customerService.searchCountryById(customerId);
	    }	
	
        
   
}
