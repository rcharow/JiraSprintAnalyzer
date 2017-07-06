package com.analyzer.service.jira;

import com.analyzer.domain.JiraSprintRapidView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rcharow on 7/6/17.
 */
@Component
public class JiraRapidViewService extends JiraService {

    @Autowired
    public JiraRapidViewService(@Value("${jira.username}") String jiraUser, @Value("${jira.password}") String jiraPassword, @Value("${jira.self}") String jiraUrl) {
        super(jiraUser, jiraPassword, jiraUrl);
    }

    public JiraSprintRapidView getSprintRapidView(String boardId, String sprintId) {
        String requestUrl = "/rest/greenhopper/1.0/rapid/charts/sprintreport?rapidViewId=" + boardId + "&sprintId=" + sprintId;

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JiraSprintRapidView> response = restTemplate.exchange(jiraUrl + requestUrl,
                HttpMethod.GET,
                request,
                JiraSprintRapidView.class
        );

        return response.getBody();
    }
}
