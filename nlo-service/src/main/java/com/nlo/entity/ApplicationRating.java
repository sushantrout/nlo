package com.nlo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ApplicationRating extends BaseEntity {
    private Long likePoint;
    private Long dislikePoint;
    private Long sharePoint;
    @OneToMany(fetch = FetchType.EAGER)
    private List<ApplicationBadge> applicationBadges = new ArrayList<>();
}
