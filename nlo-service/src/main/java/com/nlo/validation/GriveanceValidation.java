package com.nlo.validation;

import com.nlo.model.GrievanceDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class GriveanceValidation implements Validation<GrievanceDTO> {
    @Override
    public void validate(GrievanceDTO grievanceDTO) throws ValidationException {

    }
}
