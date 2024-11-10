package com.nlo.validation;

import com.nlo.model.IssueDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class IssueValidation implements Validation<IssueDTO> {
    @Override
    public void validate(IssueDTO issueDTO) throws ValidationException {

    }
}
