package com.nlo.controller;

import com.nlo.constant.TimeSpan;
import com.nlo.model.ApiResponse;
import com.nlo.model.ApplicationRatingDTO;
import com.nlo.model.CurrentUserRate;
import com.nlo.model.UserRate;
import com.nlo.service.ApplicationRatingService;
import org.springframework.http.HttpStatus;
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
    public ApiResponse getTopRated(@PathVariable Long limit, @PathVariable TimeSpan timeSpan) {
        List<UserRate> topRated = service.getTopRated(limit, timeSpan, null);
        return ApiResponse.builder()
                .data(topRated)
                .status(HttpStatus.OK.toString())
                .build();
    }

    @GetMapping("/me")
    public ApiResponse myRating() {
        CurrentUserRate userRates = service.myRating();
        return ApiResponse.builder()
                .data(userRates)
                .status(HttpStatus.OK.toString())
                .build();
    }
}
