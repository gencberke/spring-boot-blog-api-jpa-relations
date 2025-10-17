package com.berkedev.springbootblogapi.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    /**
     *  on user update operations, updatable fields
     *  as a business rule, username can't be changed
     *  nullable for partial update
     */

    @Email(message = "Email must be valid")
    String email;
    String fullName;

    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;
}
