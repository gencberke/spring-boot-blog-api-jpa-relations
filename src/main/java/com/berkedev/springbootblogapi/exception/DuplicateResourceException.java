package com.berkedev.springbootblogapi.exception;

public class DuplicateResourceException extends BlogApiException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String fieldName, String fieldValue) {
        super(String.format("%s already exists: %s", fieldName, fieldValue));
    }
}
