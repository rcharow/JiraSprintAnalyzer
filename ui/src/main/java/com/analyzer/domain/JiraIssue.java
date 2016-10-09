package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rcharow on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue {
    private String id;
    private String self;
    private String key;
    private JiraIssueFields fields;

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

    public JiraIssueFields getFields() {
        return fields;
    }

    public void setFields(JiraIssueFields fields) {
        this.fields = fields;
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
