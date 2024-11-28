package com.nlo.mapper;

import com.nlo.entity.EBook;
import com.nlo.model.EBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class EBookMapper implements BaseMapper<EBookDTO, EBook> {

    @Lazy
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public EBookDTO toDto(EBook eBook) {
        EBookDTO eBookDTO = new EBookDTO();
        eBookDTO.setId(eBook.getId());
        eBookDTO.setTitle(eBook.getTitle()); // Set non-auditing fields
        eBookDTO.setDescription(eBook.getDescription());
        eBookDTO.setStaticURL(eBook.getStaticURL());

        // Map attachment if necessary
        if (eBook.getAttachment() != null) {
            eBookDTO.setAttachmentDTO(attachmentMapper.toDto(eBook.getAttachment()));
        }

        return eBookDTO;
    }

    @Override
    public EBook toEntity(EBookDTO eBookDTO) {
        EBook eBook = new EBook();
        eBook.setId(eBookDTO.getId());
        eBook.setTitle(eBookDTO.getTitle()); // Set non-auditing fields
        eBook.setDescription(eBookDTO.getDescription());
        eBook.setStaticURL(eBookDTO.getStaticURL());

        // Map attachment if necessary
        if (eBookDTO.getAttachmentDTO() != null) {
            eBook.setAttachment(attachmentMapper.toEntity(eBookDTO.getAttachmentDTO()));
        }

        return eBook;
    }
}

