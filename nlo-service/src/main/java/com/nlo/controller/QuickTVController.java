package com.nlo.controller;

import com.nlo.model.QuickTVDTO;
import com.nlo.service.QuickTVService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/quick-tv")
public class QuickTVController extends BaseController<QuickTVDTO, QuickTVService> {
    public QuickTVController(QuickTVService service) {
        super(service);
    }
}
