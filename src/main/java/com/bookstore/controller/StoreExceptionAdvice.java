package com.bookstore.controller;

import com.bookstore.exception.StoreException;
import com.bookstore.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StoreExceptionAdvice {

    @ExceptionHandler(StoreException.class)
    public ResponseEntity<ErrorResponse> handleStoreException(StoreException ex) {
        HttpStatus errorStatus = ex.getStatus();
        if (errorStatus == null) {
            errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(getErrorResponse(ex.getMessage()), errorStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return new ResponseEntity<>(getErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse getErrorResponse(String message) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(message);
        return response;
    }
}
