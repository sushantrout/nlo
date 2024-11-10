package com.nlo.validation;

import com.nlo.model.SubIssueDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class SubIssueValidation implements Validation<SubIssueDTO>{
    @Override
    public void validate(SubIssueDTO subIssueDTO) throws ValidationException {

    }
}
