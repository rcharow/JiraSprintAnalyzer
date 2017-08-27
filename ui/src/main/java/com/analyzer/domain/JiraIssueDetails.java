package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rcharow on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueDetails {
    private Double points;
    private JiraIssueTypeFields issueType;
    private Integer timeSpent;
    private Integer aggregateTimeSpent;

    @JsonProperty("points")
    public Double getPoints() {
        return points;
    }

    @JsonProperty("customfield_10063")
    public void setPoints(Double points) {
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

    @JsonProperty("timeSpent")
    public Integer getTimeSpent() {
        return timeSpent;
    }

    @JsonProperty("timespent")
    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    @JsonProperty("aggregateTimeSpent")
    public Integer getAggregateTimeSpent() {
        return aggregateTimeSpent;
    }

    @JsonProperty("aggregatetimespent")
    public void setAggregateTimeSpent(Integer aggregateTimeSpent) {
        this.aggregateTimeSpent = aggregateTimeSpent;
    }

    @Override
    public String toString() {
        return "JiraIssueDetails{" +
                "points=" + points +
                ", issueType=" + issueType +
                ", timeSpent=" + timeSpent +
                ", aggregateTimeSpent=" + aggregateTimeSpent +
                '}';
    }
}
