package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for user login requests.
 * 
 * Contains credentials for authentication:
 * - username: user's username
 * - password: user's plain text password (will be verified against hashed password)
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
