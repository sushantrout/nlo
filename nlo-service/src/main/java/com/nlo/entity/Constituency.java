package com.nlo.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Constituency extends BaseEntity {
    private String title;
}
