package com.nlo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRate {
    private String id;
    private String name;
    private String profileImage;
    private Long totalRating;
    private String reward;
}
