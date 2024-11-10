package com.nlo.mapper;

import com.nlo.entity.Issue;
import com.nlo.model.IssueDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class IssueMapper implements BaseMapper<IssueDTO, Issue> {
    @Override
    public IssueDTO toDto(Issue issue) {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setTitle(issue.getTitle());
        return issueDTO;
    }

    @Override
    public Issue toEntity(IssueDTO issueDTO) {
        Issue issue = new Issue();
        issue.setId(issueDTO.getId());
        issue.setTitle(issueDTO.getTitle());
        return issue;
    }
}
