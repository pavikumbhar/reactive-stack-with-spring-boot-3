package com.pavikumbhar.service;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import com.pavikumbhar.model.Customer;

import io.r2dbc.spi.Parameters;
import io.r2dbc.spi.R2dbcType;
import io.r2dbc.spi.Readable;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.r2dbc.spi.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.r2dbc.OracleR2dbcTypes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class R2dbcDatabaseClientDao {
	
	private  final DatabaseClient databaseClient;

	
	
	public static final BiFunction<Row, RowMetadata, Customer> CUSTOMER_MAPPING_BI_FUNCTION = (row, rowMetaData) ->rowToCustomer(row);
	 
	public static final Function<Readable, Customer> CUSTOMER_ID_ROW_MAPPER = R2dbcDatabaseClientDao::rowToCustomer;

	/**
	 * 
	 * @param row
	 * @return
	 */
	private static Customer rowToCustomer(Readable row) {
	return Customer.builder()
					.customerId(row.get("CUSTOMER_ID", Long.class))
					.firstName(row.get("FIRST_NAME", String.class))
					.lastName(row.get("LAST_NAME", String.class))
					.email(row.get("EMAIL", String.class))
					.createdBy(row.get("CREATED_BY", String.class))
					.createdOn(row.get("CREATED_ON", LocalDateTime.class))
					.modifiedBy(row.get("MODIFIED_BY", String.class))
					.modifiedOn(row.get("MODIFIED_ON", LocalDateTime.class))
					.build();
	}
	 
	 /**
	  * 
	  * @param id
	  * @return
	  */
	 public Mono<Customer> findById(long id) {
		 log.info("R2dbcDatabaseClientDao :: findById id: {}",id);
	        return this.databaseClient
	                .sql("SELECT * FROM CUSTOMER c WHERE CUSTOMER_ID=:id")
	                .bind("id", id)
	                .map(CUSTOMER_MAPPING_BI_FUNCTION)
	                .one();
	    }

	/**
	 * CREATE OR REPLACE PROCEDURE SP_CUSTOMER (
		   customerId IN NUMBER,
		   customers OUT SYS_REFCURSOR )
		AS 
		BEGIN
		    OPEN customers FOR
		    SELECT *
		    FROM customer
		    WHERE customer_id = customerId;
		END;
	 * @param customerId
	 * @return
	 */
	 public Flux<Customer> searchCountryById(long customerId) {
		log.info("R2dbcDatabaseClientDao::getCustomer id: {}",customerId);
		final  String customersCursor="customers";
		return databaseClient.inConnectionMany(connection -> {
		
				Statement statement = connection.createStatement("BEGIN SP_CUSTOMER(:customerId,:customers);END;");
				statement.bind("customerId", Parameters.in(R2dbcType.INTEGER,customerId));
			    statement.bind(customersCursor, Parameters.out(OracleR2dbcTypes.REF_CURSOR));
		
				Flux<Result> customerResult = Flux.from(statement.execute())
						.flatMap(result -> result.map(outParameters -> outParameters.get(customersCursor,Result.class)));
						return  customerResult.flatMap(customer->customer.map(CUSTOMER_ID_ROW_MAPPER));
		
		});
	}
	 


	
}
