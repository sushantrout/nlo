package com.nlo.controller;

import com.nlo.model.ConstituencyDTO;
import com.nlo.service.ConstituencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/constituency")
public class ConstituencyController extends BaseController<ConstituencyDTO, ConstituencyService> {
    public ConstituencyController(ConstituencyService service) {
        super(service);
    }

    @GetMapping("/dropdown")
    public List<ConstituencyDTO> findDropdownData() {
        return service.findDropdownData();
    }
}
