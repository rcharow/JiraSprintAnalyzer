package com.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rcharow on 10/1/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraProject {
    private String id;
    private String name;
    private String key;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key){
        this.key = key;
    }

    @Override
    public String toString() {
        return "Id{" +
                "id='" + id + '\'' +
                ", name=" + name + '\'' +
                ", key=" + key +
                '}';
    }
}
