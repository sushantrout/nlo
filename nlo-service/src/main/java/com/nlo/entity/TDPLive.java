package com.nlo.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TDPLive extends BaseEntity {
    private String title;
    private String url;
}
