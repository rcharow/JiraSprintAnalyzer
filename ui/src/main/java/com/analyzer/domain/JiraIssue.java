package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rcharow on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue {
    private String id;
    private String self;
    private String key;
    private JiraIssueDetails details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("details")
    public JiraIssueDetails getDetails() {
        return details;
    }

    @JsonProperty("fields")
    public void setDetails(JiraIssueDetails details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "JiraIssue{" +
                "id='" + id + '\'' +
                ", self='" + self + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
