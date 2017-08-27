package com.analyzer.jira;

import com.analyzer.domain.JiraSprint;
import com.analyzer.domain.JiraSprintResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class JiraSprintService extends JiraService {

    @Autowired
    public JiraSprintService(@Value("${jira.username}") String jiraUser, @Value("${jira.password}") String jiraPassword, @Value("${jira.self}") String jiraUrl) {
        super(jiraUser, jiraPassword, jiraUrl);
    }

    public List<JiraSprint> getSprints(String boardId) {

        List<JiraSprint> sprints;
        Boolean lastPage;
        Long startPage = 0L;

        try {

            JiraSprintResponse boardResponse = getSprintPage(boardId, startPage);
            lastPage = boardResponse.getIsLast();
            sprints = boardResponse.getValues();

            while (!lastPage) {
                startPage = startPage + 50;
                boardResponse = getSprintPage(boardId, startPage);
                lastPage = boardResponse.getIsLast();
                sprints.addAll(boardResponse.getValues());
            }

            return sprints;
        } catch (Exception e) {
            throw new JiraException();
        }
    }

    private JiraSprintResponse getSprintPage(String boardId, Long startPage) {
        String requestUrl = "/rest/agile/1.0/board/" + boardId + "/sprint?limit=50";

        if (startPage != 0L) {
            requestUrl = "/rest/agile/1.0/board/" + boardId + "/sprint?limit=50&startAt=" + startPage;
        }

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        try {

            ResponseEntity<JiraSprintResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
                    HttpMethod.GET,
                    request,
                    JiraSprintResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new JiraException();
        }
    }

    private JiraSprintResponse getSprintPage(String boardId, Long startPage, String sprintStates) {
        String requestUrl = "/rest/agile/1.0/board/" + boardId + "/sprint?state=" + sprintStates + "&limit=50";

        if (startPage != 0L) {
            requestUrl = "/rest/agile/1.0/board/" + boardId + "/sprint?state=" + sprintStates + "&limit=50&startAt=" + startPage;
        }

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        try {

            ResponseEntity<JiraSprintResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
                    HttpMethod.GET,
                    request,
                    JiraSprintResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new JiraException();
        }
    }

}
