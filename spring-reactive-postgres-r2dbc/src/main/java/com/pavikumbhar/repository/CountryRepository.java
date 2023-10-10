package com.pavikumbhar.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pavikumbhar.model.Country;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryRepository extends ReactiveCrudRepository<Country,Long>{
	
	@Query(value = "SELECT c.*  FROM COUNTRIES c  " +
			   " WHERE (:name IS NULL OR :name ='' OR LOWER(c.NAME) LIKE LOWER(CONCAT(:name, '%'))) " +
			   " AND  (:iso3 IS NULL OR :iso3 ='' OR LOWER(c.ISO3) LIKE LOWER(CONCAT(:iso3, '%'))) " +
			   " OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY " )
	Flux<Country> searchCountry(@Param("name") String name,
						  @Param("iso3") String iso3,
						  @Param("offset") int offset,
						  @Param("size") int size);
	
	
	@Query(value = "SELECT count(*)  FROM COUNTRIES c  " +
			   " WHERE (:name IS NULL OR :name ='' OR LOWER(c.NAME) LIKE LOWER(CONCAT(:name, '%'))) " +
			   " AND  (:iso3 IS NULL OR :iso3 ='' OR LOWER(c.ISO3) LIKE LOWER(CONCAT(:iso3, '%')))  " )
	Mono<Long> searchCountryCount(@Param("name") String name,
								  @Param("iso3") String iso3);

}
