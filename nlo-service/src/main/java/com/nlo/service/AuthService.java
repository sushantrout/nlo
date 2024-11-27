package com.nlo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlo.entity.User;
import com.nlo.model.AuthRequest;
import com.nlo.model.AuthResponse;
import com.nlo.repository.UserRepository;
import com.nlo.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Qualifier("username-password-manager")
    private AuthenticationManager authenticationManager;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getEmail())
                .orElseThrow();
        return getAuthResponseFromUser(user);
    }

    @Transactional
    public AuthResponse getAuthResponseFromUser(User user) {
        // Convert it to UserDetails
        String password = Optional.ofNullable(user.getPassword()).orElse("");
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), password, List.of());

        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .expiresIn(jwtExpiration / 1000)
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshExpiration / 1000)
                .tokenType("Bearer")
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = this.userRepository.findByUsername(userEmail)
                    .orElseThrow();
            // Convert it to UserDetails
            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .expiresIn(jwtExpiration / 1000)
                        .refreshToken(refreshToken)
                        .refreshTokenExpiresIn(refreshExpiration / 1000)
                        .tokenType("Bearer")
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


}