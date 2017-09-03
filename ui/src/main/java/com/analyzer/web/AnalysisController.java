package com.analyzer.web;

import com.analyzer.domain.JiraSprintPointAnalysis;
import com.analyzer.domain.JiraSprintSummary;
import com.analyzer.analysis.PointAnalysisService;
import com.analyzer.analysis.SummaryAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by rcharow on 2/19/17.
 */
@Component
@Path("/analysis")
@Produces(MediaType.APPLICATION_JSON)
public class AnalysisController {

    private final SummaryAnalysisService summaryAnalysisService;
    private final PointAnalysisService pointAnalysisService;

    @Autowired
    public AnalysisController(SummaryAnalysisService summaryAnalysisService, PointAnalysisService pointAnalysisService) {
        this.summaryAnalysisService = summaryAnalysisService;
        this.pointAnalysisService = pointAnalysisService;
    }

    @GET
    @Path("/summary/{sprintId}")
    public JiraSprintSummary getSingleSprintSummary( @PathParam("sprintId") String sprintId) {
        return summaryAnalysisService.getSprintSummary(sprintId, false);
    }

    @GET
    @Path("/summary/worklogs/{sprintId}")
    public JiraSprintSummary getSprintSummaryWithWorklogs(@PathParam("sprintId") String sprintId) {
        return summaryAnalysisService.getSprintSummary(sprintId, true);
    }

    @GET
    @Path("/point/{boardId}")
    public List<JiraSprintPointAnalysis> getBoardPointAnalyses(@PathParam("boardId") String boardId) {
        return pointAnalysisService.getBoardPointAnalysis(boardId);
    }

    @GET
    @Path("/point/{boardId}/sprint/{sprintId}")
    public JiraSprintPointAnalysis getSprintPointAnalyses(@PathParam("boardId") String boardId, @PathParam("sprintId") String sprintId) {
        return pointAnalysisService.getSprintPointAnalysis(boardId, sprintId);
    }

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