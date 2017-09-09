package com.analyzer.dao;

import com.analyzer.domain.JiraWorklog;
import com.analyzer.jira.JiraIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcharow on 9/9/17.
 */
@Transactional
@Repository
public class JiraWorklogDao {
  @PersistenceContext
  private EntityManager em;
  private JiraIssueService jiraIssueService;
  private JiraSprintDao jiraSprintDao;

  @Autowired
  public JiraWorklogDao(JiraIssueService jiraIssueService, JiraSprintDao jiraSprintDao) {
    this.jiraIssueService = jiraIssueService;
    this.jiraSprintDao = jiraSprintDao;
  }

  public List<JiraWorklog> getWorklogsByIssue(String issueId) {
    return new ArrayList<>();
  }

  public List<JiraWorklog> getWorklogsBySprint(String sprintId) {
    return new ArrayList<>();
  }

  public List<JiraWorklog> getWorklogsByBoard(String boardId) {
    return new ArrayList<>();
  }
}
