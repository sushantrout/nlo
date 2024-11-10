package com.nlo.mapper;

import com.nlo.entity.BaseEntity;
import com.nlo.entity.Constituency;
import com.nlo.model.ConstituencyDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ConstituencyMapper implements BaseMapper<ConstituencyDTO, Constituency>  {

    @Override
    public ConstituencyDTO toDto(Constituency constituency) {
        ConstituencyDTO constituencyDTO = new ConstituencyDTO();
        constituencyDTO.setId(constituency.getId());
        constituencyDTO.setTitle(constituency.getTitle());
        return constituencyDTO;
    }

    @Override
    public Constituency toEntity(ConstituencyDTO constituencyDTO) {
        Constituency constituency = new Constituency();
        constituency.setId(constituencyDTO.getId());
        constituency.setTitle(constituencyDTO.getTitle());
        return constituency;
    }
}
