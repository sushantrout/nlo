package com.nlo.controller;

import com.nlo.model.SubIssueDTO;
import com.nlo.service.SubIssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/sub-issue")
public class SubIssueController extends BaseController<SubIssueDTO, SubIssueService> {
    public SubIssueController(SubIssueService service) {
        super(service);
    }

    @GetMapping("by-issue/{issueId}")
    public List<SubIssueDTO> findByIssueId(@PathVariable String issueId) {
        return service.findByIssueId(issueId);
    }
}
