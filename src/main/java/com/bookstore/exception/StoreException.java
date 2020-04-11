package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class StoreException extends RuntimeException {

    private String message;

    private HttpStatus status;

    public StoreException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public StoreException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
