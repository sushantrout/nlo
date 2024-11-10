package com.nlo.service;

import com.nlo.entity.Issue;
import com.nlo.mapper.IssueMapper;
import com.nlo.model.IssueDTO;
import com.nlo.repository.IssueRepository;
import com.nlo.validation.IssueValidation;
import org.springframework.stereotype.Service;

@Service
public class IssueService extends BaseServiceImpl<Issue, IssueDTO, IssueMapper, IssueValidation, IssueRepository> {
    protected IssueService(IssueRepository repository, IssueMapper mapper, IssueValidation validation) {
        super(repository, mapper, validation);
    }
}
