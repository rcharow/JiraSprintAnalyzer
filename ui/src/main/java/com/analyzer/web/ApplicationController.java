package com.analyzer.web;

import com.analyzer.domain.JiraProject;
import com.analyzer.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rcharow on 9/21/16.
 */
@Component
@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    private JiraService jiraService;

    @RequestMapping("/projects")
    public List<JiraProject> getProjects() {
        return jiraService.getProjects();
    }
}
