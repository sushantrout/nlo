package com.nlo.validation;

import com.nlo.model.PollDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PollValidation implements Validation<PollDTO> {
    @Override
    public void validate(PollDTO pollDTO) throws ValidationException {

    }
}
