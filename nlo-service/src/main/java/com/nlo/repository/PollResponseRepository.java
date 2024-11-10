package com.nlo.repository;

import com.nlo.entity.Poll;
import com.nlo.entity.PollResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollResponseRepository extends BaseRepository<PollResponse> {
    List<PollResponse> findByPoll(Poll pollId);
}
