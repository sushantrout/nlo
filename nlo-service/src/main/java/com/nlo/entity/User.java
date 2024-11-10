package com.nlo.entity;

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

    private String username;

    private String password;
    private String email;

    private String firstName;
    private String lastName;
    private String mobile;

    private String name;

    @Lob
    private String profileImage;

    @ManyToOne
    private Constituency constituency;
}