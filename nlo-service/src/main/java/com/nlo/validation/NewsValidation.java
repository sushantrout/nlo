package com.nlo.validation;

import com.nlo.model.NewsDTO;
import org.springframework.stereotype.Component;

@Component
public class NewsValidation implements Validation<NewsDTO> {
    @Override
    public void validate(NewsDTO newsDTO) {
    }
}