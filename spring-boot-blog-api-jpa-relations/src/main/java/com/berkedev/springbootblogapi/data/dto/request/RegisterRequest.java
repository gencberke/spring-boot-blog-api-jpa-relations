package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO for user registration requests.
 * 
 * Contains user information needed for creating a new account:
 * - username: unique identifier for login
 * - email: unique email address
 * - password: user's chosen password (will be hashed with BCrypt)
 * - fullName: optional display name
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 20, message = "Username must be between 5 to 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String fullName;
}
