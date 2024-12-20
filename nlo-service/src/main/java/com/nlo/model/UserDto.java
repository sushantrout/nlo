package com.nlo.model;

import com.nlo.constant.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String name;
    private String mobile;
    private String profileImage;
    private ConstituencyDTO constituencyDTO;
    private Role role;
    private String memberShipId;
    private Long totalShare;
}