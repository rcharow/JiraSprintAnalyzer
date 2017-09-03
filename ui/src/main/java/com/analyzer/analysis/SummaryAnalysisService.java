package com.analyzer.analysis;

import com.analyzer.dao.JiraIssueDao;
import com.analyzer.dao.JiraSprintDao;
import com.analyzer.domain.*;
import com.analyzer.jira.JiraIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by rcharow on 2/19/17.
 */
@Component
public class SummaryAnalysisService {
    private final JiraSprintDao jiraSprintDao;
    private final JiraIssueDao jiraIssueDao;
    private final JiraIssueService jiraIssueService;
    private Integer clientCostPerHour;
    private Integer internalCostPerHour;

    @Autowired
    public SummaryAnalysisService(
        JiraSprintDao jiraSprintDao,
        JiraIssueDao jiraIssueDao,
        JiraIssueService jiraIssueService,
        @Value("${domain.hourlyCost.client}") String clientCost,
        @Value("${domain.hourlyCost.internal}") String internalCost){
        this.jiraSprintDao = jiraSprintDao;
        this.jiraIssueDao = jiraIssueDao;
        this.jiraIssueService = jiraIssueService;
        this.clientCostPerHour = Integer.parseInt(clientCost);
        this.internalCostPerHour = Integer.parseInt(internalCost);
    }

    public JiraSprintSummary getSprintSummary(String sprintId, boolean includeWorklogs) {

        JiraSprintSummary summary;
        JiraSprint sprint;

        sprint = jiraSprintDao.getSprint(sprintId);

        List<JiraIssue> issues = jiraIssueDao.getParentIssuesBySprint(sprintId);
        summary = calculateStats(issues);
        summary.setId(sprintId);
        summary.setName(sprint.getName());
        summary.setCompleteDate(sprint.getCompleteDate());

        if(includeWorklogs) {
            summary.setWorklogSummary(getSprintWorklogSummary(sprintId, issues));
        }

        return summary;
    }


    public JiraWorklogSummary getSprintWorklogSummary(String sprintId, List<JiraIssue> issues) {
        Integer totalSeconds = 0;

        HashMap worklogMap = new HashMap<String,Double>();
        for (JiraIssue issue : issues) {

            List<JiraWorklog> worklogs = jiraIssueService.getIssueWorklog(issue.getId());

            if(worklogs != null){
                for(JiraWorklog item : worklogs){
                    String author = item.getAuthor().getDisplayName() == null ? item.getAuthor().getName() : item.getAuthor().getDisplayName();
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
        JiraWorklogSummary worklogSummary = new JiraWorklogSummary();
        worklogSummary.setSprintId(sprintId);

        List<JiraWorklogSummaryItem> worklogs = new ArrayList<JiraWorklogSummaryItem>();

        while(iterator.hasNext()){
            Map.Entry values = (Map.Entry)iterator.next();
            JiraWorklogSummaryItem item = new JiraWorklogSummaryItem();
            item.setAuthor((String)values.getKey());
            item.setTotalTimeHours((double)values.getValue());
            worklogs.add(item);
        }

        worklogSummary.setWorklogs(worklogs);

        return worklogSummary;
    }


    private JiraSprintSummary calculateStats(List<JiraIssue> issues) {
        JiraSprintSummary summary = new JiraSprintSummary();
        double totalPoints = 0;
        Integer totalSeconds = 0;

        summary.setTotalIssues(issues.size());

        for (JiraIssue issue : issues) {
            Double points = issue.getDetails().getPoints();
            points = points != null ? points : 0;
            totalPoints = totalPoints + points;

            Integer seconds = issue.getDetails().getTimeSpent();
            seconds = seconds != null ? seconds : 0;
            totalSeconds = totalSeconds + seconds;
        }

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
