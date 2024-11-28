package com.nlo.service;

import com.nlo.entity.Attachment;
import com.nlo.entity.EBook;
import com.nlo.mapper.EBookMapper;
import com.nlo.model.EBookDTO;
import com.nlo.repository.EBookRepository;
import com.nlo.validation.EBookValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class EBookService extends BaseServiceImpl<EBook, EBookDTO, EBookMapper, EBookValidation, EBookRepository> {
    protected EBookService(EBookRepository repository, EBookMapper mapper, EBookValidation validation) {
        super(repository, mapper, validation);
    }

    @Autowired
    private AttachmentService attachmentService;

    public EBookDTO saveWithAttachment(EBookDTO eBookDTO, MultipartFile file) {
        EBook entity = mapper.toEntity(eBookDTO);
        entity = repository.save(entity);
        ArrayList<MultipartFile> attachments = new ArrayList<>();
        attachments.add(file);
        List<Attachment> savedAttachments = attachmentService.saveAll(attachments, entity.getId());
        entity.setAttachment(savedAttachments.getFirst());
        EBook save = repository.save(entity);
        return mapper.toDto(save);
    }
}
