package com.nlo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubIssue extends BaseEntity {
    private String title;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;
}
