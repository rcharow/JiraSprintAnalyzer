package com.analyzer.dao;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprint;
import com.analyzer.domain.JiraWorklog;
import com.analyzer.jira.JiraIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
  private JiraSprintDao sprintDao;
  private JiraIssueDao issueDao;

  @Autowired
  public JiraWorklogDao(JiraIssueService jiraIssueService, JiraSprintDao jiraSprintDao, JiraIssueDao jiraIssueDao) {
    this.jiraIssueService = jiraIssueService;
    this.sprintDao = jiraSprintDao;
    this.issueDao = jiraIssueDao;
  }
  public JiraWorklog getWorklogById(String worklogId) {
    return em.createQuery("FROM JiraWorklog WHERE worklogId = :worklogId", JiraWorklog.class)
        .setParameter("worklogId", worklogId)
        .getSingleResult();
  }

  public List<JiraWorklog> getWorklogsByIssue(String issueId) {
    return em.createQuery("FROM JiraWorklog WHERE issueId = :issueId", JiraWorklog.class)
        .setParameter("issueId", issueId)
        .getResultList();
  }

  public List<JiraWorklog> getWorklogsBySprint(String sprintId) {
    return em.createQuery("FROM JiraWorklog WHERE sprintId = :sprintId", JiraWorklog.class)
        .setParameter("issueId", sprintId)
        .getResultList();
  }

  public List<JiraWorklog> getWorklogsByBoard(String boardId) {
    return em.createQuery("FROM JiraWorklog WHERE boardId = :boardId", JiraWorklog.class)
        .setParameter("issueId", boardId)
        .getResultList();
  }

  @Transactional
  public void updateJiraWorklogs() {
    Date today = new Date();
    LocalDate localToday = new java.sql.Date(today.getTime()).toLocalDate();
    List<JiraSprint> sprints = sprintDao.getClosedSprintsWithUnsyncedWorklogs();
    for (JiraSprint sprint : sprints) {
      List<JiraIssue> sprintIssues = issueDao.getIssuesByBoard(sprint.getOriginBoardId());
      for (JiraIssue issue : sprintIssues) {
        //Update worklogs and add new
        List<JiraWorklog> worklogs = jiraIssueService.getIssueWorklogs(issue.getId());
        for (JiraWorklog worklog : worklogs) {
          em.merge(worklog);
        }

        //Remove cached worklogs that don't exist in jira anymore
        List<JiraWorklog> cachedWorklogs = getWorklogsByIssue(issue.getId());
        for(JiraWorklog cached : cachedWorklogs) {
          Boolean exists = false;
          for(JiraWorklog worklog : worklogs) {
            if(cached.equals(worklog)) {
              exists = true;
            }
          }
          if(!exists) {
            em.remove(cached);
          }
        }
      }

      Date completeDate = sprint.getCompleteDate();
      LocalDate localComplete = new java.sql.Date(completeDate.getTime()).toLocalDate();
      if (ChronoUnit.DAYS.between(localComplete, localToday) > 14) {
//        em.getTransaction().begin();
        sprint.setWorklogsSynced(true);
//        em.getTransaction().commit();
      }
    }
  }
}
