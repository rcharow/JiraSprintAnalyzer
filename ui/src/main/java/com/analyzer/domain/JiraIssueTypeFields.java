package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rcharow on 10/25/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueTypeFields {
    private String id;
    private String description;
    private boolean isSubtask;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("isSubtask")
    public boolean isSubtask() {
        return isSubtask;
    }

    @JsonProperty("subtask")
    public void setSubtask(boolean subtask) {
        isSubtask = subtask;
    }
}
