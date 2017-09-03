package com.analyzer.jira;

import com.analyzer.dao.JiraSprintDao;
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
  private JiraSprintDao jiraSprintDao;
  private JiraRapidViewService jiraRapidViewService;

  public enum IssueSourceType {
    BOARD,
    SPRINT
  }

  @Autowired
  public JiraIssueService(@Value("${jira.username}") String jiraUser, @Value("${jira.password}") String jiraPassword, @Value("${jira.self}") String jiraUrl, JiraSprintDao jiraSprintDao, JiraRapidViewService jiraRapidViewService) {
    super(jiraUser, jiraPassword, jiraUrl);
    this.jiraSprintDao = jiraSprintDao;
    this.jiraRapidViewService = jiraRapidViewService;
  }

//  public List<JiraIssue> getCompletedSprintIssues(String boardId, String sprintId) {
//    List<JiraIssue> issues = getIssues(IssueSourceType.SPRINT, sprintId);
//    return filterIssuesCompletedInSprint(boardId, sprintId, issues);
//  }
//
//  public List<JiraIssue> getCompletedSprintParentIssues(String boardId, String sprintId) {
//    List<JiraIssue> issues = this.getCompletedSprintIssues(boardId, sprintId);
//
//    return getParentIssues(issues);
//  }

  public List<JiraSprintIssues> getBoardIssues(String boardId, Boolean parentIssuesOnly) {
    long start = System.nanoTime();
    List<JiraIssue> issues = getIssues(IssueSourceType.BOARD, boardId);
    if (parentIssuesOnly) {
      issues = getParentIssues(issues);
    }
    List<JiraSprint> sprints = jiraSprintDao.getSprints(boardId, "closed");
    List<JiraSprintIssues> sprintIssues = new ArrayList<>();
    for (JiraSprint sprint : sprints) {
      JiraSprintIssues singleSprintIssues = new JiraSprintIssues();
      singleSprintIssues.setSprint(sprint);
      singleSprintIssues.setJiraIssues(filterIssuesCompletedInSprint(boardId, sprint.getId(), issues));
      sprintIssues.add(singleSprintIssues);
    }

    log.warn("-------------GET BOARD ISSUES BY BOARD -  Board ID: {} - Time: {}", boardId, (double) (System.nanoTime() - start) / 1000000000.0);
    return sprintIssues;
  }

//  public List<JiraSprintIssues> getBoardParentIssues(String boardId) {
//    List<JiraSprintIssues> issues = this.getBoardIssues(boardId, Boolean.TRUE);
//    return issues;
//  }

  public List<JiraWorklog> getIssueWorklog(String issueId) {
    String requestUrl = "/rest/api/2/issue/" + issueId + "/worklog";

    HttpEntity<String> request = new HttpEntity<String>(this.jiraAuthHeaders);
    RestTemplate restTemplate = new RestTemplate();

    try {

      ResponseEntity<JiraWorklogResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
          HttpMethod.GET,
          request,
          JiraWorklogResponse.class
      );

      return response.getBody().getWorklogs();
    } catch (Exception e) {
      throw new JiraException();
    }
  }

  private List<JiraIssue> getIssues(IssueSourceType sourceType, String sourceId) {
    List<JiraIssue> issues;
    Boolean lastPage;
    Long startPage = 0L;
    long start = System.nanoTime();
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

    log.warn("-------------GET ISSUES -  Source type: {} sourceId: {} Time: {}", sourceType, sourceId, (double) (System.nanoTime() - start) / 1000000000.0);
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

    try {
      ResponseEntity<JiraIssueResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
          HttpMethod.GET,
          request,
          JiraIssueResponse.class
      );

      return response.getBody();
    } catch (Exception e) {
      throw new JiraException();
    }
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

    try {
      ResponseEntity<JiraIssueResponse> response = restTemplate.exchange(jiraUrl + requestUrl,
          HttpMethod.GET,
          request,
          JiraIssueResponse.class
      );

      return response.getBody();
    } catch (Exception e) {
      throw new JiraException();
    }
  }

  private List<JiraIssue> getParentIssues(List<JiraIssue> issues) {
    return issues.stream()
        .filter(p -> p.getDetails().getIssueType().isSubtask() == false)
        .collect(Collectors.toList());
  }

  private List<JiraIssue> filterIssuesCompletedInSprint(String boardId, String sprintId, List<JiraIssue> issues) {
    JiraSprintRapidView rapidView = jiraRapidViewService.getSprintRapidView(boardId, sprintId);
    List<String> completedIssueIds = rapidView.getContents().getCompletedIssues().stream().map(i -> i.getId()).collect(Collectors.toList());
    List<JiraIssue> completedIssues = issues.stream().filter(i -> completedIssueIds.contains(i.getId())).collect(Collectors.toList());
    return completedIssues;
  }


}
