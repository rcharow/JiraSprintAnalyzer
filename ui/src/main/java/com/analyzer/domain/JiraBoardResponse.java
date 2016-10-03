package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rcharow on 10/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraBoardResponse {
    private Integer maxResults;
    private Integer startAt;
    private Boolean isLast;
    private List<JiraBoard> values;

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public Integer getStartAt() {
        return startAt;
    }

    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    public List<JiraBoard> getValues() {
        return values;
    }

    public void setValues(List<JiraBoard> values) {
        this.values = values;
    }
}
