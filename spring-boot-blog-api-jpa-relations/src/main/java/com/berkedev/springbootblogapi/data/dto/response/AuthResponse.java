package com.berkedev.springbootblogapi.data.dto.response;

import lombok.*;

/**
 * DTO for authentication responses.
 * 
 * Returned after successful login or registration.
 * Contains JWT token that client should include in Authorization header
 * for subsequent authenticated requests.
 * 
 * Example usage:
 * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private String message;
}
