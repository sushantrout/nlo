package com.nlo.mapper;

import com.nlo.entity.TDPLive;
import com.nlo.model.TDPLiveDTO;
import org.springframework.stereotype.Component;

@Component
public class TDPLiveMapper implements BaseMapper<TDPLiveDTO, TDPLive> {
    @Override
    public TDPLiveDTO toDto(TDPLive tdpLive) {
        TDPLiveDTO dto = new TDPLiveDTO();
        dto.setId(tdpLive.getId());
        dto.setTitle(tdpLive.getTitle());
        dto.setUrl(tdpLive.getUrl());
        setAuditColumn(tdpLive, dto);
        return dto;
    }

    @Override
    public TDPLive toEntity(TDPLiveDTO tdpLiveDTO) {
        TDPLive live = new TDPLive();
        live.setId(tdpLiveDTO.getId());
        live.setTitle(tdpLiveDTO.getTitle());
        live.setUrl(tdpLiveDTO.getUrl());
        return live;
    }
}
