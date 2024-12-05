package com.nlo.controller;

import com.nlo.constant.TimeSpan;
import com.nlo.model.ApplicationRatingDTO;
import com.nlo.model.UserRate;
import com.nlo.service.ApplicationRatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/application-rating")
public class ApplicationRatingController extends BaseController<ApplicationRatingDTO, ApplicationRatingService> {
    public ApplicationRatingController(ApplicationRatingService service) {
        super(service);
    }

    @GetMapping("/top-rated/{limit}/{timeSpan}")
    public List<UserRate> getTopRated(@PathVariable Long limit, @PathVariable TimeSpan timeSpan) {
        return service.getTopRated(limit, timeSpan);
    }
}
