package com.nlo.service;

import com.nlo.entity.Attachment;
import com.nlo.entity.Stories;
import com.nlo.mapper.AttachmentMapper;
import com.nlo.mapper.StoriesMapper;
import com.nlo.model.StoriesDTO;
import com.nlo.repository.BaseRepository;
import com.nlo.validation.StoriesValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class StoriesService extends BaseServiceImpl<Stories, StoriesDTO, StoriesMapper, StoriesValidation, BaseRepository<Stories>> {
    @Autowired
    public AttachmentService attachmentService;
    @Lazy
    @Autowired
    private AttachmentMapper attachmentMapper;


    protected StoriesService(BaseRepository<Stories> repository, StoriesMapper mapper, StoriesValidation validation) {
        super(repository, mapper, validation);
    }

    public StoriesDTO saveStoriesWithAttachment(StoriesDTO storiesDTO, List<MultipartFile> files) {
        List<Attachment> staticAttachment = attachmentMapper.toEntityList(storiesDTO.getAttachments());
        Stories entity = mapper.toEntity(storiesDTO);
        entity.setAttachments(staticAttachment);
        Stories save = repository.save(entity);

        List<Attachment> attachments = attachmentService.saveAll(files, save.getId());
        save.getAttachments().addAll(attachments);
        save = repository.save(save);
        return mapper.toDto(save);
    }
}
