package com.nlo.service;

import com.nlo.entity.Poll;
import com.nlo.entity.PollResponse;
import com.nlo.entity.User;
import com.nlo.mapper.PollResponseMapper;
import com.nlo.model.*;
import com.nlo.repository.PollRepository;
import com.nlo.repository.PollResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public PollResult getLatestPollResult(String pollId) {
        List<PollResponse> byPollId = pollResponseRepository.findByPollId(pollId);
        List<PollResponseDTO> dtoList = pollResponseMapper.toDtoList(byPollId);

        Map<String, List<PollResponseDTO>> answerRespondent = dtoList.stream()
                .filter(pr -> pr.getUserId() != null && pr.getAnswerId() != null)
                .collect(Collectors.groupingBy(PollResponseDTO::getAnswerId));

        Map<String, AnswerResult> answers = new LinkedHashMap<>();

        for (String answerId : answerRespondent.keySet()) {
            List<PollResponseDTO> pollResponseDTOS = answerRespondent.get(answerId);

            Map<String, Long> constituencyCounts = pollResponseDTOS.stream()
                    .filter(dto -> dto.getConstituencyName() != null)
                    .collect(Collectors.groupingBy(
                            PollResponseDTO::getConstituencyName,
                            Collectors.counting()
                    ));

            AnswerResult answerResult = new AnswerResult();
            answerResult.setConstituencyCounts(constituencyCounts);
            answerResult.setTotalResponses((long) pollResponseDTOS.size());

            answers.put(answerId, answerResult);
        }

        PollResult pollResult = new PollResult();
        pollResult.setPollId(pollId);
        pollResult.setAnswers(answers);

        return pollResult;
    }


    public Page<RatingDTO> getTopRatedList(Pageable pageable) {
        return pollResponseRepository.findTopUsersByTotalRate(pageable);
    }
}
