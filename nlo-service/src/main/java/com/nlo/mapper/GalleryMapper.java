package com.nlo.mapper;

import com.nlo.entity.Gallery;
import com.nlo.model.GalleryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GalleryMapper implements BaseMapper<GalleryDTO, Gallery> {
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private ReactionMapper reactionMapper;

    @Override
    public GalleryDTO toDto(Gallery gallery) {
        GalleryDTO galleryDTO = new GalleryDTO();
        galleryDTO.setId(gallery.getId());
        galleryDTO.setTitle(gallery.getTitle());
        galleryDTO.setDescription(gallery.getDescription());
        galleryDTO.setAttachments(attachmentMapper.toDtoList(gallery.getAttachments()));
        galleryDTO.setReactions(reactionMapper.toDtoList(gallery.getReactions()));

        return galleryDTO;
    }

    @Override
    public Gallery toEntity(GalleryDTO galleryDTO) {
        Gallery gallery = new Gallery();
        gallery.setId(galleryDTO.getId());
        gallery.setTitle(galleryDTO.getTitle());
        gallery.setDescription(galleryDTO.getDescription());
        gallery.setAttachments(attachmentMapper.toEntityList(galleryDTO.getAttachments()));
        gallery.setReactions(reactionMapper.toEntityList(galleryDTO.getReactions()));
        return gallery;
    }
}