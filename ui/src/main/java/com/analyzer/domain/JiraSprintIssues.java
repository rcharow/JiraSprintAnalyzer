package com.analyzer.domain;

import java.util.List;

/**
 * Created by rcharow on 6/17/17.
 */
public class JiraSprintIssues {
    public JiraSprint sprint;
    public List<JiraIssue> getParentIssues() {
        return parentIssues;
    }

    public JiraSprint getSprint() {
        return sprint;
    }

    public void setSprint(JiraSprint sprint) {
        this.sprint = sprint;
    }

    public void setParentIssues(List<JiraIssue> parentIssues) {
        this.parentIssues = parentIssues;
    }

    private List<JiraIssue> parentIssues;
}
