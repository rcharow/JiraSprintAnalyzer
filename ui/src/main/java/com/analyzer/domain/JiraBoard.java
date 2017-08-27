package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by rcharow on 10/1/16.
 */
//annotations


//id
//use @column to rename sql reserved words
//snake case db table names
@Entity
@Table(name = "jira_board")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraBoard implements Comparable<JiraBoard>, Serializable{
    @Id
    @Column(name = "board_id")
    private String id;
    @Column(name = "jira_url")
    private String self;
    @Column(name = "board_name")
    private String name;
    @Column(name = "board_type")
    private String type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JiraBoard{" +
                "id='" + id + '\'' +
                ", self='" + self + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public int compareTo(JiraBoard board) {
        return name.compareTo(board.name);
    }

}
