package com.nlo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nlo.constant.GrantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String phoneNo;

    // Added by Arup as part of rewrite
    private String username;
    private String mobile;
    private String password;
    private String otp;
    @JsonProperty("grant_type")
    private GrantType grantType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("membership_id")
    private String membershipId;

}