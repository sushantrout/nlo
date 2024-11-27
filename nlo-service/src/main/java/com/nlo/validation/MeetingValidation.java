package com.nlo.validation;

import com.nlo.model.MeetingDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class MeetingValidation implements Validation<MeetingDTO> {
    @Override
    public void validate(MeetingDTO meetingDTO) throws ValidationException {

    }
}
