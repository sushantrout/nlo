package com.nlo.mapper;

import com.nlo.entity.Issue;
import com.nlo.entity.SubIssue;
import com.nlo.model.SubIssueDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
public class SubIssueMapper implements BaseMapper<SubIssueDTO, SubIssue> {
    @Override
    public SubIssueDTO toDto(SubIssue subIssue) {
        SubIssueDTO dto = new SubIssueDTO();
        dto.setId(subIssue.getId());
        dto.setTitle(subIssue.getTitle());
        Issue issue = subIssue.getIssue();
        if(Objects.nonNull(issue)) {
            dto.setIssueId(issue.getId());
        }
        return dto;
    }

    @Override
    public SubIssue toEntity(SubIssueDTO subIssueDTO) {
        SubIssue subIssue = new SubIssue();
        subIssue.setId(subIssueDTO.getId());
        subIssue.setTitle(subIssueDTO.getTitle());
        return subIssue;
    }
}
