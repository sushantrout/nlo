package com.nlo.mapper;

import com.nlo.entity.Reaction;
import com.nlo.mapper.BaseMapper;
import com.nlo.model.ReactionDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ReactionMapper implements BaseMapper<ReactionDTO, Reaction> {
    @Override
    public ReactionDTO toDto(Reaction reaction) {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setId(reaction.getId());
        reactionDTO.setReactionType(reaction.getReactionType());
        reactionDTO.setUserId(reaction.getUserId());
        return reactionDTO;
    }

    @Override
    public Reaction toEntity(ReactionDTO reactionDTO) {
        Reaction reaction = new Reaction();
        reaction.setId(reactionDTO.getId());
        reaction.setUserId(reactionDTO.getUserId());
        reaction.setReactionType(reactionDTO.getReactionType());
        return reaction;
    }
}