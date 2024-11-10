package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class GrievanceDTO extends BaseDTO {
    private String title;
    private String issueId;
    private String subIssueId;
    private String subject;
    private String location;
    private List<AttachmentDTO> attachments;
}
