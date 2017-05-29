package com.analyzer.domain;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rcharow on 2/19/17.
 */
public class JiraSprintSummary {
    private String id;
    private String self;
    private String name;
    private Integer totalIssues;
    private Integer totalPoints;
    private double totalTimeHours;
    private double clientTotalCost;
    private double internalTotalCost;
    private double margin;
    private List<JiraWorklogSummaryItem> worklogSummaryHours;
    private JiraWorklogSummary worklogSummary;


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

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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

    public double getTotalTimeHours() {
        return totalTimeHours;
    }

    public void setTotalTimeHours(double totalTimeHours) {
        this.totalTimeHours = totalTimeHours;
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

    public List<JiraWorklogSummaryItem> getWorklogSummaryHours() {
        return worklogSummaryHours;
    }

    public void setWorklogSummaryHours(List<JiraWorklogSummaryItem> worklogSummaryHours) {
        this.worklogSummaryHours = worklogSummaryHours;
    }

    public JiraWorklogSummary getWorklogSummary() {
        return worklogSummary;
    }

    public void setWorklogSummary(JiraWorklogSummary worklogSummary) {
        this.worklogSummary = worklogSummary;
    }
}
