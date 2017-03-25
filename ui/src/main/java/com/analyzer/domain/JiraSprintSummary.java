package com.analyzer.domain;

/**
 * Created by rcharow on 2/19/17.
 */
public class JiraSprintSummary {
    private String id;
    private String self;
    private Integer totalIssues;
    private Integer totalPoints;
    private Integer totalTimeSeconds;
    private double clientTotalCost;
    private double internalTotalCost;
    private double margin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public Integer getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(Integer totalIssues) {
        this.totalIssues = totalIssues;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    public void setTotalTimeSeconds(Integer totalTimeSeconds) {
        this.totalTimeSeconds = totalTimeSeconds;
    }

    public double getClientTotalCost() {
        return clientTotalCost;
    }

    public void setClientTotalCost(double clientTotalCost) {
        this.clientTotalCost = clientTotalCost;
    }

    public double getInternalTotalCost() {
        return internalTotalCost;
    }

    public void setInternalTotalCost(double internalTotalCost) {
        this.internalTotalCost = internalTotalCost;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }
}
