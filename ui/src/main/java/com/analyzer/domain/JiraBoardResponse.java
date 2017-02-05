package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by rcharow on 10/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraBoardResponse extends JiraResponse{
    private List<JiraBoard> values;

    public List<JiraBoard> getValues() {
        return values;
    }

    public void setValues(List<JiraBoard> values) {
        this.values = values;
    }
}
