package com.analyzer.dao;

import com.analyzer.domain.JiraBoard;
import com.analyzer.jira.JiraBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * Created by rcharow on 8/12/17.
 */
@Transactional
@Repository
public class JiraBoardDao {
    @PersistenceContext
    private EntityManager em;
    private JiraBoardService jiraBoardService;

    @Autowired
    public JiraBoardDao(JiraBoardService jiraBoardService) {
        this.jiraBoardService = jiraBoardService;
    }

    public List<JiraBoard> getAllBoards() {
        String query = "FROM JiraBoard as board";
        List<JiraBoard> boards = em.createQuery(query, JiraBoard.class).getResultList();
        Collections.sort(boards);
        return boards;
    }

    public List<JiraBoard> getBoardsByType(String boardType) {
        String query = "FROM JiraBoard as board WHERE board.type = :boardType";
        List<JiraBoard> boards = em.createQuery(query, JiraBoard.class)
                .setParameter("boardType", boardType)
                .getResultList();
        Collections.sort(boards);
        return boards;
    }

    public JiraBoard getBoard(String boardId) {
        return em.find(JiraBoard.class, boardId);
    }

    public void updateJiraBoards() {
        List<JiraBoard> boards = jiraBoardService.getAllBoards();
        for(JiraBoard board : boards) {
            JiraBoard existingBoard = em.find(JiraBoard.class, board.getId());
            if(existingBoard == null){
                em.persist(board);
            }
        }
    }
}
