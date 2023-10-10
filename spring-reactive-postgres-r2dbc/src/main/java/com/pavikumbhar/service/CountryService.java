package com.pavikumbhar.service;

import org.springframework.stereotype.Service;

import com.pavikumbhar.mapper.PageSupport;
import com.pavikumbhar.model.Country;
import com.pavikumbhar.repository.CountryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @author pavikumbhar
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {
		
	private final CountryRepository countryRepository;
	
	/**
	 * 
	 * @param name
	 * @param iso3
	 * @param page
	 * @param size
	 * @return
	 */
	 public Mono<PageSupport<Country>> searchCountry(String name,  String iso3,int page, int size) {
	    	log.info("CountryService:: searchCountry name: {}  iso3: {} page: {} size:{}",name,iso3,page,size);
	    	return countryRepository.searchCountry(name,iso3,page*size, size)
             .collectList()
             .zipWith(countryRepository.searchCountryCount(name,  iso3))
             .map(t -> new PageSupport<>(t.getT1(), page,size, t.getT2()));
	    }

}
