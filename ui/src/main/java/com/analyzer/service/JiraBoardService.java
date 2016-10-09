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

import java.util.Collections;
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

    private JiraBoardResponse getBoardPage(Long startPage) {
        String requestUrl = "/rest/agile/1.0/board?limit=50";

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        if(startPage != 0L){
            requestUrl = "/rest/agile/1.0/board?limit=50&startAt=" + startPage;
        }

        ResponseEntity<JiraBoardResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
                HttpMethod.GET,
                request,
                JiraBoardResponse.class
        );


        return response.getBody();
    }

    public List<JiraBoard> getAllBoards() {
        List<JiraBoard> boards;
        Boolean lastPage;
        Long startPage = 0L;

        JiraBoardResponse boardResponse = getBoardPage(startPage);
        lastPage = boardResponse.getIsLast();
        boards = boardResponse.getValues();

        while(!lastPage){
            startPage = startPage + 50;
            boardResponse = getBoardPage(startPage);
            lastPage = boardResponse.getIsLast();
            boards.addAll(boardResponse.getValues());
        }

        Collections.sort(boards);
        return boards;
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
