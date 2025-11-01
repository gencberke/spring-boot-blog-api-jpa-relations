package com.berkedev.springbootblogapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private int status;              // HTTP status code: 404, 409, 400, 500
    private String message;          // ErrorMessage: "User not found with id: 999"
    private LocalDateTime timestamp; // Error time: 2025-11-01T15:30:00
    private String path;             // Endpoint: /api/users/999

    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}