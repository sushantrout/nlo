package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EBookDTO extends BaseDTO {
    private String description;
    private AttachmentDTO attachmentDTO;
    private String staticURL;
}
