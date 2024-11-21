package com.nlo.mapper;

import com.nlo.entity.Infographics;
import com.nlo.model.InfographicsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InfographicsMapper implements BaseMapper<InfographicsDTO, Infographics> {
    private final ReactionMapper reactionMapper;

    @Override
    public InfographicsDTO toDto(Infographics infographics) {
        InfographicsDTO dto = new InfographicsDTO();
        dto.setId(infographics.getId());
        dto.setTitle(infographics.getTitle());
        dto.setUrl(infographics.getUrl());
        dto.setReactions(reactionMapper.toDtoList(infographics.getReactions()));
        return dto;
    }

    @Override
    public Infographics toEntity(InfographicsDTO infographicsDTO) {
        Infographics infographics = new Infographics();
        infographics.setId(infographicsDTO.getId());
        infographics.setTitle(infographicsDTO.getTitle());
        infographics.setUrl(infographicsDTO.getUrl());
        infographics.setReactions(reactionMapper.toEntityList(infographicsDTO.getReactions()));
        return infographics;
    }
}
