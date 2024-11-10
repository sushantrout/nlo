package com.nlo.controller;

import com.nlo.model.PollDTO;
import com.nlo.service.PollService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pool")
public class PollController extends BaseController<PollDTO, PollService> {
    public PollController(PollService service) {
        super(service);
    }

    @GetMapping("get-latest")
    public PollDTO getLatest() {
        return service.getLatest();
    }
}
