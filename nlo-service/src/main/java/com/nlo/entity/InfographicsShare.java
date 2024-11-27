package com.nlo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InfographicsShare extends BaseEntity {
    @Column(unique = true)
    private String shareCode;

    @ManyToOne
    private Infographics infographics;

    @ManyToOne
    private User user;

    private Boolean used;
}
