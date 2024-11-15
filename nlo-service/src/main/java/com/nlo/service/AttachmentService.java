package com.nlo.service;

import com.nlo.entity.Attachment;
import com.nlo.mapper.AttachmentMapper;
import com.nlo.minio.MinioStorageService;
import com.nlo.model.AttachmentDTO;
import com.nlo.repository.AttachmentRepository;
import com.nlo.validation.AttachmentValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AttachmentService extends BaseServiceImpl<Attachment, AttachmentDTO, AttachmentMapper, AttachmentValidation, AttachmentRepository> {
    @Autowired
    private MinioStorageService minioStorageService;

    protected AttachmentService(AttachmentRepository repository, AttachmentMapper mapper, AttachmentValidation validation) {
        super(repository, mapper, validation);
    }

    public List<Attachment> saveAll(List<MultipartFile> files, String id) {
        if(Objects.isNull(files)) {return new ArrayList<>();}
        return files.stream().map(file -> {
            String attachments = minioStorageService.saveFile(file, id, "attachments");
            Attachment attachment = new Attachment();
            attachment.setUrl(attachments);
            attachment.setFileName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setExtension(Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            attachment.setActive(true);
            return repository.save(attachment);
        }).toList();
    }
}
