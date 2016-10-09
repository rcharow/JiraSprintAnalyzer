package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by rcharow on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueResponse extends JiraResponse {
    private List<JiraIssue> issues;

    public List<JiraIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<JiraIssue> issues) {
        this.issues = issues;
    }
}
