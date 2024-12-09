package com.nlo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRate {
    private String id;
    private String name;
    private String profileImage;
    private Long totalRating;
    private String reward;
    private Long rank;
}
