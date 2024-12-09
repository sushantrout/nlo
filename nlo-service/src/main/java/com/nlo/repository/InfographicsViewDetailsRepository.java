package com.nlo.repository;

import com.nlo.entity.InfographicsViewDetails;
import com.nlo.model.UserViewSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface InfographicsViewDetailsRepository extends JpaRepository<InfographicsViewDetails, String> {
    List<InfographicsViewDetails> findByInfographicsIdAndUserId(String infoId, String userId);
    List<InfographicsViewDetails> findByInfographicsIdInAndUserId(List<String> infoIds, String userId);

    @Query("SELECT v.user.id AS userId, COUNT(v) AS viewCount FROM InfographicsViewDetails v " +
            "WHERE (v.createdOn >= :startTime) AND (:userId IS NULL OR v.user.id = :userId) " +
            "GROUP BY v.user.id")
    List<UserViewSummary> countGroupByUserIdAfter(OffsetDateTime startTime, String userId);
}
