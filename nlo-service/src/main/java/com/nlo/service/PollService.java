package com.nlo.service;

import com.nlo.entity.Poll;
import com.nlo.mapper.PollMapper;
import com.nlo.model.PollDTO;
import com.nlo.repository.PollRepository;
import com.nlo.repository.PollResponseRepository;
import com.nlo.validation.PollValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollService extends BaseServiceImpl<Poll, PollDTO, PollMapper, PollValidation, PollRepository> {
    @Autowired
    private PollResponseRepository pollResponseRepository;

    protected PollService(PollRepository repository, PollMapper mapper, PollValidation validation) {
        super(repository, mapper, validation);
    }

    public PollDTO getLatest() {
        Poll poll = repository.findTopByOrderByCreatedOnDesc().orElseThrow();
        return mapper.toDto(poll);
    }
}
