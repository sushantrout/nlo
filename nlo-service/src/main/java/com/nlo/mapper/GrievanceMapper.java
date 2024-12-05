package com.nlo.mapper;

import com.nlo.entity.Grievance;
import com.nlo.model.GrievanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GrievanceMapper implements BaseMapper<GrievanceDTO, Grievance> {
    @Lazy
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public GrievanceDTO toDto(Grievance grievance) {
        GrievanceDTO dto = new GrievanceDTO();
        dto.setId(grievance.getId());
        dto.setTitle(grievance.getTitle());
        dto.setSubject(grievance.getSubject());
        dto.setAttachments(attachmentMapper.toDtoList(grievance.getAttachments()));
        dto.setCreatedOn(grievance.getCreatedOn());
        dto.setStatus(grievance.getStatus());
        return dto;
    }

    @Override
    public Grievance toEntity(GrievanceDTO grievanceDTO) {
        Grievance grievance = new Grievance();
        grievance.setId(grievanceDTO.getId());
        grievance.setTitle(grievanceDTO.getTitle());
        grievance.setSubject(grievanceDTO.getSubject());
        grievance.setStatus(grievanceDTO.getStatus());
        return grievance;
    }
}
