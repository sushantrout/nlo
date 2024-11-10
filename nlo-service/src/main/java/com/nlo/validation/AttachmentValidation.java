package com.nlo.validation;

import com.nlo.model.AttachmentDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class AttachmentValidation implements Validation<AttachmentDTO> {
    @Override
    public void validate(AttachmentDTO attachmentDTO) throws ValidationException {

    }
}
