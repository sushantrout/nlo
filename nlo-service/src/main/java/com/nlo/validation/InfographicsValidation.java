package com.nlo.validation;

import com.nlo.model.InfographicsDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class InfographicsValidation implements Validation<InfographicsDTO> {
    @Override
    public void validate(InfographicsDTO infographicsDTO) throws ValidationException {

    }
}
