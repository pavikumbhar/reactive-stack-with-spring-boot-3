package com.pavikumbhar.reactive.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.notFound;


@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    //@ExceptionHandler(Exception.class)
    ResponseEntity<?> postNotFound(Exception ex) {
        log.info("handling exception::" + ex);
        return notFound().build();
    }

    @ExceptionHandler(Exception.class)
    ErrorResponse handleException(Exception e) {
        log.info("handleException");
        return ErrorResponse
                .builder(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
                .build();
    }
}
