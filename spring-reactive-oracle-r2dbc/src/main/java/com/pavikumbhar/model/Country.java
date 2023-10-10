package com.pavikumbhar.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="COUNTRIES")
@ToString
@EqualsAndHashCode
@FieldNameConstants(asEnum = true)
public class Country {
	
	@Id
    @Column(value = "ID")
    private long id;

    @Column(value = "NAME")
    private String name;

    @Column(value = "ISO3")
    private String iso3;
    
    @Column(value = "NUMERIC_CODE")
    private String numericCode;

    @Column(value = "ISO2")
    private String iso2;
    
    @Column(value = "PHONECODE")
    private String phonecode;
    
    @Column(value = "CAPITAL")
    private String capital;
    
    @Column(value = "CURRENCY")
    private String currency;
    
    @Column(value = "CURRENCY_NAME")
    private String currencyName;
    
    @Column(value = "CURRENCY_SYMBOL")
    private String currencySymbol;
    
    @Column(value = "TLD")
    private String tld;
    
    @Column(value = "NATIVE")
    private String natives;
    
    @Column(value = "REGION")
    private String region;
    
    @Column(value = "SUBREGION")
    private String subRegion;
    
    @Column(value = "TIMEZONES")
    private String timezones;
    
    @Column(value = "TRANSLATIONS")
    private String translations;
    
    @Column(value = "LATITUDE")
    private long latitude;
    
    @Column(value = "LONGITUDE")
    private long longitude;
    
    @Column(value = "EMOJI")
    private String emoji;
    
    @Column(value = "EMOJIU")
    private String emojiu;
     
    @CreatedDate
    @Column(value="CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(value="UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @Column(value = "FLAG")
    private boolean flag;
        
    @Column(value = "WIKIDATAID")
    private String wikiDataId;
    
    

}
