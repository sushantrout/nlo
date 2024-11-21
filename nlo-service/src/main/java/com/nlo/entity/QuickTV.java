package com.nlo.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QuickTV extends BaseEntity {
    private String url;
    private String title;
}
