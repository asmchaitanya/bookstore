package com.bookstore.response;

public class BaseResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseResponse(String message) {
        this.message = message;
    }
}
