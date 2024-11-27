package com.nlo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Meeting extends BaseEntity {
    private String title;
    private LocalDateTime meetingTime;

    @Column(length = 10000)
    private String description;
    private String communicationType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "constituency_id", nullable = false)
    private Constituency constituency;
}