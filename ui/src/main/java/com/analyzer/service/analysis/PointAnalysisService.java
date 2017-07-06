package com.analyzer.service.analysis;

import com.analyzer.domain.*;
import com.analyzer.service.jira.JiraIssueService;
import com.analyzer.service.jira.JiraSprintService;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rcharow on 6/16/17.
 */
@Component
public class PointAnalysisService {
    private JiraIssueService jiraIssueService;
    private JiraSprintService jiraSprintService;
    private final double[] pointValues = new double[]{0, 0.5, 1, 2, 3, 5, 8, 13, 21, 42, 101};

    @Autowired
    public PointAnalysisService(JiraIssueService jiraIssueService, JiraSprintService jiraSprintService) {
        this.jiraIssueService = jiraIssueService;
        this.jiraSprintService = jiraSprintService;
    }

    public List<JiraSprintPointAnalysis> getBoardPointAnalysis(String boardId) {
        List<JiraSprintIssues> sprintIssues = jiraIssueService.getBoardParentIssues(boardId);

        List<JiraSprintPointAnalysis> pointAnalyses = getPointAnalysis(sprintIssues);


        return pointAnalyses;
    }

    public JiraSprintPointAnalysis getSprintPointAnalysis(String sprintId) {
        List<JiraIssue> issues = jiraIssueService.getSprintParentIssues(sprintId);
        JiraSprint fullSprint = jiraSprintService.getSprintById(sprintId);

        JiraSprintIssues sprintIssues = new JiraSprintIssues();
        sprintIssues.setParentIssues(issues);
        sprintIssues.setSprint(fullSprint);

        List<JiraSprintIssues> sprintIssueList = new ArrayList<JiraSprintIssues>();
        sprintIssueList.add(sprintIssues);

        //TODO: Fix this hack
        return getPointAnalysis(sprintIssueList).get(0);
    }

    private List<JiraSprintPointAnalysis> getPointAnalysis(List<JiraSprintIssues> sprintIssues) {

        List<JiraSprintPointAnalysis> pointAnalyses = new ArrayList<JiraSprintPointAnalysis>();


        for (JiraSprintIssues sprint : sprintIssues) {
            JiraSprintPointAnalysis analysis = new JiraSprintPointAnalysis();
            analysis.setSprintId(sprint.getSprint().getId());
            analysis.setSprintName(sprint.getSprint().getName());
            analysis.setStartDate(sprint.getSprint().getStartDate());
            analysis.setEndDate(sprint.getSprint().getEndDate());
            analysis.setPointAnalysis(new ArrayList<JiraPointAnalysis>());

            for (Double pointValue : pointValues) {
                Integer count = 0;
                float totalTime = 0F;
                float totalDollars = 0F;

                for (JiraIssue issue : sprint.getParentIssues()) {
                    if (issue.getFields().getPoints() != null && issue.getFields().getPoints().equals(pointValue)) {
                        count += 1;
                        if (issue.getFields().getTimeSpent() != null) {
                            float hours = issue.getFields().getTimeSpent() / 60 / 60;
                            totalTime = totalTime + (float) issue.getFields().getTimeSpent();
                            totalDollars = totalDollars + (float) (hours * 60);
                        }
                    }
                }

                if (count > 0) {
                    JiraPointAnalysis pointAnalysis = new JiraPointAnalysis();
                    pointAnalysis.setPointValue(pointValue);
                    pointAnalysis.setAvgTimeSpentSeconds(totalTime / count);
                    pointAnalysis.setAvgDollarsSpent(totalDollars / count);
                    analysis.getPointAnalysis().add(pointAnalysis);
                }

            }

            analysis.setPointAverages(getPointAverages(sprint));
            pointAnalyses.add(analysis);

        }

        return pointAnalyses;
    }

    private JiraSprintPointAverage getPointAverages(JiraSprintIssues sprintIssues) {

        Double totalTime = 0D;
        Double totalPoints = 0D;

        for (JiraIssue issue : sprintIssues.getParentIssues()) {
            if(issue.getFields().getTimeSpent() != null) {
                totalTime += issue.getFields().getTimeSpent();
            }
            if(issue.getFields().getPoints() != null) {
                totalPoints += issue.getFields().getPoints();
            }
        }

        JiraSprintPointAverage average = new JiraSprintPointAverage();
        average.setSprintId(sprintIssues.getSprint().getId());
        average.setSprintName(sprintIssues.getSprint().getName());
        Double totalHours = totalTime / 60 / 60;

        if (sprintIssues.getParentIssues().size() > 0) {
            average.setAverageHoursPerPoint(totalHours / totalPoints);
            average.setAverageDollarsPerPoint(totalHours / totalPoints * 60);
        } else {
            average.setAverageHoursPerPoint(0D);
            average.setAverageDollarsPerPoint(0D);
        }


        return average;
    }


}
