package com.berkedev.springbootblogapi.exception;

/**
 * Exception thrown when client sends invalid data or violates business rules.
 * Results in HTTP 400 BAD REQUEST response.
 * use cases:
 *  - Invalid parameters (e.g., negative age)
 *  - Business logic violations (e.g., publishing already published post)
 *  - Invalid state transitions
 *  - Invalid date ranges
 * Example usage:
 * throw new BadRequestException("Post is already published");
 * throw new BadRequestException("Age cannot be negative");
 * throw new BadRequestException("Start date must be before end date");
 */
public class BadRequestException extends BlogApiException {

    /**
     * Constructs a BadRequestException with the specified message.
     *
     * @param message the error message describing what is wrong with the request
     */
    public BadRequestException(String message) {
        super(message);
    }
}
