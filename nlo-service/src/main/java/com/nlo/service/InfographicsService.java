package com.nlo.service;

import com.nlo.constant.ReactionType;
import com.nlo.entity.Infographics;
import com.nlo.entity.News;
import com.nlo.entity.Reaction;
import com.nlo.mapper.InfographicsMapper;
import com.nlo.mapper.ReactionMapper;
import com.nlo.model.InfographicsDTO;
import com.nlo.model.NewsDTO;
import com.nlo.model.ReactionDTO;
import com.nlo.model.UserDto;
import com.nlo.repository.Infographicsrepository;
import com.nlo.repository.ReactionRepository;
import com.nlo.repository.dbdto.ReactionDBDTO;
import com.nlo.validation.InfographicsValidation;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<InfographicsDTO> getById(String id, String shareId) {
        Optional<Infographics> dataOpt = repository.findById(id);
        Optional<InfographicsDTO> newsDTO = dataOpt.map(mapper::toDto);
        if(newsDTO.isPresent()) {
            ArrayList<InfographicsDTO> dtoList = new ArrayList<>();
            dtoList.add(newsDTO.get());
            getAllWithReaction(dtoList);
        }
        return newsDTO;
    }


    public List<InfographicsDTO> getAllWithReaction(List<InfographicsDTO> dtoList) {
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
        return dtoList;
    }
}
