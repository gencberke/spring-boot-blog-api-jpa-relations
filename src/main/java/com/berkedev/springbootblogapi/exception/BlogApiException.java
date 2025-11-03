package com.berkedev.springbootblogapi.exception;

/**
 * Base exception class for Blog API.
 * All custom exceptions extend this class.
 * Extends RuntimeException (unchecked exception) because:
 *  - No need for try-catch everywhere
 *  - More flexible for REST APIs
 *  - Spring @ControllerAdvice handles them automatically
 *
 * @see ResourceNotFoundException
 * @see DuplicateResourceException
 * @see BadRequestException
 */
public class BlogApiException extends RuntimeException {

    /**
     * Constructs a new BlogApiException with the specified message.
     *
     * @param message the error message
     */
    public BlogApiException(String message) {
        super(message);
    }

    /**
     * Constructs a new BlogApiException with the specified message and cause.
     * Useful when wrapping other exceptions.
     *
     * @param message the error message
     * @param cause   the root cause exception
     */
    public BlogApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
