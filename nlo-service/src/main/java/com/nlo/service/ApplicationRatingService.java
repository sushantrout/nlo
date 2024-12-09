package com.nlo.service;

import com.amazonaws.util.CollectionUtils;
import com.nlo.constant.TimeSpan;
import com.nlo.entity.ApplicationBadge;
import com.nlo.entity.ApplicationRating;
import com.nlo.mapper.ApplicationRatingMapper;
import com.nlo.model.*;
import com.nlo.repository.*;
import com.nlo.repository.dbdto.UserReactionSummary;
import com.nlo.validation.ApplicationRatingValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
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

    @Autowired
    private ViewDetailRepository viewDetailRepository;

    @Lazy
    @Autowired
    private UserService userService;

    @Autowired
    private InfographicsViewDetailsRepository infographicsViewDetailsRepository;

    protected ApplicationRatingService(ApplicationRatingRepository repository, ApplicationRatingMapper mapper, ApplicationRatingValidation validation) {
        super(repository, mapper, validation);
    }

    @Transactional
    public List<UserRate> getTopRated(Long limit, TimeSpan timeSpan, String userId) {
        ApplicationRating applicationRating = repository.findByDeletedFalseOrDeletedIsNull(PageRequest.of(0, 1)).stream().findFirst().orElse(new ApplicationRating());

        OffsetDateTime startTime = getStartTimeForTimeSpan(timeSpan);

        CompletableFuture<List<UserReactionSummary>> reactions = getUserReactionSummaryAsync(startTime, userId);
        CompletableFuture<List<UserShareSummary>> newsShares = getNewsShareSummaryAsync(startTime, userId);
        CompletableFuture<List<UserShareSummary>> infographicsShares = getInfographicsShareSummaryAsync(startTime, userId);
        CompletableFuture<List<UserViewSummary>> totalViewSummery = getTotalViewSummery(startTime, userId);
        CompletableFuture<List<UserViewSummary>> totalInfoViewSummery = getTotalInfographicsSummery(startTime, userId);

        CompletableFuture.allOf(reactions, newsShares, infographicsShares).join();

        try {
            Set<String> unionSet = Stream.of(
                            reactions.get().stream().map(UserReactionSummary::getUserId).toList(),
                            newsShares.get().stream().map(UserShareSummary::getUserId).toList(),
                            infographicsShares.get().stream().map(UserShareSummary::getUserId).toList(),
                            totalViewSummery.get().stream().map(UserViewSummary::getUserId).toList(),
                            totalInfoViewSummery.get().stream().map(UserViewSummary::getUserId).toList()
                    ).flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // Initialize the data map with user details
            List<UserRate> dataMap = userRepository.findUserIdAndNamesByIds(unionSet).stream()
                    .map(user -> new UserRate(user.getId(), user.getName(), user.getImage(), 0L, null, null))
                    .collect(Collectors.toList());

            updateTotalRatings(dataMap, reactions.get(),
                    UserReactionSummary::getUserId, UserReactionSummary::getPositiveCount, applicationRating.getLikePoint());

            updateTotalRatings(dataMap, reactions.get(),
                    UserReactionSummary::getUserId, UserReactionSummary::getNegativeCount, applicationRating.getDislikePoint());

            updateTotalRatings(dataMap, newsShares.get(),
                    UserShareSummary::getUserId, UserShareSummary::getTotalShares, applicationRating.getSharePoint());

            updateTotalRatings(dataMap, infographicsShares.get(),
                    UserShareSummary::getUserId, UserShareSummary::getTotalShares, applicationRating.getSharePoint());

            updateTotalRatings(dataMap, totalViewSummery.get(), UserViewSummary::getUserId, UserViewSummary::getTotalViews, applicationRating.getViewPoint());
            updateTotalRatings(dataMap, totalInfoViewSummery.get(), UserViewSummary::getUserId, UserViewSummary::getTotalViews, applicationRating.getViewPoint());

            // Handle additional rates from another repository
            pollResponseRepository.findTopUsersByTotalRate(null, startTime).getContent().stream()
                    .filter(e -> e.getUserId() != null && e.getTotalRate() != null)
                    .forEach(e -> dataMap.stream()
                            .filter(dm -> dm.getId().equals(e.getUserId()))
                            .findFirst()
                            .ifPresent(dm -> dm.setTotalRating(dm.getTotalRating() + e.getTotalRate())));

            List<UserRate> userRates = dataMap.stream()
                    .sorted(Comparator.comparingLong(UserRate::getTotalRating).reversed())
                    .limit(limit != null ? limit : dataMap.size())
                    .map(e -> {
                        e.setReward(getRewardTitle(applicationRating.getApplicationBadges(), e.getTotalRating()));
                        return e;
                    })
                    .toList();

            AtomicLong rank = new AtomicLong(0L);
            userRates.forEach(e -> {
                e.setRank(rank.incrementAndGet());
            });
            return userRates;

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
    public CompletableFuture<List<UserReactionSummary>> getUserReactionSummaryAsync(OffsetDateTime startTime, String userId) {
        List<UserReactionSummary> reactions = reactionRepository.calculateLikeDislikeSummaryForAllUsersAfter(startTime, userId);
        return CompletableFuture.completedFuture(reactions);
    }

    @Async
    public CompletableFuture<List<UserShareSummary>> getNewsShareSummaryAsync(OffsetDateTime startTime, String userId) {
        List<UserShareSummary> newsShares = newsShareRepository.calculateShareSummaryForAllUsersAfter(startTime, userId);
        return CompletableFuture.completedFuture(newsShares);
    }

    @Async
    public CompletableFuture<List<UserShareSummary>> getInfographicsShareSummaryAsync(OffsetDateTime startTime, String userId) {
        List<UserShareSummary> infographicsShares = infographicsShareRepository.calculateShareSummaryForAllUsersAfter(startTime, userId);
        return CompletableFuture.completedFuture(infographicsShares);
    }

    @Async
    public CompletableFuture<List<UserViewSummary>> getTotalViewSummery(OffsetDateTime startTime, String userId) {
        List<UserViewSummary> userViewSummaries = viewDetailRepository.countGroupByUserIdAfter(startTime, userId);
        return CompletableFuture.completedFuture(userViewSummaries);
    }

    @Async
    private CompletableFuture<List<UserViewSummary>> getTotalInfographicsSummery(OffsetDateTime startTime, String userId) {
        List<UserViewSummary> userViewSummaries = infographicsViewDetailsRepository.countGroupByUserIdAfter(startTime, userId);
        return CompletableFuture.completedFuture(userViewSummaries);
    }

    private OffsetDateTime getStartTimeForTimeSpan(TimeSpan timeSpan) {
        LocalDateTime localDateTime;
        switch (timeSpan) {
            case TODAY:
                localDateTime = LocalDateTime.now().toLocalDate().atStartOfDay(); // Start of today
                break;
            case WEEK:
                localDateTime = LocalDateTime.now().with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay(); // Start of current week
                break;
            case MONTH:
                localDateTime = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay(); // Start of current month
                break;
            case YEAR:
                localDateTime = LocalDateTime.now().withDayOfYear(1).toLocalDate().atStartOfDay(); // Start of current year
                break;
            case ALL:
                return  OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
            default:
                throw new IllegalArgumentException("Unsupported time span: " + timeSpan);
        }
        // Convert LocalDateTime to OffsetDateTime using the system's default time zone
        return localDateTime.atOffset(ZoneId.systemDefault().getRules().getOffset(localDateTime));
    }

    public CurrentUserRate myRating() {
        UserDto currentUser = userService.getCurrentUser();
        List<UserRate> all = getTopRated(Long.MAX_VALUE, TimeSpan.ALL, null);
        List<UserRate> today = getTopRated(1L, TimeSpan.TODAY, currentUser.getId());

        UserRate userRate = all.parallelStream().filter(e -> e.getId().equals(currentUser.getId())).findFirst().orElse(new UserRate());

        CurrentUserRate currentUserRate = new CurrentUserRate();
        currentUserRate.setTotalRating(userRate.getTotalRating());
        currentUserRate.setReward(userRate.getReward());
        currentUserRate.setRank(userRate.getRank());
        currentUserRate.setDailyPont(today.getFirst().getTotalRating());
        currentUserRate.setProfileImage(userRate.getProfileImage());
        return currentUserRate;
    }
}
