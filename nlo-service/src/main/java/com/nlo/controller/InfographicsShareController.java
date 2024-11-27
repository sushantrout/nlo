package com.nlo.controller;

import com.nlo.service.InfographicsShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/infographics-share")
@RequiredArgsConstructor
public class InfographicsShareController {
    private final InfographicsShareService infographicsShareService;

    @GetMapping(value = "/{infographicsId}")
    public String generateShareCode(@PathVariable String infographicsId) {
        return infographicsShareService.generateShareCode(infographicsId);
    }
}
