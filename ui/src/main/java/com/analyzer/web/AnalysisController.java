package com.analyzer.web;

import com.analyzer.domain.JiraSprintSummary;
import com.analyzer.domain.JiraWorklogSummary;
import com.analyzer.service.analysis.SummaryAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by rcharow on 2/19/17.
 */
@Component
@Path("/analysis")
@Produces(MediaType.APPLICATION_JSON)
public class AnalysisController {

    private final SummaryAnalysisService summaryAnalysisService;

    @Autowired
    public AnalysisController(SummaryAnalysisService summaryAnalysisService) {
        this.summaryAnalysisService = summaryAnalysisService;
    }

    @GET
    @Path("/summary/{sprintId}")
    public JiraSprintSummary getSingleSprintSummary(@PathParam("sprintId") String sprintId) { return summaryAnalysisService.getSprintSummary(sprintId); }

    @GET
    @Path("/worklogs/{sprintId}")
    public JiraWorklogSummary getSingleSprintWorklogSummary(@PathParam("sprintId") String sprintId) {
        return summaryAnalysisService.getSprintWorklogSummary(sprintId);
    }

    @GET
    @Path("/summary/worklogs/{sprintId}")
    public JiraSprintSummary getSprintSummaryWithWorklogs(@PathParam("sprintId") String sprintId) { return summaryAnalysisService.getSprintSummaryWithWorklogs(sprintId); }

}
//    @GET
//    @Path("/stats/{boardId}/{startSprintCompleteDate}/{endSprintCompleteDate}/{isMultiSprint}")
//    public List<JiraSprintSummary> getSprintStats(
//            @PathParam("boardId") String boardId,
//            @PathParam("startSprintCompleteDate") Date startSprintCompleteDate,
//            @PathParam("endSprintCompleteDate") Date endSprintCompleteDate,
//            @PathParam("isMultiSprint") boolean isMultiSprint ) {
//
//        return sprintStatsService.getSprintSummary(boardId, startSprintCompleteDate, endSprintCompleteDate);
//    }
//}