package com.nlo.model;

import lombok.Data;

import java.util.Map;

@Data
public class PollResult {
    private String pollId;
    private Map<String, AnswerResult> answers;

    // Getters and Setters
    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public Map<String, AnswerResult> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, AnswerResult> answers) {
        this.answers = answers;
    }
}
