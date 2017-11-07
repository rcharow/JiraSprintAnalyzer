package com.analyzer.dao;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprint;
import com.analyzer.domain.JiraWorklog;
import com.analyzer.jira.JiraIssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
  private static final Logger logger = LoggerFactory.getLogger(JiraWorklog.class);

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

  public void updateJiraWorklogs() {
    List<JiraSprint> sprints = sprintDao.getClosedSprintsWithUnsyncedWorklogs();
    for (JiraSprint sprint : sprints) {
      updateSprintWorklogs(sprint);
    }
  }

  @Transactional
  private void updateSprintWorklogs(JiraSprint sprint) {
    Date today = new Date();
    LocalDate localToday = new java.sql.Date(today.getTime()).toLocalDate();
    List<JiraIssue> sprintIssues = issueDao.getParentIssuesBySprint(sprint.getId());
    for (JiraIssue issue : sprintIssues) {
      //Update worklogs and add new
      List<JiraWorklog> worklogs = jiraIssueService.getIssueWorklogs(issue.getId());
      for (JiraWorklog updatedWorklog : worklogs) {
        String comment = updatedWorklog.getComment();
        if(comment.length() > 255) {
          updatedWorklog.setComment(comment.substring(0, Math.min(comment.length(), 255)));
        }
        updatedWorklog.setBoardId(sprint.getOriginBoardId());
        updatedWorklog.setSprintId(sprint.getId());
        updatedWorklog.setUpdateTime(new Date());
        JiraWorklog currentWorklog = em.find(JiraWorklog.class, updatedWorklog.getId());
        if(currentWorklog != null) {
          currentWorklog.setComment(updatedWorklog.getComment());
          currentWorklog.setTimeSpentSeconds(updatedWorklog.getTimeSpentSeconds());
          currentWorklog.setAuthor(updatedWorklog.getAuthor());
        } else {
          em.persist(updatedWorklog);
        }
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
    LocalDate localComplete = completeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if (ChronoUnit.WEEKS.between(localComplete, localToday) > 2) {
      sprint.setWorklogsSynced(true);
    }
    logger.info("--------------- Updated jira worklogs for sprint {}!", sprint.getId());
  }
}
