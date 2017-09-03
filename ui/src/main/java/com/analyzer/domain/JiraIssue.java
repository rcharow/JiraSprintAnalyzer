package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rcharow on 10/9/16.
 */
@Entity
@Table(name = "jira_issue")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue {
    @Id
    @Column(name = "issue_id")
    private String id;
    @Column(name = "board_id")
    private String boardId;
    @Column(name = "sprint_id")
    private String sprintId;
    @Column(name = "is_subtask")
    private boolean isSubtask;
    @Column(name = "jira_url")
    private String self;
    @Column(name = "issue_key")
    private String key;
    @OneToOne()
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "details")
    private JiraIssueDetails details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public Boolean isSubtask() {
        return isSubtask;
    }

    public void setIsSubtask(Boolean subtask) {
        isSubtask = subtask;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("details")
    public JiraIssueDetails getDetails() {
        return details;
    }

    @JsonProperty("fields")
    public void setDetails(JiraIssueDetails details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "JiraIssue{" +
                "id='" + id + '\'' +
                ", self='" + self + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
