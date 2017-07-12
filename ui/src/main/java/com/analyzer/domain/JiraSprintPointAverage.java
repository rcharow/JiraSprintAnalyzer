package com.analyzer.domain;

/**
 * Created by rcharow on 7/2/17.
 */
public class JiraSprintPointAverage {
    private String sprintId;
    private String sprintName;
    private Integer totalCompletedIssues;
    private Double totalHours;
    private Double totalDollars;
    private Double totalCompletedPoints;
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

    public Integer getTotalCompletedIssues() {
        return totalCompletedIssues;
    }

    public void setTotalCompletedIssues(Integer totalCompletedIssues) {
        this.totalCompletedIssues = totalCompletedIssues;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public Double getTotalDollars() {
        return totalDollars;
    }

    public void setTotalDollars(Double totalDollars) {
        this.totalDollars = totalDollars;
    }

    public Double getTotalCompletedPoints() {
        return totalCompletedPoints;
    }

    public void setTotalCompletedPoints(Double totalCompletedPoints) {
        this.totalCompletedPoints = totalCompletedPoints;
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
