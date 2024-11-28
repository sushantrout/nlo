package com.nlo.controller;

import com.nlo.model.EBookDTO;
import com.nlo.model.NewsDTO;
import com.nlo.service.EBookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/ebook")
public class EBookController extends BaseController<EBookDTO, EBookService> {
    public EBookController(EBookService service) {
        super(service);
    }

    @PostMapping("/save-with-attachment")
    public EBookDTO saveWithAttachment(@RequestPart EBookDTO eBookDTO, @RequestPart(required = false) MultipartFile file) {
        return service.saveWithAttachment(eBookDTO, file);
    }
}
