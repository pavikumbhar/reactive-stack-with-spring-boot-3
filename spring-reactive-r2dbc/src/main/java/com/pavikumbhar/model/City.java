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
@Table(name="CITIES")
@ToString
@EqualsAndHashCode
@FieldNameConstants(asEnum = true)
public class City {
	
	@Id
    @Column(value = "ID")
    private long id;

    @Column(value = "NAME")
    private String name;

    @Column(value = "STATE_ID")
    private long stateId;
    
    @Column(value = "STATE_CODE")
    private String stateCode;

    @Column(value = "COUNTRY_ID")
    private long countryId;
    
    @Column(value = "COUNTRY_CODE")
    private String countryCode;
        
    @Column(value = "LATITUDE")
    private long latitude;
    
    @Column(value = "LONGITUDE")
    private long longitude;
     
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
