package com.analyzer.web;

import com.analyzer.dao.JiraBoardDao;
import com.analyzer.dao.JiraSprintDao;
import com.analyzer.domain.*;
import com.analyzer.jira.JiraIssueService;
import com.analyzer.jira.JiraRapidViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by rcharow on 9/21/16.
 */
@Component
@Path("/jira")
@Produces(MediaType.APPLICATION_JSON)
public class JiraController {

    private final JiraBoardDao jiraBoardDao;

    private final JiraSprintDao jiraSprintDao;

    private final JiraIssueService jiraIssueService;

    private final JiraRapidViewService jiraRapidViewService;

    @Autowired
    public JiraController(JiraBoardDao jiraBoardDao, JiraSprintDao jiraSprintDao, JiraIssueService jiraIssueService, JiraRapidViewService jiraRapidViewService) {
        this.jiraBoardDao = jiraBoardDao;
        this.jiraSprintDao = jiraSprintDao;
        this.jiraIssueService = jiraIssueService;
        this.jiraRapidViewService = jiraRapidViewService;
    }

    @GET
    @Path("/board")
    public List<JiraBoard> getBoards() {
        return jiraBoardDao.getAllBoards();
    }

    @GET
    @Path("/board/type/{type}")
    public List<JiraBoard> getBoardsByType(@PathParam("type") String boardType) { return jiraBoardDao.getBoardsByType(boardType); }

    @GET
    @Path("/board/{id}")
    public JiraBoard getBoard(@PathParam("id") String id) {
        return jiraBoardDao.getBoard(id);
    }

    @GET
    @Path("/board/{boardId}/sprints/")
    public List<JiraSprint> getSprints(@PathParam("boardId") String boardId) {
        return jiraSprintDao.getSprints(boardId);
    }

    @GET
    @Path("/board/{boardId}/sprints/{sprintState}")
    public List<JiraSprint> getSprints(@PathParam("boardId") String boardId, @PathParam("sprintState") String sprintState) {
        return jiraSprintDao.getSprints(boardId, sprintState);
    }

    @GET
    @Path("/board/{boardId}/sprint/{sprintId}/issues")
    public List<JiraIssue> getSprintIssues(@PathParam("boardId") String boardId, @PathParam("sprintId") String sprintId) {
        return jiraIssueService.getCompletedSprintIssues(boardId, sprintId);
    }

    @GET
    @Path("board/{boardId}/sprint/{sprintId}/issues/parent")
    public List<JiraIssue> getSprintParentIssues(@PathParam("boardId") String boardId, @PathParam("sprintId") String sprintId) {
        return jiraIssueService.getCompletedSprintParentIssues(boardId, sprintId);
    }

    @GET
    @Path("/board/{boardId}/issues")
    public List<JiraSprintIssues> getBoardIssues(@PathParam("boardId") String boardId) {
        return jiraIssueService.getBoardIssues(boardId, Boolean.FALSE);
    }

    @GET
    @Path("/board/{boardId}/issues/parent")
    public List<JiraSprintIssues> getBoardParentIssues(@PathParam("boardId") String boardId) {
        return jiraIssueService.getBoardParentIssues(boardId);
    }

    @GET
    @Path("/rapid/{boardId}/{sprintId}")
    public JiraSprintRapidView getSprintRapidView(@PathParam("boardId") String boardId, @PathParam("sprintId") String sprintId) {
        return jiraRapidViewService.getSprintRapidView(boardId,sprintId);
    }
}
