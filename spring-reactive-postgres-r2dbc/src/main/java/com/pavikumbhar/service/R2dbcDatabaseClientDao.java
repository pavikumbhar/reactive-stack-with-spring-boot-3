package com.pavikumbhar.service;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pavikumbhar.model.Customer;

import io.r2dbc.postgresql.api.PostgresqlResult;
import io.r2dbc.postgresql.api.RefCursor;
import io.r2dbc.spi.Readable;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.r2dbc.spi.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	  * <pre>
	 * {@code
	 * CREATE OR REPLACE FUNCTION FN_CUSTOMER(customerId bigint)
		  RETURNS refcursor AS
		$BODY$
		    DECLARE
		      customers refcursor;           -- Declare cursor variables                         
		    BEGIN
		      OPEN customers FOR SELECT* FROM customer WHERE customer_id = customerId;
		      RETURN customers;
		    END;
		  $BODY$
      LANGUAGE plpgsql
        }
		</pre>
	 * @param customerId
	 * @return
	 */
	 @Transactional
	 public Flux<Customer> searchCustomerById(long customerId) {
		log.info("R2dbcDatabaseClientDao::searchCustomerById id: {}",customerId);
	
		return databaseClient.inConnectionMany(connection -> {
		
				Statement statement = connection.createStatement("SELECT FN_CUSTOMER($1)");
				statement.bind("$1", customerId);
				
				Flux<Customer> resultFlux = Flux.from(statement.execute())
				 .flatMap(result -> result.map((row, rowMetadata) -> row.get(0, RefCursor.class)))
				 .flatMap(rc -> rc.fetch().flatMapMany(it -> it.map(CUSTOMER_MAPPING_BI_FUNCTION)));
				
				log.debug("{}",resultFlux);
				
				Flux<RefCursor> refCursor = Flux.from(statement.execute()).flatMap(result -> result.map((row, rowMetadata) -> row.get(0, RefCursor.class)));
				Flux<PostgresqlResult> res = refCursor.flatMap(r->r.fetch());
				Flux<Customer> customerFlux = res.flatMap(records->records.map((row, rowMetadata)->
					 Customer.builder()
						.customerId(row.get("CUSTOMER_ID", Long.class))
						.firstName(row.get("FIRST_NAME", String.class))
						.lastName(row.get("LAST_NAME", String.class))
						.email(row.get("EMAIL", String.class))
						.createdBy(row.get("CREATED_BY", String.class))
						.createdOn(row.get("CREATED_ON", LocalDateTime.class))
						.modifiedBy(row.get("MODIFIED_BY", String.class))
						.modifiedOn(row.get("MODIFIED_ON", LocalDateTime.class))
						.build()
				));
				log.info("R2dbcDatabaseClientDao::searchCustomerById id: {}",customerId);		
				
			 return	 customerFlux;
		
		});
	}
	 



	
}
