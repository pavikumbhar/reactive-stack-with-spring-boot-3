package com.pavikumbhar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavikumbhar.mapper.PageSupport;
import com.pavikumbhar.model.City;
import com.pavikumbhar.service.CityService;

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
@RequestMapping("city")
@RequiredArgsConstructor
public class CityController {
	
	private final CityService cityService;
	
	 @GetMapping(value = "/search")
	 public Mono<PageSupport<City>> searchCountry(@RequestParam(required = false) String name,
												   @RequestParam(required = false) String stateCode,
												   @RequestParam(required = false) String countryCode,
												   @RequestParam(value = "page", defaultValue = "0") Integer page,
												   @RequestParam(value = "size", defaultValue = "10") Integer size) {
	        log.info("CityController :: searchCountry - page: {} size : {}", page, size);
	        return cityService.searchCity(name, stateCode, countryCode,page, size);
	    }	
	 
	 @GetMapping
	 public Flux<City> findByStateIdAndCountryId(@RequestParam(required = false) Long stateId, 
			 									 @RequestParam(required = false) Long countryId) {
		 return cityService.findByStateIdAndCountryId(stateId, countryId);
	 }

}
