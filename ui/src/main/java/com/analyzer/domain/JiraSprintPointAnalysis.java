package com.analyzer.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by rcharow on 6/17/17.
 */
public class JiraSprintPointAnalysis {
    private String sprintId;
    private String sprintName;
    private Date startDate;
    private Date endDate;
    private List<JiraPointAnalysis> pointAnalysis;

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public  List<JiraPointAnalysis> getPointAnalysis() {
        return pointAnalysis;
    }

    public void setPointAnalysis( List<JiraPointAnalysis> pointAnalysis) {
        this.pointAnalysis = pointAnalysis;
    }

}
