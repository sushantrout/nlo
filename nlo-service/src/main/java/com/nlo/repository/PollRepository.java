package com.nlo.repository;

import com.nlo.entity.Poll;

import java.util.Optional;

public interface PollRepository extends BaseRepository<Poll> {
    Optional<Poll> findTopByOrderByCreatedOnDesc();
}
