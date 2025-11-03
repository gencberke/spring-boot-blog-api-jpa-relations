package com.berkedev.springbootblogapi.exception;

/**
 * Exception thrown when attempting to create a resource that already exists.
 * Results in HTTP 409 CONFLICT response.
 * Common use cases:
 *  - Email already registered
 *  - Username already taken
 *  - Slug already exists
 *  - Category name already exists
 * Example usage:
 *  Simple message
 *      throw new DuplicateResourceException("Email already exists");
 *  Dynamic message with helper constructor
 *      throw new DuplicateResourceException("Email", "test@test.com");
 *  Result: "Email already exists: test@test.com"
 */
public class DuplicateResourceException extends BlogApiException {

    /**
     * Constructs a DuplicateResourceException with a custom message.
     *
     * @param message the error message
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

    /**
     * Constructs a DuplicateResourceException with a formatted message.
     * Generates message in format: "{fieldName} already exists: {fieldValue}"
     *
     * @param fieldName  the field that has duplicate value (e.g., "Email", "Username")
     * @param fieldValue the duplicate value (e.g., "test@test.com", "johndoe")
     */
    public DuplicateResourceException(String fieldName, String fieldValue) {
        super(String.format("%s already exists: %s", fieldName, fieldValue));
    }
}
