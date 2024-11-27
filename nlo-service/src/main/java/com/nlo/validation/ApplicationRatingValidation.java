package com.nlo.validation;

import com.nlo.model.ApplicationRatingDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRatingValidation implements Validation<ApplicationRatingDTO> {
    @Override
    public void validate(ApplicationRatingDTO applicationRatingDTO) throws ValidationException {

    }
}
