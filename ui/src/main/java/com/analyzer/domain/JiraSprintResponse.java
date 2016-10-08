package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by rcharow on 10/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSprintResponse extends JiraResponse{
    private List<JiraSprint> values;

    public List<JiraSprint> getValues() {
        return values;
    }

    public void setValues(List<JiraSprint> values) {
        this.values = values;
    }
}
