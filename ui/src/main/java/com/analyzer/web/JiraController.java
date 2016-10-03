package com.analyzer.web;

import com.analyzer.domain.JiraProject;
import com.analyzer.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Path("/projects")
    public List<JiraProject> getProjects() {
        return jiraService.getProjects();
    }
}
