package com.analyzer.service.jira;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rcharow on 6/2/17.
 */
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR,reason="Jira Error")
public class JiraException extends RuntimeException {
    public JiraException() {
        super("Jira Message");
    }
}