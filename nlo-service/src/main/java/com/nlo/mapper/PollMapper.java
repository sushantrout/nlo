package com.nlo.mapper;

import com.nlo.entity.Option;
import com.nlo.entity.Poll;
import com.nlo.model.OptionDTO;
import com.nlo.model.PollDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
@Transactional
public class PollMapper implements BaseMapper<PollDTO, Poll> {
    @Lazy
    @Autowired
    private OptionMapper optionMapper;

    @Override
    public PollDTO toDto(Poll poll) {
        PollDTO dto = new PollDTO();
        dto.setId(poll.getId());
        dto.setTitle(poll.getTitle());
        List<Option> options = poll.getOptions();
        if(Objects.nonNull(options)) {
            dto.setOptions(optionMapper.toDtoList(options));
        }
        setAuditColumn(poll, dto);
        return dto;
    }

    @Override
    public Poll toEntity(PollDTO pollDTO) {
        Poll poll = new Poll();
        poll.setId(pollDTO.getId());
        poll.setTitle(pollDTO.getTitle());
        List<OptionDTO> options = pollDTO.getOptions();
        if(Objects.nonNull(options)) {
            List<Option> entityList = optionMapper.toEntityList(options);
            poll.setOptions(entityList);
        }
        return poll;
    }
}
