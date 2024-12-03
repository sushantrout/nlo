package com.nlo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class NewsShare extends BaseEntity {
    private String shareCode;

    @ManyToOne
    private News news;

    @ManyToOne
    private User user;

    private Boolean used;
}
