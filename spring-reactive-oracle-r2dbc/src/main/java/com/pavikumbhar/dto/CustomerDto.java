package com.pavikumbhar.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  CustomerDto {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private long customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String createdBy;

    private String modifiedBy;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime createdOn;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime modifiedOn;
}
