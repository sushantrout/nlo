package com.nlo.repository;

import com.nlo.entity.Constituency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConstituencyRepository extends BaseRepository<Constituency> {
    List<Constituency> findByDeletedFalseOrDeletedIsNull();

    @Query("SELECT c FROM Constituency c WHERE LOWER(c.title) LIKE LOWER(CONCAT(:title, '%'))")
    List<Constituency> findConstituencyByName(@Param("title") String title);

}
