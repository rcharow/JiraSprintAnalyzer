package com.analyzer.domain;

/**
 * Created by rcharow on 6/17/17.
 */
public class JiraPointAnalysis {

    private double pointValue;
    private float avgTimeSpentSeconds;
    private float avgDollarsSpent;

    public double getPointValue() {
        return pointValue;
    }

    public void setPointValue(double pointValue) {
        this.pointValue = pointValue;
    }

    public float getAvgTimeSpentSeconds() {
        return avgTimeSpentSeconds;
    }

    public void setAvgTimeSpentSeconds(float avgTimeSpentSeconds) {
        this.avgTimeSpentSeconds = avgTimeSpentSeconds;
    }

    public float getAvgDollarsSpent() {
        return avgDollarsSpent;
    }

    public void setAvgDollarsSpent(Float avgDollarsSpent) {
        this.avgDollarsSpent = avgDollarsSpent;
    }
}
