package com.analyzer.domain;

/**
 * Created by rcharow on 7/6/17.
 */
public class JiraRapidViewField {
    private String text;
    private Double value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
