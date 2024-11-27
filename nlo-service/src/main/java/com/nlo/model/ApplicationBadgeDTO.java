package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationBadgeDTO extends BaseDTO {
    private Long minPoint;
    private Long maxPoint;
}
