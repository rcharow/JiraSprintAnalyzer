package com.analyzer.dao;

import com.analyzer.domain.JiraBoard;
import com.analyzer.domain.JiraSprint;
import com.analyzer.jira.JiraSprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

/**
 * Created by rcharow on 8/27/17.
 */
@Transactional
@Repository
public class JiraSprintDao {
    @PersistenceContext
    private EntityManager em;
    private JiraSprintService jiraSprintService;
    private JiraBoardDao boardDao;

    @Autowired
    public JiraSprintDao(JiraSprintService jiraSprintService, JiraBoardDao boardDao) {
        this.jiraSprintService = jiraSprintService;
        this.boardDao = boardDao;
    }

    public List<JiraSprint> getAllSprints() {
        return em.createQuery("FROM JiraSprint as sprint", JiraSprint.class).getResultList();
    }

    public List<JiraSprint> getClosedSprintsWithUnsyncedIssues() {
        return em.createQuery("FROM JiraSprint as sprint WHERE issuesSynced = false AND state = 'closed'", JiraSprint.class).getResultList();
    }

    public List<JiraSprint> getClosedSprintsWithUnsyncedWorklogs() {
        return em.createQuery("FROM JiraSprint as sprint WHERE worklogsSynced = false AND state = 'closed'", JiraSprint.class).getResultList();
    }

    public List<JiraSprint> getSprintsByBoardId(String boardId) {
        String query = "FROM JiraSprint as sprint WHERE originBoardId = :boardId";
        List<JiraSprint> sprints = em.createQuery(query, JiraSprint.class)
                .setParameter("boardId", boardId)
                .getResultList();
        Collections.sort(sprints);
        return sprints;
    }

    public List<JiraSprint> getSprintsByBoardId(String boardId, String sprintState) {
        String query = "FROM JiraSprint as sprint WHERE sprint.originBoardId = :boardId AND sprint.state = :sprintState";
        List<JiraSprint> sprints = em.createQuery(query, JiraSprint.class)
                .setParameter("boardId", boardId)
                .setParameter("sprintState", sprintState)
                .getResultList();
        Collections.sort(sprints);
        return sprints;
    }

    public JiraSprint getSprint(String sprintId) {
        return em.find(JiraSprint.class, sprintId);
    }

    @Transactional
    public void updateSprints() {
        List<JiraBoard> boards = boardDao.getBoardsByType("scrum");
        for (JiraBoard board : boards) {
            List<JiraSprint> sprints = jiraSprintService.getSprints(board.getId());
            if (sprints.size() > 0) {
                for (JiraSprint sprint : sprints) {
                    JiraSprint existingSprint = em.find(JiraSprint.class, sprint.getId());
                    if (existingSprint == null) {
                        sprint.setCurrentBoardId(board.getId());
                        em.persist(sprint);
                    }
                }
            }
        }
    }
}
