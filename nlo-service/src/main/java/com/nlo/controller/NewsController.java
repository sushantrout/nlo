package com.nlo.controller;

import com.nlo.model.NewsDTO;
import com.nlo.model.ReactionDTO;
import com.nlo.service.NewsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController extends BaseController<NewsDTO, NewsService>{
    public NewsController(NewsService service) {
        super(service);
    }

    @GetMapping("/find-all-details")
    public List<NewsDTO> getAllWithReaction(){
        return service.getAllWithReaction();
    }

    @PostMapping("{newsId}/reaction")
    public NewsDTO reaction(@RequestBody ReactionDTO reactionDTO, @PathVariable String  newsId){
        return service.reaction(newsId, reactionDTO);
    }

    @GetMapping("/find-all-details/hot")
    public List<NewsDTO> getAllHotNews(){
        return service.getAllHotNews();
    }

    @PostMapping("/save-with-attachment")
    public NewsDTO saveWithAttachment(@RequestPart NewsDTO newsDTO, @RequestPart(required = false) List<MultipartFile> files) {
        return service.saveWithAttachment(newsDTO, files);
    }

    @PutMapping("hot/{newsId}/{value}")
    public NewsDTO makeHot(@PathVariable String  newsId, @PathVariable Boolean value) {
        return service.makeHot(newsId, value);
    }
}