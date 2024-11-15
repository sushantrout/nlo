package com.nlo.repository;

import com.nlo.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PollRepository extends BaseRepository<Poll> {
    Page<Poll> findByDeletedFalseOrDeletedIsNull(Pageable pageable);
    Optional<Poll> findTopByOrderByCreatedOnDesc();

    @Query("SELECT p FROM Poll p WHERE p NOT IN " +
            "(SELECT pr.poll FROM PollResponse pr WHERE pr.user.id = :userId) AND( p.deleted is null OR p.deleted = FALSE)")
    Page<Poll> findPollsNotAnsweredByUser(@Param("userId") String userId, Pageable pageable);
}
