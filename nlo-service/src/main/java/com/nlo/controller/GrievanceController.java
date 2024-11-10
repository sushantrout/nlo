package com.nlo.controller;

import com.nlo.model.GrievanceDTO;
import com.nlo.service.GrievanceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "api/grievance")
public class GrievanceController extends BaseController<GrievanceDTO, GrievanceService> {
    public GrievanceController(GrievanceService service) {
        super(service);
    }

    @PostMapping("/save-with-attachment")
    public GrievanceDTO saveWithAttachment(@RequestPart GrievanceDTO grievanceDTO, @RequestPart(required = false) List<MultipartFile> files) {
        return service.saveWithAttachment(grievanceDTO, files);
    }
}
