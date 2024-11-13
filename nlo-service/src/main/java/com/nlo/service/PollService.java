package com.nlo.service;

import com.nlo.entity.Poll;
import com.nlo.entity.PollResponse;
import com.nlo.mapper.PollMapper;
import com.nlo.model.PollDTO;
import com.nlo.model.UserDto;
import com.nlo.repository.PollRepository;
import com.nlo.repository.PollResponseRepository;
import com.nlo.validation.PollValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService extends BaseServiceImpl<Poll, PollDTO, PollMapper, PollValidation, PollRepository> {
    @Autowired
    private PollResponseRepository pollResponseRepository;
    @Autowired
    private UserService userService;

    protected PollService(PollRepository repository, PollMapper mapper, PollValidation validation) {
        super(repository, mapper, validation);
    }

    @Override
    public Page<PollDTO> getAll(Pageable pageable) {
        Page<Poll> dataPage = repository.findByDeletedFalseOrDeletedIsNull(pageable);
        try {
            UserDto currentUser = userService.getCurrentUser();
            String currentUserId = currentUser.getId();

            // Get IDs of polls the user has already answered
            List<PollResponse> responses = pollResponseRepository.findByPollIdInAndUserId(
                    dataPage.getContent().stream().map(Poll::getId).toList(), currentUserId);

            List<String> alreadyAnsweredIds = responses.stream()
                    .map(PollResponse::getPoll)
                    .map(Poll::getId)
                    .toList();

            // Filter out polls the user has already answered
            List<PollDTO> filteredPolls = dataPage.getContent().stream()
                    .filter(poll -> !alreadyAnsweredIds.contains(poll.getId()))
                    .map(mapper::toDto)
                    .toList();

            // Return filtered results as a Page
            return new PageImpl<>(filteredPolls, pageable, dataPage.getTotalElements());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Map all polls to DTOs if an exception occurs
        return dataPage.map(mapper::toDto);
    }


    public PollDTO getLatest() {
        Poll poll = repository.findTopByOrderByCreatedOnDesc().orElseThrow();
        return mapper.toDto(poll);
    }
}
