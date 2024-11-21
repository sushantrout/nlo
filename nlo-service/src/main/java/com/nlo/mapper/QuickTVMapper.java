package com.nlo.mapper;

import com.nlo.entity.QuickTV;
import com.nlo.model.QuickTVDTO;
import org.springframework.stereotype.Component;

@Component
public class QuickTVMapper implements BaseMapper<QuickTVDTO, QuickTV> {

    @Override
    public QuickTVDTO toDto(QuickTV quickTV) {
        QuickTVDTO dto = new QuickTVDTO();
        dto.setId(quickTV.getId());
        dto.setUrl(quickTV.getUrl());
        dto.setTitle(quickTV.getTitle());
        return dto;
    }

    @Override
    public QuickTV toEntity(QuickTVDTO quickTVDTO) {
        QuickTV tv = new QuickTV();
        tv.setId(quickTVDTO.getId());
        tv.setTitle(quickTVDTO.getTitle());
        tv.setUrl(quickTVDTO.getUrl());
        return tv;
    }
}
