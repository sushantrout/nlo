package com.nlo.controller;

import com.nlo.entity.User;
import com.nlo.model.AuthRequest;
import com.nlo.model.AuthResponse;
import com.nlo.repository.UserRepository;
import com.nlo.security.JwtService;
import com.nlo.security.MyUserDetailsService;
import com.nlo.service.AuthService;
import com.nlo.service.OtpService;
import com.nlo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }


    @GetMapping(value = "request-otp/{phoneNo}")
    public Map<String, Object> getOtp(@PathVariable String phoneNo) {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            //generate OTP
            String otp = otpService.generateOtp(phoneNo);
            returnMap.put("otp", otp);
            returnMap.put("status", "success");
            returnMap.put("message", "Otp sent successfully");
        } catch (Exception e) {
            returnMap.put("status", "failed");
            returnMap.put("message", e.getMessage());
        }

        return returnMap;
    }

    @PostMapping(value = "verify-otp")
    @Transactional
    public Map<String, Object> verifyOtp(@RequestBody AuthRequest authenticationRequest) {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            User user = userService.findByMobile(authenticationRequest);
            //verify otp
            if (authenticationRequest.getOtp().equals(otpService.getCacheOtp(authenticationRequest.getPhoneNo()))) {
                AuthResponse authResponse = authService.getAuthResponseFromUser(user);
                returnMap.put("status", "success");
                returnMap.put("message", "Otp verified successfully");
                returnMap.put("access_token", authResponse.getAccessToken());
                returnMap.put("expires_in", authResponse.getExpiresIn());
                returnMap.put("refresh_token", authResponse.getRefreshToken());
                returnMap.put("refresh_token_expires_in", authResponse.getRefreshTokenExpiresIn());
                returnMap.put("token_type", authResponse.getTokenType());
                otpService.clearOtp(authenticationRequest.getPhoneNo());
            } else {
                returnMap.put("status", "success");
                returnMap.put("message", "Otp is either expired or incorrect");
            }

        } catch (Exception e) {
            returnMap.put("status", "failed");
            returnMap.put("message", e.getMessage());
        }

        return returnMap;
    }

    //create auth token
    public String createAuthenticationToken(AuthRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNo(), "")
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getPhoneNo());
        return jwtTokenUtil.generateToken(userDetails);
    }


    @GetMapping(value = "request-otp/by-member-ship/{memberShipId}")
    public Map<String, Object> getOtpByMemberShipId(@PathVariable String memberShipId) {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            //generate OTP
            return otpService.getOtpByMemberShipId(memberShipId);

        } catch (Exception e) {
            returnMap.put("status", "failed");
            returnMap.put("message", e.getMessage());
        }

        return returnMap;
    }
}