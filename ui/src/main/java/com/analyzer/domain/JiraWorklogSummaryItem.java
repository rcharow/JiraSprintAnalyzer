package com.analyzer.domain;

/**
 * Created by rcharow on 4/8/17.
 */
class JiraWorklogSummaryItem {
    private String author;
    private double totalTimeHours;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getTotalTimeHours() {
        return totalTimeHours;
    }

    public void setTotalTimeHours(double totalTimeHours) {
        this.totalTimeHours = totalTimeHours;
    }
}
