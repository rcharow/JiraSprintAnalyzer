package com.analyzer.service;

import com.analyzer.domain.JiraBoard;
import com.analyzer.domain.JiraBoardResponse;
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
public class JiraBoardService extends JiraService {

    @Autowired
    public JiraBoardService(@Value("${jira.username}") String jiraUser, @Value("${jira.password}") String jiraPassword, @Value("${jira.self}") String jiraUrl) {
        super(jiraUser, jiraPassword, jiraUrl);
    }

    public List<JiraBoard> getAllBoards() {
        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JiraBoardResponse> response = restTemplate.exchange(jiraUrl + "/rest/agile/1.0/board",
                HttpMethod.GET,
                request,
                JiraBoardResponse.class
        );

        JiraBoardResponse boardResponse = response.getBody();

        return boardResponse.getValues();
    }

    public JiraBoard getBoard(String boardId) {
        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JiraBoard> response = restTemplate.exchange(jiraUrl + "/rest/agile/1.0/board/" + boardId,
                HttpMethod.GET,
                request,
                JiraBoard.class
        );

        JiraBoard board = response.getBody();

        return board;
    }
}
