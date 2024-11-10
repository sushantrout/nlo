package com.nlo.validation;

import com.nlo.model.StoriesDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;


@Component
public class StoriesValidation implements Validation<StoriesDTO> {

    @Override
    public void validate(StoriesDTO storiesDTO) throws ValidationException {
        if (storiesDTO.getTitle() == null || storiesDTO.getTitle().isEmpty()) {
            throw new ValidationException("Title is required");
        }
    }
}
