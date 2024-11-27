package com.nlo.repository;

import com.nlo.entity.InfographicsShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfographicsShareRepository extends JpaRepository<InfographicsShare, String> {
    @Query("SELECT ns FROM InfographicsShare ns WHERE ns.shareCode = :shareCode AND (ns.used = false OR ns.used IS NULL)")
    Optional<InfographicsShare> findByShareCodeAndUsedFalseOrNull(String shareCode);

    @Query("SELECT COUNT(id) FROM InfographicsShare WHERE infographics.id = :infographicsId")
    Number getShareCountByInfographics(String infographicsId);
}
