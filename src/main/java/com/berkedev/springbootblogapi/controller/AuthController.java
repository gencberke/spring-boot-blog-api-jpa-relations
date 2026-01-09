package com.berkedev.springbootblogapi.controller;

import com.berkedev.springbootblogapi.data.dto.request.LoginRequest;
import com.berkedev.springbootblogapi.data.dto.request.RegisterRequest;
import com.berkedev.springbootblogapi.data.dto.request.UserCreateRequest;
import com.berkedev.springbootblogapi.data.dto.response.AuthResponse;
import com.berkedev.springbootblogapi.data.entity.User;
import com.berkedev.springbootblogapi.security.JwtService;
import com.berkedev.springbootblogapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication operations.
 * Handles user registration and login endpoints.
 * Returns JWT tokens for successful authentication.
 * Endpoints:
 * - POST /api/auth/register: Register new user account
 * - POST /api/auth/login: Authenticate user and get JWT token
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user account.
     * Creates a new user with hashed password and default USER role.
     * Returns JWT token for immediate authentication after registration.
     * @param registerRequest user registration data
     * @return AuthResponse containing JWT token and success message
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .fullName(registerRequest.getFullName())
                .build();

        userService.create(userCreateRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Authenticate user and get JWT token.
     * Validates username and password credentials.
     * Returns JWT token on successful authentication.
     * @param loginRequest user login credentials
     * @return AuthResponse containing JWT token and success message
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
