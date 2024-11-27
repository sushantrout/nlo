package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationRatingDTO extends BaseDTO {
    Long likePoint;
    Long dislikePoint;
    Long sharePoint;
    List<ApplicationBadgeDTO> applicationBadges;
}
