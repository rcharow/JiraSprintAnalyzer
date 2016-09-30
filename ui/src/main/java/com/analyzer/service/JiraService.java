package com.analyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by rcharow on 9/30/16.
 */
@Component
@PropertySource(value = "config.properties")
public class JiraService {

    @Autowired
    @Value("${jira.username}")
    private String jiraUser;

    @Autowired
    @Value("${jira.password}")
    private String jiraPassword;

    @Autowired
    @Value("${jira.url}")
    private String jiraUrl;


    public String getProjects() {
        return this.jiraUrl;
    }
}
