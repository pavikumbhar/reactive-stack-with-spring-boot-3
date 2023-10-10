package com.pavikumbhar.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pavikumbhar.model.City;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * 
 * @author pavikumbhar
 *
 */
public interface CityRepository  extends ReactiveCrudRepository<City,Long> {
	

	@Query(value = "SELECT c.*  FROM CITIES c  " +
			   " WHERE (:name IS NULL OR :name ='' OR LOWER(c.NAME) LIKE LOWER(CONCAT(:name, '%'))) " +
			   " AND  (:stateCode IS NULL OR :stateCode ='' OR LOWER(c.STATE_CODE) LIKE LOWER(CONCAT(:stateCode, '%'))) " +
			   " AND  (:countryCode IS NULL OR :countryCode ='' OR LOWER(c.COUNTRY_CODE) LIKE LOWER(CONCAT(:countryCode, '%'))) " +
			   " OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY " )
	Flux<City> searchCity(@Param("name") String name,
						  @Param("stateCode") String stateCode,
						  @Param("countryCode") String countryCode,
						  @Param("offset") int offset,
						  @Param("size") int size);
	
	
	@Query(value = "SELECT count(*)  FROM CITIES c  " +
			   " WHERE (:name IS NULL OR :name ='' OR LOWER(c.NAME) LIKE LOWER(CONCAT(:name, '%'))) " +
			   " AND  (:stateCode IS NULL OR :stateCode ='' OR LOWER(c.STATE_CODE) LIKE LOWER(CONCAT(:stateCode, '%'))) " +
			   " AND  (:countryCode IS NULL OR :countryCode ='' OR LOWER(c.COUNTRY_CODE) LIKE LOWER(CONCAT(:countryCode, '%')))  " )
	Mono<Long> searchCityCount(@Param("name") String name,
							   @Param("stateCode") String stateCode,
							   @Param("countryCode") String countryCode);
	
	@Query(value = "SELECT c.*  FROM CITIES c  " +
			   " WHERE (:stateId IS NULL OR c.STATE_ID =:stateId) " +
			   " AND  (:countryId IS NULL OR c.COUNTRY_ID =:countryId )" )
	Flux<City> findByStateIdAndCountryId(@Param("stateId") Long stateId,
										 @Param("countryId") Long countryId);

}
