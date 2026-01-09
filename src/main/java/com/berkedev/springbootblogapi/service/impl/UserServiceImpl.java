package com.berkedev.springbootblogapi.service.impl;

import com.berkedev.springbootblogapi.data.dto.request.UserCreateRequest;
import com.berkedev.springbootblogapi.data.dto.request.UserUpdateRequest;
import com.berkedev.springbootblogapi.data.dto.response.UserResponse;
import com.berkedev.springbootblogapi.data.entity.User;
import com.berkedev.springbootblogapi.data.mapper.UserMapper;
import com.berkedev.springbootblogapi.data.repository.UserRepository;
import com.berkedev.springbootblogapi.exception.DuplicateResourceException;
import com.berkedev.springbootblogapi.exception.ResourceNotFoundException;
import com.berkedev.springbootblogapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserCreateRequest createRequest) {
        if (userRepository.existsByUsername(createRequest.getUsername())) {
            throw new DuplicateResourceException("Username", createRequest.getUsername());
        }

        if (userRepository.existsByEmail(createRequest.getEmail())) {
            throw new DuplicateResourceException("Email", createRequest.getEmail());
        }

        User user = userMapper.toEntity(createRequest);
        
        // Hash password with BCrypt before saving
        String hashedPassword = passwordEncoder.encode(createRequest.getPassword());
        user.setPassword(hashedPassword);
        
        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    /**
     * JPA DIRTY CHECKING :
     * JPA tracks changes on managed entities and auto-updates on transaction commit.
     * But BEST PRACTICE is to use explicit save() for clarity and maintainability.
     * Why explicit save()?
     * - Clear and readable code
     * - Spring Boot recommendation
     */
    @Override
    public UserResponse update(Long id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if(updateRequest.getEmail() != null &&
                !updateRequest.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(updateRequest.getEmail())) {
            throw new DuplicateResourceException("Email", updateRequest.getEmail());
        }

        userMapper.updateEntityFromRequest(updateRequest, user);

        User updated = userRepository.save(user);

        return userMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return userMapper.toResponse(user);
    }
}
