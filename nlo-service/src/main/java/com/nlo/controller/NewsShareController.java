package com.nlo.controller;

import com.nlo.service.NewsShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/news-share")
@RequiredArgsConstructor
public class NewsShareController {
    private final NewsShareService newsShareService;

    @GetMapping(value = "/{newsId}")
    public String generateShareCode(@PathVariable String newsId) {
        return newsShareService.generateShareCode(newsId);
    }
}
