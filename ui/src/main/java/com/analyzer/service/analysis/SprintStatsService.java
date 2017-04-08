package com.analyzer.service.analysis;

import com.analyzer.domain.JiraIssue;
import com.analyzer.domain.JiraSprintSummary;
import com.analyzer.domain.JiraWorklog;
import com.analyzer.domain.JiraWorklogSummaryItem;
import com.analyzer.service.jira.JiraIssueService;
import com.analyzer.service.jira.JiraSprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
        HashMap worklogMap = new HashMap();
        for (JiraIssue issue : issues) {
            Integer points = issue.getFields().getPoints();
            points = points != null ? points : 0;
            totalPoints = totalPoints + points;

            Integer seconds = issue.getFields().getTimeSpent();
            seconds = seconds != null ? seconds : 0;
            totalSeconds = totalSeconds + seconds;

            List<JiraWorklog> worklogs = jiraIssueService.getIssueWorklog(issue.getId());

            if(worklogs != null){
                for(JiraWorklog item : worklogs){
                    String author = item.getAuthor().getDisplayName();
                    double time = Optional.ofNullable(item.getTimeSpentSeconds()).orElse(0);
                    time = time/60/60;
                    if(worklogMap.containsKey(author)){
                        worklogMap.put(author, (double)worklogMap.get(author) + time);
                    } else {
                        worklogMap.put(author, time);
                    }
                }
            }
        }

        Iterator iterator = worklogMap.entrySet().iterator();
        List<JiraWorklogSummaryItem> worklogSummary = new ArrayList<JiraWorklogSummaryItem>();
        while(iterator.hasNext()){
            Map.Entry values = (Map.Entry)iterator.next();
            JiraWorklogSummaryItem item = new JiraWorklogSummaryItem();
            item.setAuthor((String)values.getKey());
            item.setTotalTimeHours((double)values.getValue());
            worklogSummary.add(item);
        }
        summary.setWorklogSummaryHours(worklogSummary);

        double hours = (double)totalSeconds / 60 / 60;
        summary.setTotalTimeHours(hours);
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
