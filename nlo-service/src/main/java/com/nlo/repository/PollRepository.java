package com.nlo.repository;

import com.nlo.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PollRepository extends BaseRepository<Poll> {
    Page<Poll> findByDeletedFalseOrDeletedIsNull(Pageable pageable);
    Optional<Poll> findTopByOrderByCreatedOnDesc();
}
