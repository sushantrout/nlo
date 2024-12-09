package com.nlo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrentUserRate extends UserRate {
    private Long dailyPont;

    public CurrentUserRate(String id, String name, String profileImage, Long totalRating, String reward, Long rank) {
        super(id, name, profileImage, totalRating, reward, rank);
    }
}
