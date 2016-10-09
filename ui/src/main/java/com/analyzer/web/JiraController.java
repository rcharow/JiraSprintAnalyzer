package com.analyzer.web;

import com.analyzer.domain.JiraBoard;
import com.analyzer.domain.JiraBoardResponse;
import com.analyzer.domain.JiraProject;
import com.analyzer.domain.JiraSprint;
import com.analyzer.service.JiraBoardService;
import com.analyzer.service.JiraService;
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
    @Path("/board/{boardId}/sprints/{sprintStates}")
    public List<JiraSprint> getSprints(@PathParam("boardId") String boardId, @PathParam("sprintStates") String sprintStates) {
        return jiraSprintService.getSprints(boardId,sprintStates);
    }
}
