package com.nlo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InfographicsViewDetails extends BaseEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "infographics_id")
    private Infographics infographics;
}
