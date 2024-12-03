package com.nlo.repository;

import com.nlo.entity.News;
import com.nlo.entity.NewsShare;
import com.nlo.model.UserShareSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsShareRepository extends JpaRepository<NewsShare, String> {
    @Query("SELECT ns FROM NewsShare ns WHERE ns.shareCode = :shareCode AND (ns.used = false OR ns.used IS NULL)")
    Optional<NewsShare> findByShareCodeAndUsedFalseOrNull(@Param("shareCode") String shareCode);

    @Query("SELECT COUNT(ns) FROM NewsShare ns WHERE ns.news = :news AND ns.used = true")
    long countUsedNewsShares(@Param("news") News news);

    @Query("SELECT ns.user.id AS userId, " +
            "COUNT(ns) AS totalShares " +
            "FROM NewsShare ns " +
            "GROUP BY ns.user.id")
    List<UserShareSummary> calculateShareSummaryForAllUsers();
}
