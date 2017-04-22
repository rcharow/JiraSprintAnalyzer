package com.analyzer.domain;

import java.util.List;

/**
 * Created by rcharow on 4/22/17.
 */
public class JiraWorklogSummary {
    private String sprintId;
    private List<JiraWorklogSummaryItem> worklogs;

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public List<JiraWorklogSummaryItem> getWorklogs() {
        return worklogs;
    }

    public void setWorklogs(List<JiraWorklogSummaryItem> worklogs) {
        this.worklogs = worklogs;
    }
}
