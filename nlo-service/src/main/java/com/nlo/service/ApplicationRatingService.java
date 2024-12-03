package com.nlo.service;

import com.nlo.entity.ApplicationBadge;
import com.nlo.entity.ApplicationRating;
import com.nlo.mapper.ApplicationRatingMapper;
import com.nlo.model.*;
import com.nlo.repository.*;
import com.nlo.repository.dbdto.UserReactionSummary;
import com.nlo.validation.ApplicationRatingValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ApplicationRatingService extends BaseServiceImpl<ApplicationRating, ApplicationRatingDTO, ApplicationRatingMapper, ApplicationRatingValidation, ApplicationRatingRepository> {
    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private InfographicsShareRepository infographicsShareRepository;

    @Autowired
    private NewsShareRepository newsShareRepository;

    @Autowired
    private PollResponseRepository pollResponseRepository;

    @Autowired
    private UserRepository userRepository;

    protected ApplicationRatingService(ApplicationRatingRepository repository, ApplicationRatingMapper mapper, ApplicationRatingValidation validation) {
        super(repository, mapper, validation);
    }

    @Transactional
    public List<UserRate> getTopRated(Long limit) {
        ApplicationRating applicationRating = repository.findByDeletedFalseOrDeletedIsNull(PageRequest.of(0, 1)).stream().findFirst().orElse(new ApplicationRating());

        CompletableFuture<List<UserReactionSummary>> reactions = getUserReactionSummaryAsync();
        CompletableFuture<List<UserShareSummary>> newsShares = getNewsShareSummaryAsync();
        CompletableFuture<List<UserShareSummary>> infographicsShares = getInfographicsShareSummaryAsync();

        CompletableFuture.allOf(reactions, newsShares, infographicsShares).join();

        try {
            Set<String> unionSet = Stream.of(
                            reactions.get().stream().map(UserReactionSummary::getUserId).toList(),
                            newsShares.get().stream().map(UserShareSummary::getUserId).toList(),
                            infographicsShares.get().stream().map(UserShareSummary::getUserId).toList()
                    ).flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // Initialize the data map with user details
            List<UserRate> dataMap = userRepository.findUserIdAndNamesByIds(unionSet).stream()
                    .map(user -> new UserRate(user.getId(), user.getName(), user.getImage(), 0L, null))
                    .collect(Collectors.toList());

            updateTotalRatings(dataMap, reactions.get(),
                    UserReactionSummary::getUserId, UserReactionSummary::getPositiveCount, applicationRating.getLikePoint());

            updateTotalRatings(dataMap, reactions.get(),
                    UserReactionSummary::getUserId, UserReactionSummary::getNegativeCount, applicationRating.getDislikePoint());

            updateTotalRatings(dataMap, newsShares.get(),
                    UserShareSummary::getUserId, UserShareSummary::getTotalShares, applicationRating.getSharePoint());

            updateTotalRatings(dataMap, infographicsShares.get(),
                    UserShareSummary::getUserId, UserShareSummary::getTotalShares, applicationRating.getSharePoint());

            // Handle additional rates from another repository
            pollResponseRepository.findTopUsersByTotalRate(null).getContent().stream()
                    .filter(e -> e.getUserId() != null && e.getTotalRate() != null)
                    .forEach(e -> {
                        dataMap.stream()
                                .filter(dm -> dm.getId().equals(e.getUserId()))
                                .findFirst()
                                .ifPresent(dm -> dm.setTotalRating(dm.getTotalRating() + e.getTotalRate()));
                    });

            return dataMap.stream()
                    .sorted(Comparator.comparingLong(UserRate::getTotalRating).reversed())
                    .limit(limit != null ? limit : dataMap.size())
                    .map(e -> {
                        e.setReward(getRewardTitle(applicationRating.getApplicationBadges(), e.getTotalRating()));
                        return e;
                    })
                    .toList();

        } catch (Exception e) {
            log.error(e.getMessage());
            return List.of();
        }

    }

    private String getRewardTitle(List<ApplicationBadge> applicationBadges, Long totalRating) {
        if (applicationBadges == null || totalRating == null) {
            return null; // Handle null input gracefully
        }

        return applicationBadges.stream()
                .filter(badge -> totalRating >= badge.getMinPoint() && totalRating <= badge.getMaxPoint())
                .map(ApplicationBadge::getTitle)
                .findFirst()
                .orElse(null); // Return null if no matching badge is found
    }


    private <T> void updateTotalRatings(List<UserRate> dataMap, List<T> summaries,
                                        Function<T, String> userIdExtractor,
                                        Function<T, Long> valueExtractor, Long rate) {
        summaries.stream()
                .filter(summary -> userIdExtractor.apply(summary) != null && valueExtractor.apply(summary) != null)
                .collect(Collectors.toMap(userIdExtractor, valueExtractor))
                .forEach((userId, value) ->
                        dataMap.stream()
                                .filter(userRate -> userRate.getId().equals(userId))
                                .findFirst()
                                .ifPresent(userRate -> userRate.setTotalRating(userRate.getTotalRating() + (value * Optional.ofNullable(rate).orElse(1L))))
                );
    }

    @Async
    public CompletableFuture<List<UserReactionSummary>> getUserReactionSummaryAsync() {
        List<UserReactionSummary> reactions = reactionRepository.calculateLikeDislikeSummaryForAllUsers();
        return CompletableFuture.completedFuture(reactions);
    }

    @Async
    public CompletableFuture<List<UserShareSummary>> getNewsShareSummaryAsync() {
        List<UserShareSummary> newsShares = newsShareRepository.calculateShareSummaryForAllUsers();
        return CompletableFuture.completedFuture(newsShares);
    }

    @Async
    public CompletableFuture<List<UserShareSummary>> getInfographicsShareSummaryAsync() {
        List<UserShareSummary> infographicsShares = infographicsShareRepository.calculateShareSummaryForAllUsers();
        return CompletableFuture.completedFuture(infographicsShares);
    }
}
