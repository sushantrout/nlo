package com.nlo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InfographicsShare extends BaseEntity {
    private String shareCode;

    @ManyToOne
    private Infographics infographics;

    @ManyToOne
    private User user;

    private Boolean used;
}
