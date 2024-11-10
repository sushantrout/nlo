package com.nlo.validation;

import com.nlo.model.ConstituencyDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ConstituencyValidation implements Validation<ConstituencyDTO> {
    @Override
    public void validate(ConstituencyDTO constituencyDTO) throws ValidationException {

    }
}
