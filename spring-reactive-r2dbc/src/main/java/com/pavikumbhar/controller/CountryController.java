package com.pavikumbhar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavikumbhar.mapper.PageSupport;
import com.pavikumbhar.model.Country;
import com.pavikumbhar.service.CountryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @author pavikumbhar
 *
 */
@Slf4j
@RestController
@RequestMapping("country")
@RequiredArgsConstructor
public class CountryController {
	
	private final CountryService countryService;
	
	@GetMapping(value = "/search")
	 public Mono<PageSupport<Country>> searchCountry(@RequestParam(required = false) String name,
													  @RequestParam(required = false) String iso3,
													  @RequestParam(value = "page", defaultValue = "0") Integer page,
													  @RequestParam(value = "size", defaultValue = "10") Integer size) {
	    	log.info("CountryController:: searchCountry name:{} iso3: {} page: {} size :{}",name,iso3,page,size);
	    	return countryService.searchCountry(name,iso3,page, size);
        }

}
