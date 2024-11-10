package com.nlo.mapper;

import com.nlo.entity.Attachment;
import com.nlo.mapper.BaseMapper;
import com.nlo.model.AttachmentDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AttachmentMapper implements BaseMapper<AttachmentDTO, Attachment> {
    @Override
    public AttachmentDTO toDto(Attachment attachment) {
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setUrl(attachment.getUrl());
        attachmentDTO.setContentType(attachment.getContentType());
        attachmentDTO.setExtension(attachment.getExtension());
        attachmentDTO.setThumbnailUrl(attachment.getThumbnailUrl());
        attachmentDTO.setThumbnailMediaType(attachment.getThumbnaileMediaType());
        attachmentDTO.setStaticUrl(attachment.getStaticURL());
        attachmentDTO.setDescription(attachment.getDescription());
        return attachmentDTO;
    }

    @Override
    public Attachment toEntity(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();
        attachment.setId(attachmentDTO.getId());
        attachment.setUrl(attachmentDTO.getUrl());
        attachment.setContentType(attachmentDTO.getContentType());
        attachment.setExtension(attachmentDTO.getExtension());
        attachment.setThumbnailUrl(attachmentDTO.getThumbnailUrl());
        attachment.setThumbnaileMediaType(attachmentDTO.getThumbnailMediaType());
        attachment.setStaticURL(attachmentDTO.getStaticUrl());
        attachment.setDescription(attachment.getDescription());
        return attachment;
    }
}
