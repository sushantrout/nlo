package com.nlo.service;

import com.nlo.constant.ReactionType;
import com.nlo.entity.Infographics;
import com.nlo.entity.Reaction;
import com.nlo.mapper.InfographicsMapper;
import com.nlo.mapper.ReactionMapper;
import com.nlo.model.InfographicsDTO;
import com.nlo.model.ReactionDTO;
import com.nlo.model.UserDto;
import com.nlo.repository.Infographicsrepository;
import com.nlo.repository.ReactionRepository;
import com.nlo.repository.dbdto.ReactionDBDTO;
import com.nlo.validation.InfographicsValidation;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InfographicsService extends BaseServiceImpl<Infographics, InfographicsDTO, InfographicsMapper, InfographicsValidation, Infographicsrepository> {
    @Autowired
    private UserService userService;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private ReactionMapper reactionMapper;

    @Lazy
    @Autowired
    private InfographicsShareService infographicsShareService;

    protected InfographicsService(Infographicsrepository repository, InfographicsMapper mapper, InfographicsValidation validation) {
        super(repository, mapper, validation);
    }

    public InfographicsDTO reaction(String infographicsId, ReactionDTO reactionDTO) {
        Infographics infographics = repository.findById(infographicsId).orElseThrow(() -> new ServiceException("Infographics not found."));

        UserDto currentUser = userService.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            String currentUserId = currentUser.getId();
            reactionDTO.setUserId(currentUserId);
            Optional<ReactionDBDTO> first = reactionRepository.findByUserIdAndInfographicsIds(currentUserId, List.of(infographicsId)).stream().findFirst();
            if(reactionDTO.getReactionType().equals(ReactionType.NONE) && first.isPresent()) {
                infographics.setReactions(infographics.getReactions().stream().filter(e -> e.getUserId() != null && !e.getUserId().equals(currentUserId)).collect(Collectors.toList()));
                repository.save(infographics);
                reactionRepository.deleteById(first.get().getReactionId());
            } else {
                if(first.isPresent()) {
                    String reactionId = first.get().getReactionId();
                    reactionRepository.findById(reactionId).ifPresent(re -> {
                        re.setReactionType(reactionDTO.getReactionType());
                        reactionRepository.save(re);
                    });
                } else {
                    Reaction entity = reactionMapper.toEntity(reactionDTO);
                    entity = reactionRepository.save(entity);
                    infographics.getReactions().add(entity);
                    repository.save(infographics);
                }
            }

        }
        return mapper.toDto(repository.findById(infographicsId).get());
    }

    @Override
    public Page<InfographicsDTO> getAll(Pageable pageable) {
        Page<Infographics> dataPage = repository.findByDeletedFalseOrDeletedIsNull(pageable);
        Page<InfographicsDTO> infographicsDTOs = dataPage.map(mapper::toDto);
        getAllWithReaction(infographicsDTOs.getContent());
        return infographicsDTOs;
    }

    @Override
    public Optional<InfographicsDTO> getById(String id, String shareId) {
        Optional<Infographics> dataOpt = repository.findById(id);
        Optional<InfographicsDTO> infographicsDTO = dataOpt.map(mapper::toDto);
        if(infographicsDTO.isPresent()) {
            ArrayList<InfographicsDTO> dtoList = new ArrayList<>();
            dtoList.add(infographicsDTO.get());
            getAllWithReaction(dtoList);
        }

        activeShareCount(shareId);
        return infographicsDTO;
    }

    private void activeShareCount(String shareId) {
        infographicsShareService.updateTheStatus(shareId);
    }


    public void getAllWithReaction(List<InfographicsDTO> dtoList) {
        List<String> infographicsIds = dtoList.stream().map(InfographicsDTO::getId).toList();

        UserDto currentUser = userService.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            String currentUserId = currentUser.getId();
            List<ReactionDBDTO> currentUserReactions = reactionRepository.findByUserIdAndInfographicsIds(currentUserId, infographicsIds);

            Map<String, ReactionDBDTO> reactionMap = currentUserReactions.stream()
                    .collect(Collectors.toMap(ReactionDBDTO::getDataId, reaction -> reaction, (a, b) -> a));

            dtoList.forEach(infographicsDTO -> {
                ReactionDBDTO reaction = reactionMap.get(infographicsDTO.getId());
                if (reaction != null && reaction.getCurrentUser().equals(currentUserId)) {
                    infographicsDTO.setCurrentUserReaction(reaction.getReactionType());
                }
            });
        }
    }
}
