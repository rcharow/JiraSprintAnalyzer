package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rcharow on 10/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraResponse {
    private Integer maxResults;
    private Long startAt;
    private Integer total;
    private Boolean isLast;

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(boolean last) {
        isLast = last;
    }
}
