package com.analyzer.service.jira;

import com.analyzer.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rcharow on 10/9/16.
 */
@Component
public class JiraIssueService extends JiraService {
    private Logger log = LoggerFactory.getLogger(JiraIssueService.class);
    private JiraSprintService jiraSprintService;
    private JiraRapidViewService jiraRapidViewService;

    private enum IssueSourceType {
        BOARD,
        SPRINT
    }

    @Autowired
    public JiraIssueService(@Value("${jira.username}") String jiraUser, @Value("${jira.password}") String jiraPassword, @Value("${jira.self}") String jiraUrl, JiraSprintService jiraSprintService, JiraRapidViewService jiraRapidViewService) {
        super(jiraUser, jiraPassword, jiraUrl);
        this.jiraSprintService = jiraSprintService;
        this.jiraRapidViewService = jiraRapidViewService;
    }

    public List<JiraIssue> getCompletedSprintIssues(String boardId, String sprintId) {
        List<JiraIssue> issues = getIssues(IssueSourceType.SPRINT, sprintId);
        return filterIssuesCompletedInSprint(boardId, sprintId, issues);
    }

    public List<JiraIssue> getCompletedSprintParentIssues(String boardId, String sprintId) {
        List<JiraIssue> issues = this.getCompletedSprintIssues(boardId, sprintId);

        return getParentIssues(issues);
    }

    public List<JiraSprintIssues> getBoardIssues(String boardId, Boolean parentIssuesOnly) {
        List<JiraSprintIssues> sprintIssues = new ArrayList<JiraSprintIssues>();
        List<JiraSprint> closedSprints = jiraSprintService.getSprints(boardId, "closed");
        for(JiraSprint sprint : closedSprints) {
            String sprintId = sprint.getId();
            List<JiraIssue> issues;
            if(parentIssuesOnly) {
                issues = getCompletedSprintParentIssues(boardId, sprintId);
            } else {
                issues = getCompletedSprintIssues(boardId, sprintId);
            }
            issues = filterIssuesCompletedInSprint(boardId, sprintId, issues);
            JiraSprintIssues singleSprintIssues = new JiraSprintIssues();
            singleSprintIssues.setSprint(sprint);
            singleSprintIssues.setParentIssues(issues);
            sprintIssues.add(singleSprintIssues);
        }

        return sprintIssues;

    }


    public List<JiraSprintIssues> getBoardParentIssues(String boardId) {
        List<JiraSprintIssues> issues = this.getBoardIssues(boardId, Boolean.TRUE);
        return issues;
    }

    public List<JiraWorklog> getIssueWorklog(String issueId) {
        String requestUrl = "/rest/api/2/issue/" + issueId + "/worklog";

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JiraWorklogResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
                HttpMethod.GET,
                request,
                JiraWorklogResponse.class
        );

        return response.getBody().getWorklogs();
    }

    private List<JiraIssue> getIssues(IssueSourceType sourceType, String sourceId) {
        List<JiraIssue> issues;
        Boolean lastPage;
        Long startPage = 0L;
        JiraIssueResponse issueResponse;

        if (sourceType.equals(IssueSourceType.BOARD)) {
            issueResponse = getBoardIssuePage(sourceId, startPage);
        } else if (sourceType.equals(IssueSourceType.SPRINT)) {
            issueResponse = getSprintIssuePage(sourceId, startPage);
        } else {
            throw new RuntimeException("IssueSourceType does not match any known source types.");
        }

        issues = issueResponse.getIssues();
        lastPage = issues.size() == 0;

        while (!lastPage) {
            startPage = startPage + 1000;
            if (sourceType.equals(IssueSourceType.BOARD)) {
                issueResponse = getBoardIssuePage(sourceId, startPage);
            } else if (sourceType.equals(IssueSourceType.SPRINT)) {
                issueResponse = getSprintIssuePage(sourceId, startPage);
            }
            lastPage = issueResponse.getIssues().size() == 0;
            issues.addAll(issueResponse.getIssues());
        }

        return issues;
    }

    private JiraIssueResponse getSprintIssuePage(String sprintId, Long startPage) {
        String requestUrl = "/rest/agile/1.0/sprint/" + sprintId + "/issue?maxResults=1000&startAt=0";
        //https://jira.portlandwebworks.com:8443/rest/agile/1.0/sprint/697/issue?limit=50&startAt=100

        if (startPage != 0L) {
            requestUrl = "/rest/agile/1.0/sprint/" + sprintId + "/issue?maxResults=1000&startAt=" + startPage;
        }

        HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JiraIssueResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
                HttpMethod.GET,
                request,
                JiraIssueResponse.class
        );

        return response.getBody();

//        ResponseEntity<String> response = restTemplate.exchange(jiraUrl + requestUrl,
//                HttpMethod.GET,
//                request,
//                String.class
//        );
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        try {
//
//            JsonNode node = mapper.readTree(new StringReader(response.getBody()));
//            log.warn("-----=== {}",mapper.writeValueAsString(node.withArray("issues").get(0)));
//        } catch(IOException ex) {
//            throw new RuntimeException(ex);
//        }
//        return null;
    }

//    public List<JiraIssue> getBoardIssues(String boardId) {
//
//    }

    private JiraIssueResponse getBoardIssuePage(String boardId, Long startPage) {
        String requestUrl = "/rest/agile/1.0/board/" + boardId + "/issue?maxResults=1000&startAt=0";

        if (startPage != 0L) {
            requestUrl = "/rest/agile/1.0/board/" + boardId + "/issue?maxResults=1000&startAt=" + startPage;
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

    private List<JiraIssue> getParentIssues(List<JiraIssue> issues) {
        return issues.stream()
                .filter(p -> p.getFields().getIssueType().isSubtask() == false)
                .collect(Collectors.toList());
    }

    private List<JiraIssue> filterIssuesCompletedInSprint(String boardId, String sprintId, List<JiraIssue> issues) {
        JiraSprintRapidView rapidView = jiraRapidViewService.getSprintRapidView(boardId,sprintId);
        List<String> completedIssueIds = rapidView.getContents().getCompletedIssues().stream().map(i -> i.getId()).collect(Collectors.toList());
        List<JiraIssue> completedIssues = issues.stream().filter(i -> completedIssueIds.contains(i.getId())).collect(Collectors.toList());
        return completedIssues;
    }


}
