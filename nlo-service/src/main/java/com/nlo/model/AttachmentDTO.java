package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentDTO extends BaseDTO{
    private String url;
    private String contentType;
    private String extension;
    private String thumbnailUrl;
    private String thumbnailMediaType;
    private String staticUrl;
    private String description;
}
