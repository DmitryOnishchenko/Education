package com.donishchenko.instaphoto.instagram.api;

public class ApiException extends Throwable {
    private String message;

    public ApiException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
