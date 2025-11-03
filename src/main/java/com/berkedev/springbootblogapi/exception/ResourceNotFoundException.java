package com.berkedev.springbootblogapi.exception;

/**
 * Exception thrown when a requested resource is not found in the database.
 * Results in HTTP 404 NOT FOUND response.
 * Common use cases:
 *  - User not found by id
 *  - Post not found by slug
 *  - Category not found by name
 *  - Any resource lookup that fails
 * Example usage:
 *  Simple message
 *      throw new ResourceNotFoundException("User not found");
 *  Dynamic message with helper constructor
 *      throw new ResourceNotFoundException("User", "id", 999);
 *  Result: "User not found with id: 999"
 */
public class ResourceNotFoundException extends BlogApiException {

    /**
     * Constructs a ResourceNotFoundException with a custom message.
     *
     * @param message the error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a ResourceNotFoundException with a formatted message.
     * Generates message in format: "{resourceName} not found with {fieldName}: {fieldValue}"
     *
     * @param resourceName the name of the resource (e.g., "User", "Post")
     * @param fieldName    the field that was searched (e.g., "id", "email")
     * @param fieldValue   the value that was not found (e.g., 999, "test@test.com")
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
    }
}
