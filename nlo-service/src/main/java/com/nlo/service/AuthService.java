package com.nlo.service;

import com.google.common.base.Preconditions;
import com.nlo.entity.User;
import com.nlo.exception.UnAuthorizedException;
import com.nlo.model.AuthRequest;
import com.nlo.model.AuthResponse;
import com.nlo.model.MemberShipDTO;
import com.nlo.model.UserDto;
import com.nlo.repository.UserRepository;
import com.nlo.security.JwtService;
import com.nlo.util.SMSUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Qualifier("username-password-manager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConstituencyService constituencyService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SMSUtil smsUtil;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private static final Integer OTP_EXPIRE_MIN = 5;

    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        Preconditions.checkNotNull(request.getGrantType(), "grantType cannot be null");
        return switch (request.getGrantType()) {
            case PASSWORD -> authenticateUsingPassword(request);
            case REFRESH_TOKEN -> authenticateUsingRefreshToken(request);
            case REQUEST_OTP -> authenticateUsingRequestOtp(request);
            case VERIFY_OTP -> authenticateUsingVerifyOtp(request);
            case MEMBERSHIP_ID -> authenticateUsingMembershipId(request);
        };
    }

    private AuthResponse authenticateUsingPassword(AuthRequest request) {
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getUsername()), "Username cannot be blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getPassword()), "Password cannot be blank");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        return getAuthResponseFromUser(user);
    }

    private AuthResponse authenticateUsingRefreshToken(AuthRequest request) {
        String refreshToken = request.getRefreshToken();
        Preconditions.checkArgument(StringUtils.isNotBlank(refreshToken), "Refresh token cannot be blank");
        String username = jwtService.extractUsername(refreshToken);
        if (StringUtils.isBlank(username)) {
            throw new UnAuthorizedException("Invalid refresh token");
        }

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UnAuthorizedException("User not found"));

        if (!jwtService.isTokenExpired(refreshToken)) {
            return getAuthResponseFromUser(user);
        } else {
            throw new UnAuthorizedException("Refresh token expired");
        }
    }

    public AuthResponse authenticateUsingRequestOtp(AuthRequest request) {
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getMobile()), "PhoneNo cannot be blank");
        String otp = otpService.generateOtp(request.getMobile());
        smsUtil.sendMessage(otp, request.getMobile());
        return AuthResponse.builder()
                .phoneNo(request.getMobile())
                //.otp(otp)
                .otpExpiresIn(OTP_EXPIRE_MIN)
                .build();
    }

    private AuthResponse authenticateUsingVerifyOtp(AuthRequest request) {
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getMobile()), "PhoneNo cannot be blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getOtp()), "Otp cannot be blank");
        Preconditions.checkArgument(StringUtils.equals(request.getOtp(), otpService.getCacheOtp(request.getMobile())),
                "Otp is either expired or incorrect");
        User user = userRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> new UnAuthorizedException("User not found"));

        return getAuthResponseFromUser(user);
    }

    private AuthResponse authenticateUsingMembershipId(AuthRequest request) {
        String membershipId = request.getMembershipId();
        Preconditions.checkArgument(StringUtils.isNotBlank(membershipId), "MembershipId cannot be blank");
        String mobileNumber = userService.getMobileByMembershipId(membershipId);
        if (StringUtils.isBlank(mobileNumber)) {
            MemberShipDTO memberShipDTO = new MemberShipDTO();
            memberShipDTO.setMembershipId(membershipId);
            MemberShipDTO body = restTemplate.postForEntity("https://www.mypartydashboard.com/BSA2/WebService/Cadre/getCadreDetailsByMembershipId",
                    memberShipDTO,
                    MemberShipDTO.class
            ).getBody();
            UserDto userDto = new UserDto();
            userDto.setMobile(body.getMobileNo());
            userDto.setMemberShipId(membershipId);
            userDto.setProfileImage(body.getImgUrl());
            userDto.setName(body.getCadreName());
            String constituency = body.getConstituency();
            if(StringUtils.isNotBlank(constituency)) {
                try {
                    userDto.setConstituencyDTO(constituencyService.getConstituencyByName(constituency));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            mobileNumber = userService.saveUser(userDto).getMobile();
        }
        String otp = otpService.generateOtp(mobileNumber);

        return AuthResponse.builder()
                .phoneNo(mobileNumber)
                .otp(otp)
                .otpExpiresIn(OTP_EXPIRE_MIN)
                .build();
    }

    private AuthResponse getAuthResponseFromUser(User user) {
        // Convert it to UserDetails
        String password = Optional.ofNullable(user.getPassword()).orElse("");
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(user.getUsername(), password, List.of());

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

}