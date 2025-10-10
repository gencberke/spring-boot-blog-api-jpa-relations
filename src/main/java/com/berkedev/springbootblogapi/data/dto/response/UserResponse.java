package com.berkedev.springbootblogapi.data.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    /**
     *  uses for responses to client. never return password info to client
     */

    private Long id;
    private String fullName;
    private String username;
    private LocalDateTime createdAt;
    private String email;
}
