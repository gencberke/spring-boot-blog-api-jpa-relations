package com.berkedev.springbootblogapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security Configuration.
 * 
 * Configures:
 * - JWT authentication filter
 * - Endpoint authorization rules
 * - Password encoding (BCrypt)
 * - Stateless session management
 * 
 * Security Features:
 * - JWT token-based authentication
 * - Role-based authorization (USER, ADMIN)
 * - BCrypt password hashing
 * - Stateless architecture (no sessions)
 * - CSRF disabled (using JWT)
 * 
 * Note: AuthenticationProvider is autoconfigured by Spring Security 6
 * when UserDetailsService and PasswordEncoder beans are present.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Configures HTTP security (filter chain and authorization rules).
     * 
     * Security Rules:
     * - Public endpoints: /api/auth/**, GET /api/posts/**, GET /api/categories/**, GET /api/tags/**
     * - Admin endpoints: /api/users/**, POST/PUT/DELETE /api/categories/**, POST/PUT/DELETE /api/tags/**
     * - Authenticated endpoints: All other endpoints require authentication
     * 
     * @param http HttpSecurity configuration object
     * @return SecurityFilterChain configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (we're using JWT tokens, not cookies)
                .csrf(AbstractHttpConfigurer::disable)
                
                // Configure endpoint authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication required
                        .requestMatchers("/api/auth/**").permitAll()                    // Login, Register
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()   // Read posts
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()  // Read categories
                        .requestMatchers(HttpMethod.GET, "/api/tags/**").permitAll()    // Read tags
                        
                        // Admin-only endpoints
                        .requestMatchers("/api/users/**").hasRole("ADMIN")              // User management
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tags/**").hasRole("ADMIN")
                        
                        // All other endpoints require authentication (any role)
                        .anyRequest().authenticated()
                )
                
                // Configure session management (stateless - no sessions)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                
                // Add JWT filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Password encoder bean (BCrypt).
     * 
     * BCrypt features:
     * - One-way hashing (cannot be reversed)
     * - Salt generation (different hash for same password)
     * - Slow by design (prevents brute force attacks)
     * 
     * Spring Security 6 automatically uses this bean for authentication.
     * 
     * @return PasswordEncoder BCrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager bean.
     * 
     * Used by AuthController for login authentication.
     * Spring Security 6 automatically configures this when UserDetailsService
     * and PasswordEncoder beans are present.
     * 
     * @param config AuthenticationConfiguration Spring's authentication configuration
     * @return AuthenticationManager authentication manager
     * @throws Exception if configuration fails
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
            throws Exception {
        return config.getAuthenticationManager();
    }
}
