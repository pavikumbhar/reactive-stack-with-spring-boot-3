package com.pavikumbhar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pavikumbhar.dto.CustomerDto;
import com.pavikumbhar.mapper.CustomerMapper;
import com.pavikumbhar.mapper.PageSupport;
import com.pavikumbhar.model.Customer;
import com.pavikumbhar.repository.CustomerRepository;

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
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository  customerRepository;
    private final CustomerMapper customerMapper;
	private final R2dbcDatabaseClientDao r2dbcDatabaseClientDao;
   
    
    /**
     * 
     * @param customerDto
     * @return
     */
    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> customerDto ){
    	log.info("CustomerService::saveCustomer");
        return customerDto.map(customerMapper::toCustomer)
                .flatMap(customerRepository::save)
                .map(customerMapper::toCustomerDto);
    }
    
    /**
     * 
     * @param customerId
     * @return
     */
    public Mono<CustomerDto> findCustomerById(Long customerId ){
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new Exception(String.format("customer with ID %d not found", customerId))))
                .map(customerMapper::toCustomerDto);
    }
    
    /**
     * 
     * @param pageRequest
     * @return
     */
    public Mono<Page<Customer>> findCustomer(PageRequest pageRequest) {
        return customerRepository.findAll()
                .collectList()
                .zipWith(this.customerRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param page
	 * @return
	 */
    public Mono<PageSupport<Customer>> searchCustomer(String firstName, String lastName, String email,PageRequest page) {
    	log.info("CustomerService:: searchCustomer firstName: {} lastName :{} email: {} page: {}",firstName,lastName,email,page);
        return customerRepository.findCustomerByNameAndEmail(firstName,lastName,email,page)
            .collectList()
            .map(list -> new PageSupport<>(
                list.stream()
                    .skip((long)page.getPageNumber() * page.getPageSize())
                    .limit(page.getPageSize())
                    .toList(),
                page.getPageNumber(), page.getPageSize(), list.size()));
    }

    /**
     * 
     * @param customerId
     * @return
     */
	public Flux<CustomerDto> searchCountryById(long customerId) {
		 r2dbcDatabaseClientDao.searchCountryById(customerId)
				.map(customerMapper::toCustomerDto);
		return customerRepository.callMyFunction(customerId)
				.map(customerMapper::toCustomerDto);
	}
	
}
