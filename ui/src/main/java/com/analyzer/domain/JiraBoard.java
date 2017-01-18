package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rcharow on 10/1/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraBoard implements Comparable<JiraBoard>{
    private String id;
    private String self;
    private String name;
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
