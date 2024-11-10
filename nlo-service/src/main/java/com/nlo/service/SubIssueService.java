package com.nlo.service;

import com.nlo.entity.Issue;
import com.nlo.entity.SubIssue;
import com.nlo.mapper.SubIssueMapper;
import com.nlo.model.SubIssueDTO;
import com.nlo.repository.IssueRepository;
import com.nlo.repository.SubIssueRepository;
import com.nlo.validation.SubIssueValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SubIssueService extends BaseServiceImpl<SubIssue, SubIssueDTO, SubIssueMapper, SubIssueValidation, SubIssueRepository> {
    @Autowired
    private IssueRepository issueRepository;

    protected SubIssueService(SubIssueRepository repository, SubIssueMapper mapper, SubIssueValidation validation) {
        super(repository, mapper, validation);
    }

    public SubIssueDTO create(SubIssueDTO subIssueDTO) {
        SubIssue entity = mapper.toEntity(subIssueDTO);
        Issue issue = issueRepository.findById(subIssueDTO.getIssueId()).orElseThrow();
        entity.setIssue(issue);
        SubIssue save = repository.save(entity);
        return mapper.toDto(save);
    }

    public List<SubIssueDTO> findByIssueId(String issueId) {
        return mapper.toDtoList(repository.findByIssueId(issueId)
                .stream().filter(si -> Objects.isNull(si.getDeleted()) || !si.getDeleted()).toList());
    }
}
