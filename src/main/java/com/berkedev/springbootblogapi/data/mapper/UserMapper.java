package com.berkedev.springbootblogapi.data.mapper;

import com.berkedev.springbootblogapi.data.dto.request.UserCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.UserUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.UserResponse;
import com.berkedev.springbootblogapi.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // for nested mapper injection on other mappers
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null)
            return null;

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toEntity(UserCreateRequest createRequest) {
        if (createRequest == null)
            return null;

        return User.builder()
                .email(createRequest.getEmail())
                .fullName(createRequest.getFullName())
                .username(createRequest.getUsername())
                .password(createRequest.getPassword())
                .build();
    }

    public void updateEntityFromRequest(UserUpdateRequest updateRequest, User user) {
        if (updateRequest == null || user == null)
            return;

        if (updateRequest.getEmail() != null)
            user.setEmail(updateRequest.getEmail());

        if (updateRequest.getPassword() != null)
            user.setPassword(updateRequest.getPassword());

        if (updateRequest.getFullName() != null)
            user.setFullName(updateRequest.getFullName());
    }
}
