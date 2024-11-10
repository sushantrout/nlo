package com.nlo.controller;

import com.nlo.model.ConstituencyDTO;
import com.nlo.service.ConstituencyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/constituency")
public class ConstituencyController extends BaseController<ConstituencyDTO, ConstituencyService> {
    public ConstituencyController(ConstituencyService service) {
        super(service);
    }
}
