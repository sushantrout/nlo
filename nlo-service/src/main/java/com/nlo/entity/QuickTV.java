package com.nlo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QuickTV extends BaseEntity {
    @Column(length = 512)
    private String url;
    private String title;
}
