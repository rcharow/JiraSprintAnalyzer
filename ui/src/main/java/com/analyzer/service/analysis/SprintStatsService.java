package com.analyzer.service.analysis;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprintSummary;
import com.analyzer.service.jira.JiraIssueService;
import com.analyzer.service.jira.JiraSprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by rcharow on 2/19/17.
 */
@Component
public class SprintStatsService {
    private final JiraSprintService jiraSprintService;
    private final JiraIssueService jiraIssueService;
    private final Integer clientCostPerHour = 125;
    private final Integer internalCostPerHour = 65;

    @Autowired
    public SprintStatsService(JiraSprintService jiraSprintService, JiraIssueService jiraIssueService){
        this.jiraSprintService = jiraSprintService;
        this.jiraIssueService = jiraIssueService;
    }

    public JiraSprintSummary getSprintSummary(String sprintId) {

        JiraSprintSummary summary = new JiraSprintSummary();

        List<JiraIssue> issues = jiraIssueService.getSprintIssues(sprintId);
        summary = calculateStats(issues);
        summary.setId(sprintId);

        return summary;
    }

    private JiraSprintSummary calculateStats(List<JiraIssue> issues) {
        JiraSprintSummary summary = new JiraSprintSummary();
        Integer totalPoints = 0;
        Integer totalSeconds = 0;

        summary.setTotalIssues(issues.size());

        for (JiraIssue issue : issues) {
            Integer points = issue.getFields().getPoints();
            points = points != null ? points : 0;
            totalPoints = totalPoints + points;

            Integer seconds = issue.getFields().getTimeSpent();
            seconds = seconds != null ? seconds : 0;
            totalSeconds = totalSeconds + seconds;
        }
        summary.setTotalTimeSeconds(totalSeconds);
        summary.setTotalPoints(totalPoints);

        double clientCost = totalSeconds/60/60* clientCostPerHour;
        summary.setClientTotalCost(clientCost);

        double internalCost = totalSeconds/60/60* internalCostPerHour;
        summary.setInternalTotalCost(internalCost);

        double margin = (clientCost - internalCost) / clientCost * 100;
        summary.setMargin(margin);

        return summary;
    }
}
