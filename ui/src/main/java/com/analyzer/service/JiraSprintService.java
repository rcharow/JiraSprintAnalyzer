package com.analyzer.service;

import com.analyzer.domain.JiraSprint;
import com.analyzer.domain.JiraSprintResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by rcharow on 10/9/16.
 */

@Component
public class JiraSprintService extends JiraService{

    @Autowired
    public JiraSprintService(@Value("${jira.username}") String jiraUser, @Value("${jira.password}") String jiraPassword, @Value("${jira.self}") String jiraUrl) {
        super(jiraUser, jiraPassword, jiraUrl);
    }

    public List<JiraSprint> getSprints(String boardId) {

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JiraSprintResponse> response = restTemplate.exchange(jiraUrl + "/rest/agile/1.0/board/" + boardId + "/sprint",
                HttpMethod.GET,
                request,
                JiraSprintResponse.class
        );

        JiraSprintResponse sprintsResponse = response.getBody();

        return sprintsResponse.getValues();
    }

    public List<JiraSprint> getSprints(String boardId, String sprintStates) {

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JiraSprintResponse> response =
                restTemplate.exchange(jiraUrl + "/rest/agile/1.0/board/" + boardId + "/sprint?state=" + sprintStates,
                        HttpMethod.GET,
                        request,
                        JiraSprintResponse.class
                );

        JiraSprintResponse sprintsResponse = response.getBody();

        return sprintsResponse.getValues();
    }
}
