package com.nlo.repository;

import com.nlo.entity.ViewDetail;
import com.nlo.model.UserViewSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ViewDetailRepository extends BaseRepository<ViewDetail> {
    List<ViewDetail> findByNewsIdAndUserId(String newsId, String userId);

    List<ViewDetail> findByNewsIdInAndUserId(List<String> newsIds, String userId);

    @Query("SELECT v.user.id AS userId, COUNT(v) AS viewCount FROM ViewDetail v " +
            "WHERE (v.createdOn >= :startTime) " +
            "GROUP BY v.user.id")
    List<UserViewSummary> countGroupByUserIdAfter(OffsetDateTime startTime);
}
