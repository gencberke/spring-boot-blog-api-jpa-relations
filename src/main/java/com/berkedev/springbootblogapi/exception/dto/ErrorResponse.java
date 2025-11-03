package com.berkedev.springbootblogapi.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Standard error response structure returned to clients when an error occurs.
 * Provides a consistent error format across the entire API.
 * All exception handlers return this DTO (or its subclasses).
 * Example response:
 * {
 *   "status": 404,
 *   "message": "User not found with id: 999",
 *   "timestamp": "2025-11-01T15:30:00",
 *   "path": "/api/users/999"
 * }
 * See also: ValidationErrorResponse
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * HTTP status code (404, 409, 400, 500, etc.)
     */
    private int status;

    /**
     * Human-readable error message.
     */
    private String message;

    /**
     * Timestamp when the error occurred (formatted as ISO-8601).
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * API endpoint path where the error occurred (e.g., "/api/users/999").
     */
    private String path;

    /**
     * Constructor for creating error response without path.
     * Useful when path is not relevant or available.
     */
    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
