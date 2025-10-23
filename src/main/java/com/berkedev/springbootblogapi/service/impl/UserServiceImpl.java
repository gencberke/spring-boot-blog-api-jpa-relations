package com.berkedev.springbootblogapi.service.impl;

import com.berkedev.springbootblogapi.data.dto.request.UserCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.UserUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.UserResponse;
import com.berkedev.springbootblogapi.data.entity.User;
import com.berkedev.springbootblogapi.data.mapper.UserMapper;
import com.berkedev.springbootblogapi.data.repository.UserRepository;
import com.berkedev.springbootblogapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponse create(UserCreateRequest createRequest) {
        if (userRepository.existsByUsername(createRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + createRequest.getUsername());
        }

        if (userRepository.existsByEmail(createRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + createRequest.getUsername());
        }

        User user = userMapper.toEntity(createRequest);
        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if(updateRequest.getEmail() != null &&
                !updateRequest.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(updateRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + updateRequest.getEmail());
        }

        userMapper.updateEntityFromRequest(updateRequest, user);

        User updated = userRepository.save(user);

        return userMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return userMapper.toResponse(user);
    }
}
