package com.nlo.validation;

import com.nlo.model.EBookDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class EBookValidation implements Validation<EBookDTO> {
    @Override
    public void validate(EBookDTO eBookDTO) throws ValidationException {

    }
}
