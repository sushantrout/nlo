package com.nlo.mapper;

import com.nlo.entity.TODOList;
import com.nlo.model.TODOListDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TODOListMapper implements BaseMapper<TODOListDTO, TODOList> {
    @Override
    public TODOListDTO toDto(TODOList todoList) {
        TODOListDTO dto = new TODOListDTO();
        dto.setId(todoList.getId());
        dto.setStaticURL(todoList.getStaticURL());
        dto.setTitle(todoList.getName());
        return dto;
    }

    @Override
    public TODOList toEntity(TODOListDTO todoListDTO) {
        TODOList e = new TODOList();
        e.setName(todoListDTO.getTitle());
        e.setId(todoListDTO.getId());
        e.setStaticURL(todoListDTO.getStaticURL());
        return e;
    }
}
