package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by rcharow on 4/8/17.
 */
@Entity
@Table(name = "jira_worklog")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraWorklog {
    @Id
    @Column(name = "worklog_id")
    private String id;
    @Column(name = "jira_url")
    private String self;
    private JiraWorklogAuthor author;
    private String comment;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "time_logged_seconds")
    private Integer timeSpentSeconds;
    @Column(name = "issue_id")
    private String issueId;
    @Column(name = "sprint_id")
    private String sprintId;
    @Column(name = "board_id")
    private String boardId;

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

    public JiraWorklogAuthor getAuthor() {
        return author;
    }

    public void setAuthor(JiraWorklogAuthor author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public void setTimeSpentSeconds(Integer timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
