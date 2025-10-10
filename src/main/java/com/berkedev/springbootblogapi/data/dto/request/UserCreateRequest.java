package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserCreateRequest {

    /**
     *  only needed in user persist operations, fields below enough to create a new user
     */

    @NotBlank(message = "Username required")
    @Size(min = 5, max = 20, message = "Username must be between 5 to 20 characters")
    String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;
    String fullName;
}
