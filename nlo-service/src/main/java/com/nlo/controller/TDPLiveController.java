package com.nlo.controller;

import com.nlo.model.TDPLiveDTO;
import com.nlo.service.TDPLiveService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/tdb-live")
public class TDPLiveController extends BaseController<TDPLiveDTO, TDPLiveService> {
    public TDPLiveController(TDPLiveService service) {
        super(service);
    }
}
