package com.nlo.controller;

import com.nlo.model.StoriesDTO;
import com.nlo.service.StoriesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/stories")
public class StoriesController extends BaseController<StoriesDTO, StoriesService> {
    public StoriesController(StoriesService service) {
        super(service);
    }

    @PostMapping(value = "/save-with-attachment")
    public StoriesDTO saveStories(@RequestPart StoriesDTO storiesDTO, @RequestPart(required = false) List<MultipartFile> files) {
        return service.saveStoriesWithAttachment(storiesDTO, files);
    }

}
