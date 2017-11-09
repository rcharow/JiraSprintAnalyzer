package com.analyzer.dao;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraIssueDetails;
import com.analyzer.domain.JiraIssueTypeFields;
import com.analyzer.domain.JiraSprint;
import com.analyzer.jira.JiraIssueService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;
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
  public void updateJiraSprintIssues(String sprintId) {

    JiraSprint sprint = em.find(JiraSprint.class,sprintId);

    //TODO: Is using the 'current board' correct? Seems like the origin board might not exist. Will this cause dupes?
    List<JiraIssue> sprintIssues = jiraIssueService.getCompletedSprintIssues(sprint.getCurrentBoardId(), sprint.getId());
    for (JiraIssue updatedIssue : sprintIssues) {
      updatedIssue.setBoardId(sprint.getCurrentBoardId());
      updatedIssue.setSprintId(sprint.getId());
      JiraIssue currentIssue = em.find(JiraIssue.class, updatedIssue.getId());
      if (currentIssue != null) {
        currentIssue.setDetails(updatedIssue.getDetails());
      } else {
        em.persist(updatedIssue);
      }
    }

    //Remove cached issues that don't exist in jira anymore
    List<JiraIssue> cachedIssues = getParentIssuesBySprint(sprint.getId());
    for (JiraIssue cached : cachedIssues) {
      Boolean exists = false;
      for (JiraIssue issue : sprintIssues) {
        if (cached.equals(issue)) {
          exists = true;
        }
      }
      if (!exists) {
        em.remove(cached);
      }
    }

    Date completeDate = sprint.getCompleteDate();
    //TODO: Is this right? what about the timezone?
    Date today = new Date();
    LocalDate localToday = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate localComplete = completeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if (ChronoUnit.WEEKS.between(localComplete, localToday) > 2) {
      sprint.setIssuesSynced(true);
    }
  }

}
