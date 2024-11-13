package com.nlo.repository;

import com.nlo.entity.News;
import com.nlo.entity.NewsShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NewsShareRepository extends JpaRepository<NewsShare, String> {
    @Query("SELECT ns FROM NewsShare ns WHERE ns.shareCode = :shareCode AND (ns.used = false OR ns.used IS NULL)")
    Optional<NewsShare> findByShareCodeAndUsedFalseOrNull(@Param("shareCode") String shareCode);

    @Query("SELECT COUNT(ns) FROM NewsShare ns WHERE ns.news = :news AND ns.used = true")
    long countUsedNewsShares(@Param("news") News news);
}
