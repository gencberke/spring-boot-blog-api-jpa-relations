package com.berkedev.springbootblogapi.exception;

public class BadRequestException extends BlogApiException {

    public BadRequestException(String message) {
        super(message);
    }
}
