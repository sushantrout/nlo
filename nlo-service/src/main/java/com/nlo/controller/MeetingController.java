package com.nlo.controller;

import com.nlo.model.MeetingDTO;
import com.nlo.service.MeetingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/meeting")
public class MeetingController extends BaseController<MeetingDTO, MeetingService> {
    public MeetingController(MeetingService service) {
        super(service);
    }
}
