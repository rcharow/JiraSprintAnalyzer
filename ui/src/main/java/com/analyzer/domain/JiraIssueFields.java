package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rcharow on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueFields {
    private Integer points;
    private JiraIssueTypeFields issuetype;
    private Boolean isSubtask;

    @JsonProperty("points")
    public Integer getPoints() {
        return points;
    }

    @JsonProperty("customfield_10063")
    public void setPoints(Integer points) {
        this.points = points;
    }

    public JiraIssueTypeFields getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(JiraIssueTypeFields issuetype) {
        this.issuetype = issuetype;
    }

    @Override
    public String toString() {
        return "JiraIssueFields{" +
                "points=" + points +
                ", issuetype=" + issuetype +
                '}';
    }
}
