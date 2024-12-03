package com.nlo.controller;

import com.nlo.model.ApplicationRatingDTO;
import com.nlo.service.ApplicationRatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/application-rating")
public class ApplicationRatingController extends BaseController<ApplicationRatingDTO, ApplicationRatingService> {
    public ApplicationRatingController(ApplicationRatingService service) {
        super(service);
    }

    @GetMapping("/top-rated/{limit}")
    public void getTopRated(@PathVariable Long limit) {
        service.getTopRated(limit);
    }
}
