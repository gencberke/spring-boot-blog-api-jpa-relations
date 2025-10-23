package com.berkedev.springbootblogapi.service;

import com.berkedev.springbootblogapi.data.dto.request.UserCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.UserUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserCreateRequest request);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    UserResponse update(Long id, UserUpdateRequest request);  // ← YENİ!

    void delete(Long id);

    UserResponse getByUsername(String username);

    UserResponse getByEmail(String email);
}
