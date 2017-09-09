package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by rcharow on 10/8/16.
 */
@Entity
@Table(name = "jira_sprint")
@JsonIgnoreProperties(ignoreUnknown = true )
public class JiraSprint implements Comparable<JiraSprint> {
    @Id
    @Column(name = "sprint_id")
    private String id;
    @Column(name = "jira_url")
    private String self;
    private String state;
    @Column(name = "sprint_name")
    private String name;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "complete_date")
    private Date completeDate;
    @Column(name = "origin_board")
    private String originBoardId;
    @Column(name = "issues_synced")
    private Boolean issuesSynced = false;
    @Column(name = "worklogs_synced")
    private Boolean worklogsSynced = false;

    public enum sprintStates {
        FUTURE, ACTIVE, CLOSED
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getOriginBoardId() {
        return originBoardId;
    }

    public void setOriginBoardId(String originBoardId) {
        this.originBoardId = originBoardId;
    }

    public Boolean getIssuesSynced() {
        return issuesSynced;
    }

    public void setIssuesSynced(Boolean issuesSynced) {
        this.issuesSynced = issuesSynced;
    }

    public Boolean getWorklogsSynced() {
        return worklogsSynced;
    }

    public void setWorklogsSynced(Boolean worklogsSynced) {
        this.worklogsSynced = worklogsSynced;
    }

    public int compareTo(JiraSprint sprint) {
        return completeDate.compareTo(sprint.completeDate);
    }


}
