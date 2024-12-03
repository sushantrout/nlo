package com.nlo.service;

import com.nlo.entity.ApplicationRating;
import com.nlo.entity.PollResponse;
import com.nlo.mapper.ApplicationRatingMapper;
import com.nlo.model.ApplicationRatingDTO;
import com.nlo.repository.ApplicationRatingRepository;
import com.nlo.repository.PollResponseRepository;
import com.nlo.repository.ReactionRepository;
import com.nlo.repository.dbdto.UserReactionSummary;
import com.nlo.validation.ApplicationRatingValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApplicationRatingService extends BaseServiceImpl<ApplicationRating, ApplicationRatingDTO, ApplicationRatingMapper, ApplicationRatingValidation, ApplicationRatingRepository> {
    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PollResponseRepository pollResponseRepository;

    protected ApplicationRatingService(ApplicationRatingRepository repository, ApplicationRatingMapper mapper, ApplicationRatingValidation validation) {
        super(repository, mapper, validation);
    }

    public void getTopRated(Long limit) {
        List<UserReactionSummary> userReactionSummaries = reactionRepository.calculateLikeDislikeSummaryForAllUsers();
        userReactionSummaries.forEach(e -> {
            log.info("{} => {} {}", e.getUserId(), e.getPositiveCount(), e.getNegativeCount());
        });


    }
}
