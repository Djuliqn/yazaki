package com.yazaki.yazaki.domain.core;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yazaki.yazaki.domain.exception.DishExistsException;
import com.yazaki.yazaki.domain.exception.DishIllegalArgumentException;
import com.yazaki.yazaki.domain.exception.OrderDateExistsException;
import com.yazaki.yazaki.domain.exception.OrderIllegalArgumentException;
import com.yazaki.yazaki.domain.exception.RecordNotFoundException;
import com.yazaki.yazaki.domain.exception.UsernameExistsException;

@RestControllerAdvice
public class WebRestControllerAdvice extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> handleRecordNotFoundException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<?> handleUsernameExistsException(RuntimeException ex, WebRequest request) {
        final String bodyOfResponse = "Потребителското име съществъва.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(DishExistsException.class)
    public ResponseEntity<?> handleDishExistsException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(DishIllegalArgumentException.class)
    public ResponseEntity<?> handleDishIllegalArgumentException(RuntimeException ex, WebRequest request) {
    	return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    @ExceptionHandler(OrderIllegalArgumentException.class)
    public ResponseEntity<?> handleOrderIllegalArgumentException(RuntimeException ex, WebRequest request) {
    	return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    @ExceptionHandler(OrderDateExistsException.class)
    public ResponseEntity<?> handleOrderDateExistsException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

   
}
