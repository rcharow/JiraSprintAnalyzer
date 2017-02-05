package com.analyzer.service.jira;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * Created by rcharow on 9/30/16.
 */
@Component
@PropertySource(value = "config.properties")
abstract class JiraService {
    private String jiraUser;

    private String jiraPassword;

    String jiraUrl;

    final HttpHeaders jiraAuthHeaders;

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
