package com.analyzer.web;

import com.analyzer.domain.JiraBoard;
import com.analyzer.domain.JiraBoardResponse;
import com.analyzer.domain.JiraProject;
import com.analyzer.service.JiraService;
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
    private JiraService jiraService;

    @GET
    @Path("/project")
    public List<JiraProject> getProjects() {
        return jiraService.getProjects();
    }

    @GET
    @Path("/board")
    public List<JiraBoard> getBoards() {
        return jiraService.getAllBoards();
    }

    @GET
    @Path("/board/{id}")
    public JiraBoard getBoard(@PathParam("id") String id) {
        return jiraService.getBoard(id);
    }
}
