package com.analyzer.analysis;

import com.analyzer.dao.JiraIssueDao;
import com.analyzer.dao.JiraSprintDao;
import com.analyzer.domain.*;
import com.analyzer.jira.JiraRapidViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcharow on 6/16/17.
 */
@Component
public class PointAnalysisService {
    private JiraIssueDao jiraIssueDao;
    private JiraSprintDao jiraSprintDao;
    private JiraRapidViewService jiraRapidViewService;
    private Integer clientCostPerHour;
    private Integer internalCostPerHour;
    private final double[] pointValues = new double[]{0, 0.5, 1, 2, 3, 5, 8, 13, 21, 42, 101};

    @Autowired
    public PointAnalysisService(
        JiraIssueDao jiraIssueDao,
        JiraSprintDao jiraSprintService,
        JiraRapidViewService jiraRapidViewService,
        @Value("${domain.hourlyCost.client}") String clientCost,
        @Value("${domain.hourlyCost.internal}") String internalCost
    ) {
        this.jiraIssueDao = jiraIssueDao;
        this.jiraSprintDao = jiraSprintService;
        this.jiraRapidViewService = jiraRapidViewService;
        this.clientCostPerHour = Integer.parseInt(clientCost);
        this.internalCostPerHour = Integer.parseInt(internalCost);
    }

    public List<JiraSprintPointAnalysis> getBoardPointAnalysis(String boardId) {
        List<JiraSprint> sprints = jiraSprintDao.getClosedSprintsByBoardId(boardId);
        List<JiraSprintIssues> sprintIssueList = new ArrayList<>();
        for(JiraSprint sprint : sprints) {
            List<JiraIssue> issues = jiraIssueDao.getParentIssuesBySprint(sprint.getId());
            JiraSprintIssues sprintIssues = new JiraSprintIssues();
            sprintIssues.setSprint(sprint);
            sprintIssues.setJiraIssues(issues);
            sprintIssueList.add(sprintIssues);
        }

        List<JiraSprintPointAnalysis> pointAnalyses = getPointAnalysis(sprintIssueList, boardId);
        return pointAnalyses;
    }

    public JiraSprintPointAnalysis getSprintPointAnalysis(String boardId, String sprintId) {
        List<JiraIssue> issues = jiraIssueDao.getParentIssuesBySprint(sprintId);
        JiraSprint fullSprint = jiraSprintDao.getSprint(sprintId);

        JiraSprintIssues sprintIssues = new JiraSprintIssues();
        sprintIssues.setJiraIssues(issues);
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

            analysis.setSprintId(sprint.getSprint().getId());
            analysis.setSprintName(sprint.getSprint().getName());
            analysis.setStartDate(sprint.getSprint().getStartDate());
            analysis.setEndDate(sprint.getSprint().getEndDate());
            analysis.setPointAnalysis(new ArrayList<JiraPointAnalysis>());

            for (Double pointValue : pointValues) {
                Integer count = 0;
                float totalTime = 0F;
                float totalDollars = 0F;

                for (JiraIssue issue : sprint.getJiraIssues()) {
                    if (issue.getDetails().getPoints() != null && issue.getDetails().getPoints().equals(pointValue)) {
                        count += 1;
                        if (issue.getDetails().getTimeSpent() != null) {
                            float hours = issue.getDetails().getTimeSpent() / 60 / 60;
                            totalTime = totalTime + (float) issue.getDetails().getTimeSpent();
                            totalDollars = totalDollars + (float) (hours * clientCostPerHour);
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

        for (JiraIssue issue : sprintIssues.getJiraIssues()) {
            if (issue.getDetails().getTimeSpent() != null) {
                totalTime += issue.getDetails().getTimeSpent();
            }
            if (issue.getDetails().getPoints() != null) {
                totalPoints += issue.getDetails().getPoints();
            }
        }

        JiraSprintPointAverage average = new JiraSprintPointAverage();
        average.setSprintId(sprintIssues.getSprint().getId());
        average.setSprintName(sprintIssues.getSprint().getName());
        Double totalHours = totalTime / 60 / 60;
        average.setTotalHours(totalHours);
        average.setTotalDollars(totalHours * clientCostPerHour);
        average.setTotalCompletedPoints(totalPoints);

        if (sprintIssues.getJiraIssues().size() > 0) {
            average.setTotalCompletedIssues(sprintIssues.getJiraIssues().size());
            average.setAverageHoursPerPoint(totalPoints > 0.0 ? totalHours / totalPoints : totalHours);
            average.setAverageDollarsPerPoint(totalPoints > 0.0 ? average.getAverageHoursPerPoint() * internalCostPerHour : totalHours * internalCostPerHour);
        } else {
            average.setTotalCompletedIssues(0);
            average.setAverageHoursPerPoint(0D);
            average.setAverageDollarsPerPoint(0D);
        }


        return average;
    }


}
