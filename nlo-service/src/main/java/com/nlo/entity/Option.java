package com.nlo.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Option extends BaseEntity {
    private String title;
    private Integer sortOrder;
}
