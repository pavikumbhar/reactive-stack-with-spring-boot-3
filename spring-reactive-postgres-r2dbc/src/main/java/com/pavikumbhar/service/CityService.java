package com.pavikumbhar.service;

import org.springframework.stereotype.Service;

import com.pavikumbhar.mapper.PageSupport;
import com.pavikumbhar.model.City;
import com.pavikumbhar.repository.CityRepository;

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
public class CityService {
	
 private final CityRepository cityRepository;

	/**
	 * 
	 * @param name
	 * @param stateCode
	 * @param countryCode
	 * @param page
	 * @param size
	 * @return
	 */
	 public Mono<PageSupport<City>> searchCity(String name, String stateCode, String countryCode,int page, int size) {
	    	log.info("CityService:: searchCity name: {} stateCode :{} countryCode: {} page: {}",name,stateCode,countryCode,page);
	    	return cityRepository.searchCity(name,stateCode,countryCode,page*size, size)
             .collectList()
             .zipWith(cityRepository.searchCityCount(name, stateCode, countryCode))
             .map(t -> new PageSupport<>(t.getT1(), page,size, t.getT2()));
	    	
	    	
	    }
	 
	 /**
	  * 
	  * @param stateId
	  * @param countryId
	  * @return
	  */
	 public Flux<City> findByStateIdAndCountryId(Long stateId, Long countryId) {
		 log.info("CityService::findByStateIdOrCountryId :stateId: {} countryId: {}",countryId,countryId);
			return cityRepository.findByStateIdAndCountryId(stateId, countryId);

		}
	 

}
