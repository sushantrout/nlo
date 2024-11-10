package com.nlo.controller;

import com.nlo.model.GalleryDTO;
import com.nlo.service.GalleryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController extends BaseController<GalleryDTO, GalleryService>{
    public GalleryController(GalleryService service) {
        super(service);
    }
}