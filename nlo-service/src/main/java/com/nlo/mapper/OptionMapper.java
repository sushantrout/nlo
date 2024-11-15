package com.nlo.mapper;

import com.nlo.entity.Option;
import com.nlo.model.OptionDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OptionMapper implements BaseMapper<OptionDTO, Option> {
    @Override
    public OptionDTO toDto(Option option) {
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setId(option.getId());
        optionDTO.setSortOrder(option.getSortOrder());
        optionDTO.setTitle(option.getTitle());
        optionDTO.setRate(option.getRate());
        return optionDTO;
    }

    @Override
    public Option toEntity(OptionDTO optionDTO) {
        Option option = new Option();
        option.setId(optionDTO.getId());
        option.setSortOrder(optionDTO.getSortOrder());
        option.setTitle(optionDTO.getTitle());
        option.setRate(optionDTO.getRate());
        return option;
    }
}
