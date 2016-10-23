package com.analyzer.web;

import com.analyzer.domain.*;
import com.analyzer.service.JiraBoardService;
import com.analyzer.service.JiraIssueService;
import com.analyzer.service.JiraSprintService;
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

    @Autowired
    private JiraBoardService jiraBoardService;

    @Autowired
    private JiraSprintService jiraSprintService;

    @Autowired
    private JiraIssueService jiraIssueService;

    @GET
    @Path("/board")
    public List<JiraBoard> getBoards() {
        return jiraBoardService.getAllBoards();
    }

    @GET
    @Path("/board/{id}")
    public JiraBoard getBoard(@PathParam("id") String id) {
        return jiraBoardService.getBoard(id);
    }

    @GET
    @Path("/board/{boardId}/sprints/")
    public List<JiraSprint> getSprints(@PathParam("boardId") String boardId) {
        return jiraSprintService.getSprints(boardId);
    }

    @GET
    @Path("/board/{boardId}/sprints/{sprintState}")
    public List<JiraSprint> getSprints(@PathParam("boardId") String boardId, @PathParam("sprintState") String sprintState) {
        return jiraSprintService.getSprints(boardId, sprintState);
    }

    @GET
    @Path("/sprint/{sprintId}/issues")
    public List<JiraIssue> getIssues(@PathParam("sprintId") String sprintId) {
        return jiraIssueService.getSprintIssues(sprintId);
    }
}
