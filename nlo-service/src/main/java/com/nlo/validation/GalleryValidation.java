package com.nlo.validation;

import com.nlo.model.GalleryDTO;
import org.springframework.stereotype.Component;

@Component
public class GalleryValidation implements Validation<GalleryDTO> {
    @Override
    public void validate(GalleryDTO galleryDTO) {
        if (galleryDTO.getTitle() == null || galleryDTO.getTitle().isEmpty()) {
            throw new RuntimeException("Title is required");
        }
    }
}