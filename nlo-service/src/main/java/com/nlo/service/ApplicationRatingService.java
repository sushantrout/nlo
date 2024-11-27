package com.nlo.service;

import com.nlo.entity.ApplicationRating;
import com.nlo.mapper.ApplicationRatingMapper;
import com.nlo.model.ApplicationRatingDTO;
import com.nlo.repository.ApplicationRatingRepository;
import com.nlo.validation.ApplicationRatingValidation;
import org.springframework.stereotype.Service;

@Service
public class ApplicationRatingService extends BaseServiceImpl<ApplicationRating, ApplicationRatingDTO, ApplicationRatingMapper, ApplicationRatingValidation, ApplicationRatingRepository> {
    protected ApplicationRatingService(ApplicationRatingRepository repository, ApplicationRatingMapper mapper, ApplicationRatingValidation validation) {
        super(repository, mapper, validation);
    }
}
