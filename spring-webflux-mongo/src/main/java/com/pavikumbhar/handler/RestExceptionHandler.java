package com.pavikumbhar.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
@RestControllerAdvice
@Slf4j
class RestExceptionHandler {

@ExceptionHandler(Exception.class)
ResponseEntity<Map<String, Object>> notFound(Exception ex) {
        log.info("handling exception::" + ex);
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


   // @ExceptionHandler(Exception.class)
    ErrorResponse handleException(Exception e) {
        log.info("handleException");
        return ErrorResponse
                .builder(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
                .build();
    }

}