package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by rcharow on 7/6/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSprintRapidViewContents {
    private List<JiraIssue> completedIssues;
    private List<JiraIssue> issuesNotCompletedInCurrentSprint;
    private JiraRapidViewField completedIssuesEstimateSum;
    private JiraRapidViewField issuesNotCompletedEstimateSum;
    private JiraRapidViewField allIssuesEstimateSum;

    public List<JiraIssue> getCompletedIssues() {
        return completedIssues;
    }

    public void setCompletedIssues(List<JiraIssue> completedIssues) {
        this.completedIssues = completedIssues;
    }

    public List<JiraIssue> getIssuesNotCompletedInCurrentSprint() {
        return issuesNotCompletedInCurrentSprint;
    }

    public void setIssuesNotCompletedInCurrentSprint(List<JiraIssue> issuesNotCompletedInCurrentSprint) {
        this.issuesNotCompletedInCurrentSprint = issuesNotCompletedInCurrentSprint;
    }

    public JiraRapidViewField getCompletedIssuesEstimateSum() {
        return completedIssuesEstimateSum;
    }

    public void setCompletedIssuesEstimateSum(JiraRapidViewField completedIssuesEstimateSum) {
        this.completedIssuesEstimateSum = completedIssuesEstimateSum;
    }

    public JiraRapidViewField getIssuesNotCompletedEstimateSum() {
        return issuesNotCompletedEstimateSum;
    }

    public void setIssuesNotCompletedEstimateSum(JiraRapidViewField issuesNotCompletedEstimateSum) {
        this.issuesNotCompletedEstimateSum = issuesNotCompletedEstimateSum;
    }

    public JiraRapidViewField getAllIssuesEstimateSum() {
        return allIssuesEstimateSum;
    }

    public void setAllIssuesEstimateSum(JiraRapidViewField allIssuesEstimateSum) {
        this.allIssuesEstimateSum = allIssuesEstimateSum;
    }
}
