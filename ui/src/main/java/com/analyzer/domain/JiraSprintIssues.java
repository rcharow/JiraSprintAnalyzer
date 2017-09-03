package com.analyzer.domain;

import java.util.List;

/**
 * Created by rcharow on 6/17/17.
 */
public class JiraSprintIssues {
    public JiraSprint sprint;
    private List<JiraIssue> jiraIssues;

    public List<JiraIssue> getJiraIssues() {
        return jiraIssues;
    }

    public JiraSprint getSprint() {
        return sprint;
    }

    public void setSprint(JiraSprint sprint) {
        this.sprint = sprint;
    }

    public void setJiraIssues(List<JiraIssue> jiraIssues) {
        this.jiraIssues = jiraIssues;
    }
}
