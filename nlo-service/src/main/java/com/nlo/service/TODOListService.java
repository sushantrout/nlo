package com.nlo.service;

import com.nlo.entity.TODOList;
import com.nlo.mapper.ReactionMapper;
import com.nlo.mapper.TODOListMapper;
import com.nlo.model.ReactionDTO;
import com.nlo.model.TODOListDTO;
import com.nlo.repository.TODOListRepository;
import com.nlo.validation.TODOListValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TODOListService extends BaseServiceImpl<TODOList, TODOListDTO, TODOListMapper, TODOListValidation, TODOListRepository> {
    @Lazy
    @Autowired
    private ReactionMapper reactionMapper;

    protected TODOListService(TODOListRepository repository, TODOListMapper mapper, TODOListValidation validation) {
        super(repository, mapper, validation);
    }

    public TODOListDTO reaction(String todoId, ReactionDTO reactionDTO) {
        TODOList todoList = repository.findById(todoId).orElseThrow(() -> new RuntimeException("TOTOLIst not found"));
        todoList.getReactions().add(reactionMapper.toEntity(reactionDTO));
        return mapper.toDto(repository.save(todoList));
    }
}
