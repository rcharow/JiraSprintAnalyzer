package com.analyzer.web;

import com.analyzer.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rcharow on 9/30/16.
 */
@RestController
public class JiraController {
    @Autowired
    private JiraService jiraService;

    @RequestMapping("/projects")
    public String getProjects() {
        return jiraService.getProjects();
    }

}
