package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by rcharow on 4/8/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraWorklogResponse extends JiraResponse {
    private List<JiraWorklog> worklogs;

    public List<JiraWorklog> getWorklogs() {
        return worklogs;
    }

    public void setWorklogs(List<JiraWorklog> worklogs) {
        this.worklogs = worklogs;
    }
}
