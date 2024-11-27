package com.nlo.mapper;

import com.nlo.entity.ApplicationBadge;
import com.nlo.entity.ApplicationRating;
import com.nlo.model.ApplicationBadgeDTO;
import com.nlo.model.ApplicationRatingDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Transactional
public class ApplicationRatingMapper implements BaseMapper<ApplicationRatingDTO, ApplicationRating> {

    @Override
    public ApplicationRatingDTO toDto(ApplicationRating applicationRating) {
        if (applicationRating == null) {
            return null;
        }

        ApplicationRatingDTO applicationRatingDTO = new ApplicationRatingDTO();
        applicationRatingDTO.setId(applicationRating.getId());
        applicationRatingDTO.setLikePoint(applicationRating.getLikePoint());
        applicationRatingDTO.setDislikePoint(applicationRating.getDislikePoint());
        applicationRatingDTO.setSharePoint(applicationRating.getSharePoint());

        List<ApplicationBadge> applicationBadges = applicationRating.getApplicationBadges();
        if (Objects.nonNull(applicationBadges)) {
            List<ApplicationBadgeDTO> badgeDTOs = applicationBadges.stream()
                    .map(this::mapToBadgeDTO)
                    .sorted(Comparator.comparing(ApplicationBadgeDTO::getMinPoint))
                    .collect(Collectors.toList());
            applicationRatingDTO.setApplicationBadges(badgeDTOs);
        }

        return applicationRatingDTO;
    }

    @Override
    public ApplicationRating toEntity(ApplicationRatingDTO applicationRatingDTO) {
        if (applicationRatingDTO == null) {
            return null;
        }

        ApplicationRating applicationRating = new ApplicationRating();
        applicationRating.setId(applicationRatingDTO.getId());
        applicationRating.setLikePoint(applicationRatingDTO.getLikePoint());
        applicationRating.setDislikePoint(applicationRatingDTO.getDislikePoint());
        applicationRating.setSharePoint(applicationRatingDTO.getSharePoint());

        List<ApplicationBadgeDTO> badgeDTOs = applicationRatingDTO.getApplicationBadges();
        if (Objects.nonNull(badgeDTOs)) {
            List<ApplicationBadge> badges = badgeDTOs.stream()
                    .map(this::mapToBadgeEntity)
                    .collect(Collectors.toList());
            applicationRating.setApplicationBadges(badges);
        }

        return applicationRating;
    }

    private ApplicationBadgeDTO mapToBadgeDTO(ApplicationBadge applicationBadge) {
        if (applicationBadge == null) {
            return null;
        }

        ApplicationBadgeDTO applicationBadgeDTO = new ApplicationBadgeDTO();
        applicationBadgeDTO.setId(applicationBadge.getId());
        applicationBadgeDTO.setMinPoint(applicationBadge.getMinPoint());
        applicationBadgeDTO.setMaxPoint(applicationBadge.getMaxPoint());
        applicationBadgeDTO.setTitle(applicationBadge.getTitle());
        return applicationBadgeDTO;
    }

    private ApplicationBadge mapToBadgeEntity(ApplicationBadgeDTO applicationBadgeDTO) {
        if (applicationBadgeDTO == null) {
            return null;
        }

        ApplicationBadge applicationBadge = new ApplicationBadge();
        applicationBadge.setId(applicationBadgeDTO.getId());
        applicationBadge.setMinPoint(applicationBadgeDTO.getMinPoint());
        applicationBadge.setMaxPoint(applicationBadgeDTO.getMaxPoint());
        applicationBadge.setTitle(applicationBadgeDTO.getTitle());
        return applicationBadge;
    }
}
