package com.nlo.service;

import com.nlo.entity.Poll;
import com.nlo.entity.PollResponse;
import com.nlo.entity.User;
import com.nlo.mapper.PollResponseMapper;
import com.nlo.model.PollDTO;
import com.nlo.model.PollResponseDTO;
import com.nlo.model.UserDto;
import com.nlo.repository.PollRepository;
import com.nlo.repository.PollResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PollResponseService {
    private final PollResponseRepository pollResponseRepository;
    private final PollResponseMapper pollResponseMapper;
    private final UserService userService;
    private final PollRepository pollRepository;

    public PollResponseDTO answer(PollResponseDTO pollResponseDTO) {
        PollResponse entity = pollResponseMapper.toEntity(pollResponseDTO);
        //Set userid
        UserDto currentUser = userService.getCurrentUser();
        if(Objects.nonNull(currentUser)) {
            User user = new User();
            user.setId(currentUser.getId());
            entity.setUser(user);
        }
        entity = pollResponseRepository.save(entity);
        return pollResponseMapper.toDto(entity);
    }

    public List<PollResponseDTO> getLatestPollResult() {
        Poll poll = pollRepository.findTopByOrderByCreatedOnDesc().orElseThrow();
        List<PollResponse> byPollId = pollResponseRepository.findByPoll(poll);
        return pollResponseMapper.toDtoList(byPollId);
    }
}
