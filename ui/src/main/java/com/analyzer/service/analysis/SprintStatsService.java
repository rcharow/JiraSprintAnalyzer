package com.analyzer.service.analysis;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprint;
import com.analyzer.domain.JiraSprintSummary;
import com.analyzer.service.jira.JiraIssueService;
import com.analyzer.service.jira.JiraSprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rcharow on 2/19/17.
 */
@Component
public class SprintStatsService {
    private final JiraSprintService jiraSprintService;
    private final JiraIssueService jiraIssueService;
    private final Integer costPerHour = 125;

    @Autowired
    public SprintStatsService(JiraSprintService jiraSprintService, JiraIssueService jiraIssueService){
        this.jiraSprintService = jiraSprintService;
        this.jiraIssueService = jiraIssueService;
    }

    public JiraSprintSummary getSprintSummary(String sprintId) {

        JiraSprintSummary summary = new JiraSprintSummary();
        summary.setId(sprintId);

        List<JiraIssue> issues = jiraIssueService.getSprintIssues(sprintId);
        summary = calculateStats(issues);

        return summary;
    }

    private JiraSprintSummary calculateStats(List<JiraIssue> issues) {
        JiraSprintSummary stats = new JiraSprintSummary();
        Integer totalPoints = 0;
        Integer totalSeconds = 0;

        stats.setTotalIssues(issues.size());

        for (JiraIssue issue : issues) {
            Integer points = issue.getFields().getPoints();
            points = points != null ? points : 0;
            totalPoints = totalPoints + points;

            Integer seconds = issue.getFields().getTimeSpent();
            seconds = seconds != null ? seconds : 0;
            totalSeconds = totalSeconds + seconds;
        }

        double cost = totalSeconds/60/60*costPerHour;
        stats.setTotalCost(cost);

        return stats;
    }
}
