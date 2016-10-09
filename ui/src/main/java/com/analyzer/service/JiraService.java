package com.analyzer.service;

import com.analyzer.domain.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by rcharow on 9/30/16.
 */
@Component
@PropertySource(value = "config.properties")
public abstract class JiraService {
    private String jiraUser;

    private String jiraPassword;

    protected String jiraUrl;

    protected HttpHeaders jiraAuthHeaders;

    public void setJiraUser(String jiraUser) {
        this.jiraUser = jiraUser;
    }

    public void setJiraPassword(String jiraPassword) {
        this.jiraPassword = jiraPassword;
    }

    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }

    @Autowired
    public JiraService(@Value("${jira.username}") String jiraUser,
                       @Value("${jira.password}") String jiraPassword,
                       @Value("${jira.self}") String jiraUrl) {

        this.jiraUser = jiraUser;
        this.jiraPassword = jiraPassword;
        this.jiraUrl = jiraUrl;

        String jiraCredentials = jiraUser + ":" + jiraPassword;
        byte[] jiraCredentialsByte = Base64.encodeBase64(jiraCredentials.getBytes());
        String encodedCreds = new String(jiraCredentialsByte);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedCreds);

        this.jiraAuthHeaders = headers;
    }
}
