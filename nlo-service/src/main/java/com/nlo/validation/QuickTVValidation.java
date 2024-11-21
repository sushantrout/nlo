package com.nlo.validation;

import com.nlo.model.QuickTVDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class QuickTVValidation implements Validation<QuickTVDTO> {
    @Override
    public void validate(QuickTVDTO quickTVDTO) throws ValidationException {

    }
}
