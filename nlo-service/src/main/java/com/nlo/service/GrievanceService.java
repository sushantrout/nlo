package com.nlo.service;

import com.nlo.constant.GrievanceStatus;
import com.nlo.entity.Attachment;
import com.nlo.entity.Grievance;
import com.nlo.mapper.GrievanceMapper;
import com.nlo.model.GrievanceDTO;
import com.nlo.repository.GrievanceRepository;
import com.nlo.validation.GriveanceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class GrievanceService extends BaseServiceImpl<Grievance, GrievanceDTO, GrievanceMapper, GriveanceValidation, GrievanceRepository> {
    @Autowired
    public AttachmentService attachmentService;

    protected GrievanceService(GrievanceRepository repository, GrievanceMapper mapper, GriveanceValidation validation) {
        super(repository, mapper, validation);
    }

    public GrievanceDTO saveWithAttachment(GrievanceDTO grievanceDTO, List<MultipartFile> files) {
        Grievance entity = mapper.toEntity(grievanceDTO);
        entity = repository.save(entity);
        List<Attachment> attachments = attachmentService.saveAll(files, entity.getId());
        entity = repository.findById(entity.getId()).orElseThrow();
        entity.getAttachments().addAll(attachments);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public void updateStatus(String id, GrievanceStatus status) {
        Grievance grievance = repository.findById(id).orElseThrow();
        grievance.setStatus(status);
        repository.save(grievance);
    }
}
