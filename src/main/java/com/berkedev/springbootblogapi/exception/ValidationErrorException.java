package com.berkedev.springbootblogapi.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorException extends ErrorResponse {

    private Map<String, String> fieldErrors;

    public ValidationErrorException(
            int status,
            String message,
            LocalDateTime timestamp,
            String path,
            Map<String, String> fieldErrors // mapping of field errors
    ) {
        super(status, message, timestamp, path);
        this.fieldErrors = fieldErrors;
    }
}
