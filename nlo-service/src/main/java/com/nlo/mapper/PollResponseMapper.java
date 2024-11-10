package com.nlo.mapper;

import com.nlo.entity.Option;
import com.nlo.entity.Poll;
import com.nlo.entity.PollResponse;
import com.nlo.model.PollResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
public class PollResponseMapper implements BaseMapper<PollResponseDTO, PollResponse> {
    @Override
    public PollResponseDTO toDto(PollResponse pollResponse) {
        PollResponseDTO dto = new PollResponseDTO();
        dto.setId(pollResponse.getId());
        if(Objects.nonNull(pollResponse.getPoll())) {
            dto.setPollId(pollResponse.getPoll().getId());
        }

        if(Objects.nonNull(pollResponse.getOption())) {
            dto.setAnswerId(pollResponse.getOption().getId());
        }
        return dto;
    }

    @Override
    public PollResponse toEntity(PollResponseDTO pollResponseDTO) {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setId(pollResponse.getId());
        if(Objects.nonNull(pollResponseDTO.getPollId())) {
            Poll poll = new Poll();
            poll.setId(pollResponseDTO.getPollId());
            pollResponse.setPoll(poll);
        }

        if(Objects.nonNull(pollResponseDTO.getAnswerId())) {
            Option option = new Option();
            option.setId(pollResponseDTO.getAnswerId());
            pollResponse.setOption(option);

        }
        return pollResponse;
    }
}
