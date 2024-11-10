package com.nlo.model;

import com.nlo.model.AttachmentDTO;
import com.nlo.model.BaseDTO;
import com.nlo.model.ReactionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GalleryDTO extends BaseDTO {
    private String title; // Title of the gallery
    private String description; // Description of the gallery
    private LocalDateTime createdDate; // When the gallery was created

    private List<AttachmentDTO> attachments = new ArrayList<>();
    private List<ReactionDTO> reactions = new ArrayList<>();

}