package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rcharow on 7/6/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true )
public class JiraSprintRapidView {
    private JiraSprintRapidViewContents contents;
    private JiraRapidViewSprint sprint;

    public JiraSprintRapidViewContents getContents() {
        return contents;
    }

    public void setContents(JiraSprintRapidViewContents contents) {
        this.contents = contents;
    }

    public JiraRapidViewSprint getSprint() {
        return sprint;
    }

    public void setSprint(JiraRapidViewSprint sprint) {
        this.sprint = sprint;
    }
}
