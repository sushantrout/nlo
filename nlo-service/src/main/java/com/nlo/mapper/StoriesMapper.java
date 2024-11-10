package com.nlo.mapper;

import com.nlo.entity.Stories;
import com.nlo.mapper.AttachmentMapper;
import com.nlo.mapper.BaseMapper;
import com.nlo.model.StoriesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Transactional
public class StoriesMapper implements BaseMapper<StoriesDTO, Stories> {
    private final AttachmentMapper attachmentMapper;
    @Override
    public StoriesDTO toDto(Stories stories) {
        StoriesDTO storiesDTO = new StoriesDTO();
        storiesDTO.setId(stories.getId());
        storiesDTO.setTitle(stories.getTitle());
        if(Objects.nonNull(stories.getHeaderImage())) {
            storiesDTO.setHeaderImage(attachmentMapper.toDto(stories.getHeaderImage()));
        }
        if (Objects.nonNull(stories.getAttachments())) {
            storiesDTO.setAttachments(attachmentMapper.toDtoList(stories.getAttachments()));
        }
        storiesDTO.setIsPublished(stories.getIsPublished());
        return storiesDTO;
    }

    @Override
    public Stories toEntity(StoriesDTO storiesDTO) {
        Stories stories = new Stories();
        stories.setId(storiesDTO.getId());
        stories.setTitle(storiesDTO.getTitle());
        if(Objects.nonNull(storiesDTO.getHeaderImage())) {
            stories.setHeaderImage(attachmentMapper.toEntity(storiesDTO.getHeaderImage()));
        }
        if(Objects.nonNull(storiesDTO.getAttachments())) {
            stories.setAttachments(attachmentMapper.toEntityList(storiesDTO.getAttachments()));
        }
        stories.setIsPublished(storiesDTO.getIsPublished());
        return stories;
    }
}
