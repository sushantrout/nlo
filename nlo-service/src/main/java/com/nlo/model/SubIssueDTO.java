package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubIssueDTO extends BaseDTO {
    private String title;
    private String issueId;
}
