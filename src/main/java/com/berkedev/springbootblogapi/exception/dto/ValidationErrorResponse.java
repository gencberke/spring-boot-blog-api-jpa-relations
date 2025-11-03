package com.berkedev.springbootblogapi.exception.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Error response for validation failures.
 * Extends ErrorResponse to include field-specific validation errors.
 * Used when @Valid annotation fails on request DTOs.
 * Response structure example:
 * {
 *   "status": 400,
 *   "message": "Validation failed",
 *   "timestamp": "2025-11-01T15:30:00",
 *   "path": "/api/users",
 *   "fieldErrors": {
 *     "email": "Email must be valid",
 *     "username": "Username must be between 5-20 characters"
 *   }
 * }
 * The fieldErrors map contains:
 * - Key: Field name (e.g., "email", "username")
 * - Value: Validation error message for that field
 */
@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {

    /**
     * Map of field names to their validation error messages.
     * Key: field name (e.g., "email")
     * Value: error message (e.g., "Email must be valid")
     */
    private Map<String, String> fieldErrors;

    /**
     * Constructs a ValidationErrorResponse with all fields.
     *
     * @param status      HTTP status code (typically 400)
     * @param message     general error message (e.g., "Validation failed")
     * @param timestamp   when the validation error occurred
     * @param path        API endpoint where validation failed
     * @param fieldErrors map of field names to error messages
     */
    public ValidationErrorResponse(
            int status,
            String message,
            LocalDateTime timestamp,
            String path,
            Map<String, String> fieldErrors
    ) {
        super(status, message, timestamp, path);
        this.fieldErrors = fieldErrors;
    }
}
