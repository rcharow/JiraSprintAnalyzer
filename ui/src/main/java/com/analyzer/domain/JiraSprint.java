package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * Created by rcharow on 10/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true )
public class JiraSprint implements Comparable<JiraSprint> {
    private String id;
    private String self;
    private String state;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private Integer originBoardId;

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

    public Integer getOriginBoardId() {
        return originBoardId;
    }

    public void setOriginBoardId(Integer originBoardId) {
        this.originBoardId = originBoardId;
    }

    public int compareTo(JiraSprint sprint) {
        return completeDate.compareTo(sprint.completeDate);
    }


}
