package com.analyzer.dao;

import com.analyzer.domain.JiraBoard;
import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprintIssues;
import com.analyzer.jira.JiraIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by rcharow on 8/27/17.
 */
@Transactional
@Repository
public class JiraIssueDao {
  @PersistenceContext
  private EntityManager em;
  private JiraIssueService jiraIssueService;
  private JiraBoardDao boardDao;

  @Autowired
  public JiraIssueDao(JiraIssueService jiraIssueService, JiraBoardDao boardDao) {
    this.jiraIssueService = jiraIssueService;
    this.boardDao = boardDao;
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

  public void updateJiraIssues() {
    List<JiraBoard> boards = boardDao.getAllBoards();
    for (JiraBoard board : boards) {
      List<JiraSprintIssues> sprints = jiraIssueService.getBoardIssues(board.getId(), Boolean.FALSE);
      for (JiraSprintIssues sprintIssues : sprints) {
        for (JiraIssue issue : sprintIssues.getJiraIssues()) {
          issue.setBoardId(board.getId());
          issue.setSprintId(sprintIssues.getSprint().getId());
          issue.setIsSubtask(issue.getDetails().getIssueType().isSubtask());
          JiraIssue existingIssue = em.find(JiraIssue.class, issue.getId());
          if (existingIssue == null) {
            em.persist(issue);
          }
        }
      }
    }
  }

}
