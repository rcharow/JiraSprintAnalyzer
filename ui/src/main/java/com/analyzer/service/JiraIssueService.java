package com.analyzer.service;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraIssueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by rcharow on 10/9/16.
 */
@Component
public class JiraIssueService extends JiraService {
    @Autowired
    public JiraIssueService(@Value("${jira.username}") String jiraUser, @Value("${jira.password}") String jiraPassword, @Value("${jira.self}") String jiraUrl) {
        super(jiraUser, jiraPassword, jiraUrl);
    }

    private JiraIssueResponse getSprintIssuePage(String sprintId, Long startPage){
        String requestUrl =  "/rest/agile/1.0/sprint/" + sprintId + "/issue?limit=50&startAt=0";
        //https://jira.portlandwebworks.com:8443/rest/agile/1.0/sprint/697/issue?limit=50&startAt=100

        if(startPage != 0L){
            requestUrl =  "/rest/agile/1.0/sprint/" + sprintId + "/issue?limit=50&startAt=" + startPage;
        }

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JiraIssueResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
                HttpMethod.GET,
                request,
                JiraIssueResponse.class
        );

        return response.getBody();
    }

    public List<JiraIssue> getSprintIssues(String sprintId){
        List<JiraIssue> issues;
        Boolean lastPage;
        Long startPage = 0L;

        JiraIssueResponse sprintResponse = getSprintIssuePage(sprintId, startPage);
        issues = sprintResponse.getIssues();
        lastPage = issues.size() == 0;

        while(!lastPage){
            startPage = startPage + 50;
            sprintResponse = getSprintIssuePage(sprintId, startPage);
            lastPage = sprintResponse.getIssues().size() == 0;
            issues.addAll(sprintResponse.getIssues());
        }

        return issues;
    }
}
