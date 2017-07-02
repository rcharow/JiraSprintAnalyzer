package com.analyzer.domain;

/**
 * Created by rcharow on 7/2/17.
 */
public class JiraSprintPointAverage {
    private String sprintId;
    private String sprintName;
    private Double averageHoursPerPoint;
    private Double averageDollarsPerPoint;

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

    public Double getAverageHoursPerPoint() {
        return averageHoursPerPoint;
    }

    public void setAverageHoursPerPoint(Double averageHoursPerPoint) {
        this.averageHoursPerPoint = averageHoursPerPoint;
    }

    public Double getAverageDollarsPerPoint() {
        return averageDollarsPerPoint;
    }

    public void setAverageDollarsPerPoint(Double averageDollarsPerPoint) {
        this.averageDollarsPerPoint = averageDollarsPerPoint;
    }
}
