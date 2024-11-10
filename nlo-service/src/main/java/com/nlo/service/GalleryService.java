package com.nlo.service;

import com.nlo.entity.Gallery;
import com.nlo.mapper.GalleryMapper;
import com.nlo.model.GalleryDTO;
import com.nlo.repository.GalleryRepository;
import com.nlo.validation.GalleryValidation;
import org.springframework.stereotype.Service;

@Service
public class GalleryService extends BaseServiceImpl<Gallery, GalleryDTO, GalleryMapper, GalleryValidation, GalleryRepository>{
    protected GalleryService(GalleryRepository repository, GalleryMapper mapper, GalleryValidation validation) {
        super(repository, mapper, validation);
    }
}