package com.analyzer.dao;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprint;
import com.analyzer.jira.JiraIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Date;


/**
 * Created by rcharow on 8/27/17.
 */
@Transactional
@Repository
public class JiraIssueDao {
  @PersistenceContext
  private EntityManager em;
  private JiraIssueService jiraIssueService;
  private JiraSprintDao sprintDao;

  @Autowired
  public JiraIssueDao(JiraIssueService jiraIssueService, JiraSprintDao sprintDao) {
    this.jiraIssueService = jiraIssueService;
    this.sprintDao = sprintDao;
  }

  public List<JiraIssue> getParentIssuesBySprint(String sprintId) {
    String query = "FROM JiraIssue WHERE sprintId = :sprintId AND isSubtask = FALSE";
    return em.createQuery(query, JiraIssue.class)
        .setParameter("sprintId", sprintId)
        .getResultList();
  }

  public List<JiraIssue> getIssuesByBoard(String boardId) {
    String query = "FROM JiraIssue WHERE boardId = :boardId AND isSubtask = FALSE";
    return em.createQuery(query, JiraIssue.class)
        .setParameter("boardId", boardId)
        .getResultList();
  }

  @Transactional
  public void updateJiraIssues() {
    Date today = new Date();
    LocalDate localToday = new java.sql.Date(today.getTime()).toLocalDate();
    List<JiraSprint> sprints = sprintDao.getClosedSprintsWithUnsyncedIssues();
    for (JiraSprint sprint : sprints) {
      //TODO: Is using the 'current board' correct? Seems like the origin board might not exist. Will this cause dupes?
      List<JiraIssue> sprintIssues = jiraIssueService.getCompletedSprintIssues(sprint.getCurrentBoardId(), sprint.getId());
      for (JiraIssue issue : sprintIssues) {
        em.merge(issue);
      }

      //Remove cached issues that don't exist in jira anymore
      List<JiraIssue> cachedIssues = getParentIssuesBySprint(sprint.getId());
      for(JiraIssue cached : cachedIssues) {
        Boolean exists = false;
        for(JiraIssue issue : sprintIssues) {
          if(cached.equals(issue)) {
            exists = true;
          }
        }
        if(!exists) {
          em.remove(cached);
        }
      }

      Date completeDate = sprint.getCompleteDate();
      LocalDate localComplete = new java.sql.Date(completeDate.getTime()).toLocalDate();
      if (ChronoUnit.DAYS.between(localComplete, localToday) > 14) {
//        em.getTransaction().begin();
        sprint.setIssuesSynced(true);
//        em.getTransaction().commit();
      }
    }
  }

}
