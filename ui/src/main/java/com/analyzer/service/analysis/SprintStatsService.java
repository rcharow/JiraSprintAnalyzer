package com.analyzer.service.analysis;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprint;
import com.analyzer.domain.JiraSprintStats;
import com.analyzer.service.jira.JiraBoardService;
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

    public List<JiraSprintStats> getSprintStats(String boardId, Date startSprintCompleteDate, Date endSprintCompleteDate) {
        List<JiraSprint> sprints = jiraSprintService.getSprints(boardId);
        List<JiraSprintStats> statsList = new ArrayList<JiraSprintStats>();

        for (JiraSprint sprint : sprints) {
            Date sprintDate = sprint.getCompleteDate();
            if((sprintDate.equals(startSprintCompleteDate) || sprintDate.after(startSprintCompleteDate) ) && (sprintDate.equals(endSprintCompleteDate) || sprintDate.before(endSprintCompleteDate))){

                String sprintId = sprint.getId();
                JiraSprintStats stats = new JiraSprintStats();
                stats.setId(sprintId);

                List<JiraIssue> issues = jiraIssueService.getSprintIssues(sprintId);
                stats = calculateStats(issues);

                statsList.add(stats);
            }
        }

        return statsList;
    }

    private JiraSprintStats calculateStats(List<JiraIssue> issues) {
        JiraSprintStats stats = new JiraSprintStats();
        Integer totalPoints = 0;
        Integer totalSeconds = 0;

        stats.setTotalIssues(issues.size());

        for (JiraIssue issue : issues) {
            totalPoints = totalPoints + issue.getFields().getPoints();
            totalSeconds = totalSeconds + issue.getFields().getTimeSpent();
        }

        double cost = totalSeconds/60/60*costPerHour;
        stats.setTotalCost(cost);

        return stats;
    }
}
