package com.analyzer.jira;

import com.analyzer.dao.JiraBoardDao;
import com.analyzer.domain.JiraBoard;
import com.analyzer.domain.JiraBoardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        List<JiraBoard> boards;
        Boolean lastPage;
        Long startPage = 0L;

        try {

            JiraBoardResponse boardResponse = getBoardPage(startPage);
            lastPage = boardResponse.getIsLast();
            boards = boardResponse.getValues();

            while (!lastPage) {
                startPage = startPage + 50;
                boardResponse = getBoardPage(startPage);
                lastPage = boardResponse.getIsLast();
                boards.addAll(boardResponse.getValues());
            }

            return boards;
        } catch (Exception e) {
            throw new JiraException();
        }
    }

    private JiraBoardResponse getBoardPage(Long startPage) {
        try {
            String requestUrl = "/rest/agile/1.0/board?limit=50";

            HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
            RestTemplate restTemplate = new RestTemplate();

            if (startPage != 0L) {
                requestUrl = "/rest/agile/1.0/board?limit=50&startAt=" + startPage;
            }

            ResponseEntity<JiraBoardResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
                    HttpMethod.GET,
                    request,
                    JiraBoardResponse.class
            );


            return response.getBody();
        } catch (Exception e) {
            throw new JiraException();
        }
    }
}
