package com.pavikumbhar.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="CUSTOMER")
@ToString
@EqualsAndHashCode
@FieldNameConstants(asEnum = true)
public class Customer implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1662734661300411666L;

	@Id
    @Column(value = "CUSTOMER_ID")
    private long customerId;

    @Column(value = "FIRST_NAME")
    private String firstName;

    @Column(value = "LAST_NAME")
    private String lastName;

    @Column(value = "EMAIL")
    private String email;

    @CreatedBy
    @Column(value="CREATED_BY")
    private String createdBy;

    @LastModifiedBy
    @Column(value="MODIFIED_BY")
    private String modifiedBy;

    @CreatedDate
    @Column(value="CREATED_ON")
    private LocalDateTime createdOn;

    @LastModifiedDate
    @Column(value="MODIFIED_ON")
    private LocalDateTime modifiedOn;
}
