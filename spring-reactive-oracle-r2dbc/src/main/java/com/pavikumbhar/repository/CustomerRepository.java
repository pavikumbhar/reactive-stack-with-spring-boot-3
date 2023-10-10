package com.pavikumbhar.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pavikumbhar.model.Customer;

import reactor.core.publisher.Flux;

public interface CustomerRepository  extends ReactiveCrudRepository<Customer,Long> {

    Flux<Customer> findAllBy(Pageable pageable);

    @Query("SELECT * FROM Customer")
    Flux<Customer> findAllByNativeQuery();


    @Query(value = "SELECT c.* FROM Customer c  " +
    			   " WHERE (:firstName IS NULL OR TRIM(BOTH :firstName) ='' OR LOWER(c.first_name) LIKE LOWER(CONCAT(:firstName, '%'))) " +
    			   " AND  (:lastName IS NULL OR TRIM(BOTH :lastName) ='' OR LOWER(c.last_name) LIKE LOWER(CONCAT(:lastName, '%'))) " +
    			   " AND  (:email IS NULL OR TRIM(BOTH :email) ='' OR LOWER(c.email) LIKE LOWER(CONCAT(:email, '%')))")
    Flux<Customer> findCustomerByNameAndEmail(@Param("firstName") String firstName,
										      @Param("lastName") String lastName,
										      @Param("email") String email,
										      Pageable pageable);
    
    @Query("BEGIN :customers := FN_CUSTOMER(:customerId); END;")
    Flux<Customer> callMyFunction(long customerId);

}

