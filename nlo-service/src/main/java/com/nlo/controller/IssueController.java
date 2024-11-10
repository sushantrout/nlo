package com.nlo.controller;

import com.nlo.model.IssueDTO;
import com.nlo.service.IssueService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/issue")
public class IssueController extends BaseController<IssueDTO, IssueService> {
    public IssueController(IssueService service) {
        super(service);
    }
}
