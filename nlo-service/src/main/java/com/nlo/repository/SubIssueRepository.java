package com.nlo.repository;

import com.nlo.entity.SubIssue;

import java.util.List;

public interface SubIssueRepository extends BaseRepository<SubIssue> {
    List<SubIssue> findByIssueId(String issueId);
}
