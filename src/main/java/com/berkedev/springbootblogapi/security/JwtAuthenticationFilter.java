package com.berkedev.springbootblogapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter.
 * Intercepts every HTTP request and validates JWT token from Authorization header.
 * If token is valid, sets authentication in SecurityContext.
 * Filter Flow:
 * 1. Extract JWT token from Authorization header
 * 2. Validate token signature and expiration
 * 3. Load user details from database
 * 4. Set authentication in SecurityContext
 * 5. Continue filter chain
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        // 1. Get Authorization header from request
        final String authHeader = request.getHeader("Authorization");
        
        // 2. Check if header exists and starts with "Bearer "
        // If not, skip JWT authentication (public endpoint or other auth method)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 3. Extract JWT token by removing "Bearer " prefix (7 characters)
        final String jwt = authHeader.substring(7);
        
        // 4. Extract username from JWT token
        final String username = jwtService.extractUsername(jwt);
        
        // 5. Check if username exists and user is not already authenticated
        // SecurityContext.getAuthentication() == null means user hasn't been authenticated yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 6. Load user details from database using username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // 7. Validate token (check signature and expiration)
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // 8. Create authentication token with user details and authorities
                // null credentials because we don't need password after authentication
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,                    // Principal: authenticated user
                        null,                           // Credentials: password not needed
                        userDetails.getAuthorities()    // Authorities: user roles
                );
                
                // 9. Set additional request details (IP address, session ID, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // 10. Set authentication in SecurityContext
                // This tells Spring Security that user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 11. Continue filter chain to next filter or controller
        filterChain.doFilter(request, response);
    }
}
