package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class BaseDTO {
    private String id;
    private String title;

    private String createdBy;

    private String updatedBy;

    private OffsetDateTime createdOn;

    private OffsetDateTime updatedOn;

    private Boolean active;

    private Boolean deleted;
}