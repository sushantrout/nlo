package com.nlo.repository;

import com.nlo.entity.PollResponse;
import com.nlo.model.RatingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PollResponseRepository extends BaseRepository<PollResponse> {
    List<PollResponse> findByPollId(String pollId);

    @Query("SELECT new com.nlo.model.RatingDTO(pr.user.id, pr.user.username, SUM(pr.option.rate), pr.user.name, pr.user.mobile) " +
            "FROM PollResponse pr " +
            "WHERE (pr.createdOn >= :startTime) " +
            "GROUP BY pr.user.id, pr.user.username, pr.user.name, pr.user.mobile " +
            "ORDER BY SUM(pr.option.rate) DESC")
    Page<RatingDTO> findTopUsersByTotalRate(Pageable pageable, OffsetDateTime startTime);




}
