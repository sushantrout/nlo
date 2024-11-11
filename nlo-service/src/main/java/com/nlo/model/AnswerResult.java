package com.nlo.model;

import lombok.Data;

import java.util.Map;

@Data
public class AnswerResult {
    private Map<String, Long> constituencyCounts;
    private Long totalResponses;

    // Getters and Setters
    public Map<String, Long> getConstituencyCounts() {
        return constituencyCounts;
    }

    public void setConstituencyCounts(Map<String, Long> constituencyCounts) {
        this.constituencyCounts = constituencyCounts;
    }

    public Long getTotalResponses() {
        return totalResponses;
    }

    public void setTotalResponses(Long totalResponses) {
        this.totalResponses = totalResponses;
    }
}