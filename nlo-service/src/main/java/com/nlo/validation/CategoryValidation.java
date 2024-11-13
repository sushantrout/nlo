package com.nlo.validation;

import com.nlo.model.CategoryDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidation implements Validation<CategoryDTO> {
    @Override
    public void validate(CategoryDTO categoryDTO) throws ValidationException {

    }
}
