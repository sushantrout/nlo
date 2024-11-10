package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoriesDTO extends BaseDTO {
    private AttachmentDTO headerImage;
    private List<AttachmentDTO> attachments;
    private Boolean isPublished;
}
