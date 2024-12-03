package com.nlo.entity;

import com.nlo.constant.Role;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_users")
public class User {

    @Id
    @Tsid
    private String id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String mobile;

    private String name;

    @Lob
    private String profileImage;

    @ManyToOne
    private Constituency constituency;

    @Enumerated(EnumType.STRING)
    private Role role;

    String memberShipId;

    private String deviceToken;
}