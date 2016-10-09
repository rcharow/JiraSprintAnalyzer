package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rcharow on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueFields {
    private Integer customfield_10063;
    private String description;

    public Integer getCustomfield_10063() {
        return customfield_10063;
    }

    public void setCustomfield_10063(Integer customfield_10063) {
        this.customfield_10063 = customfield_10063;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
