package com.nlo.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ApplicationBadge extends BaseEntity {
    private String title;
    private Long minPoint;
    private Long maxPoint;
}
