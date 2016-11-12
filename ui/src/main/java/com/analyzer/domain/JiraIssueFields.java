package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rcharow on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueFields {
    private Integer points;
    private JiraIssueTypeFields issueType;

    @JsonProperty("points")
    public Integer getPoints() {
        return points;
    }

    @JsonProperty("customfield_10063")
    public void setPoints(Integer points) {
        this.points = points;
    }

    @JsonProperty("issueType")
    public JiraIssueTypeFields getIssueType() {
        return issueType;
    }

    @JsonProperty("issuetype")
    public void setIssueType(JiraIssueTypeFields issueType) {
        this.issueType = issueType;
    }

    @Override
    public String toString() {
        return "JiraIssueFields{" +
                "points=" + points +
                ", issueType=" + issueType +
                '}';
    }
}
