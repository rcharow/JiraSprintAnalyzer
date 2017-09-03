package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rcharow on 10/9/16.
 */
@Entity
@Table(name = "jira_issue_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Double points;
  @Transient
  private JiraIssueTypeFields issueType;
  @Column(name = "time_logged_seconds")
  private Integer timeSpent;
  @Column(name = "aggregate_time_logged_seconds")
  private Integer aggregateTimeSpent;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty("points")
  public Double getPoints() {
    return points;
  }

  @JsonProperty("customfield_10063")
  public void setPoints(Double points) {
    this.points = points;
  }

  @JsonProperty("issueType")
  public JiraIssueTypeFields getIssueType() {
    return issueType;
  }

  @JsonProperty("issuetype")
  public void setIssueType(JiraIssueTypeFields issueType) {
    this.issueType = issueType;
  }

  @JsonProperty("timeSpent")
  public Integer getTimeSpent() {
    return timeSpent;
  }

  @JsonProperty("timespent")
  public void setTimeSpent(Integer timeSpent) {
    this.timeSpent = timeSpent;
  }

  @JsonProperty("aggregateTimeSpent")
  public Integer getAggregateTimeSpent() {
    return aggregateTimeSpent;
  }

  @JsonProperty("aggregatetimespent")
  public void setAggregateTimeSpent(Integer aggregateTimeSpent) {
    this.aggregateTimeSpent = aggregateTimeSpent;
  }

  @Override
  public String toString() {
    return "JiraIssueDetails{" +
        "points=" + points +
        ", issueType=" + issueType +
        ", timeSpent=" + timeSpent +
        ", aggregateTimeSpent=" + aggregateTimeSpent +
        '}';
  }
}
