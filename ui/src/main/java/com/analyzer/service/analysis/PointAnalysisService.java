package com.analyzer.service.analysis;

import com.analyzer.domain.*;
import com.analyzer.service.jira.JiraIssueService;
import com.analyzer.service.jira.JiraRapidViewService;
import com.analyzer.service.jira.JiraSprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcharow on 6/16/17.
 */
@Component
public class PointAnalysisService {
    private JiraIssueService jiraIssueService;
    private JiraSprintService jiraSprintService;
    private JiraRapidViewService jiraRapidViewService;
    private final double[] pointValues = new double[]{0, 0.5, 1, 2, 3, 5, 8, 13, 21, 42, 101};

    @Autowired
    public PointAnalysisService(JiraIssueService jiraIssueService, JiraSprintService jiraSprintService, JiraRapidViewService jiraRapidViewService) {
        this.jiraIssueService = jiraIssueService;
        this.jiraSprintService = jiraSprintService;
        this.jiraRapidViewService = jiraRapidViewService;
    }

    public List<JiraSprintPointAnalysis> getBoardPointAnalysis(String boardId) {
        List<JiraSprintIssues> sprintIssues = jiraIssueService.getBoardParentIssues(boardId);

        List<JiraSprintPointAnalysis> pointAnalyses = getPointAnalysis(sprintIssues, boardId);


        return pointAnalyses;
    }

    public JiraSprintPointAnalysis getSprintPointAnalysis(String boardId, String sprintId) {
        List<JiraIssue> issues = jiraIssueService.getCompletedSprintParentIssues(boardId, sprintId);
        JiraSprint fullSprint = jiraSprintService.getSprintById(sprintId);

        JiraSprintIssues sprintIssues = new JiraSprintIssues();
        sprintIssues.setParentIssues(issues);
        sprintIssues.setSprint(fullSprint);

        List<JiraSprintIssues> sprintIssueList = new ArrayList<JiraSprintIssues>();
        sprintIssueList.add(sprintIssues);

        //TODO: Fix this hack
        return getPointAnalysis(sprintIssueList, boardId).get(0);
    }

    private List<JiraSprintPointAnalysis> getPointAnalysis(List<JiraSprintIssues> sprintIssues, String boardId) {

        List<JiraSprintPointAnalysis> pointAnalyses = new ArrayList<JiraSprintPointAnalysis>();

        for (JiraSprintIssues sprint : sprintIssues) {
            JiraSprintPointAnalysis analysis = new JiraSprintPointAnalysis();
            JiraSprintRapidView rapidView = jiraRapidViewService.getSprintRapidView(boardId, sprint.getSprint().getId());

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
        average.setTotalHours(totalHours);
        average.setTotalCompletedPoints(totalPoints);


        if (sprintIssues.getParentIssues().size() > 0) {
            average.setTotalCompletedIssues(sprintIssues.getParentIssues().size());
            average.setAverageHoursPerPoint(totalHours / totalPoints);
            average.setAverageDollarsPerPoint(totalPoints > 0.0 ? average.getAverageHoursPerPoint() * 60 : totalHours * 60);
        } else {
            average.setTotalCompletedIssues(0);
            average.setAverageHoursPerPoint(0D);
            average.setAverageDollarsPerPoint(0D);
        }


        return average;
    }


}
