package com.nlo.validation;

import com.nlo.model.TODOListDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TODOListValidation implements Validation<TODOListDTO> {
    @Override
    public void validate(TODOListDTO todoListDTO) throws ValidationException {

    }
}
