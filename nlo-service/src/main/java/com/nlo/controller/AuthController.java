package com.nlo.controller;

import com.nlo.constant.GrantType;
import com.nlo.model.AuthRequest;
import com.nlo.model.AuthResponse;
import com.nlo.service.AuthService;
import com.nlo.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping(path = "/token")
    public ResponseEntity<AuthResponse> getToken(@RequestParam(value = "grant_type") GrantType grantType,
                                                 @RequestParam(required = false) String password,
                                                 @RequestParam(required = false) String username,
                                                 @RequestParam(required = false) String mobile,
                                                 @RequestParam(required = false) String otp,
                                                 @RequestParam(value = "refresh_token", required = false) String refreshToken,
                                                 @RequestParam(value = "membership_id", required = false) String membershipId) {
        AuthRequest authRequest = AuthRequest.builder()
                .password(password)
                .mobile(mobile)
                .otp(otp)
                .refreshToken(refreshToken)
                .username(username)
                .grantType(grantType)
                .membershipId(membershipId)
                .build();
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

}