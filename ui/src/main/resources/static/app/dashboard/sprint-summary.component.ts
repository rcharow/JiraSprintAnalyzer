import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JiraSprintSummary, JiraWorklogSummary } from "../jira/jira.model";
import { JiraService } from "../jira/jira.service";
import { DashboardService } from "../dashboard/dashboard.service";

@Component({
  selector: 'sprint-stats',
  templateUrl: '/app/dashboard/sprint-summary.component.html'
})

export class SprintSummaryComponent implements OnInit {
  summaries:JiraSprintSummary[] = null;
  //worklogs:JiraWorklogSummary[] = [];
  loadingSummary:boolean;
  loadingWorklogs:boolean;

  constructor(private jiraService: JiraService, private dashboardService: DashboardService){}

  ngOnInit(){
    this.loadingSummary = true;
    this.loadingWorklogs = true;

    this.jiraService.currentSummary.subscribe((summary:JiraSprintSummary[]) => {
      this.summaries = summary;
      this.loadingSummary = !summary;
    });

    //this.jiraService.currentWorklogSummary.subscribe((summary:JiraWorklogSummary[]) => {
    //  this.worklogs = summary;
    //  this.loadingWorklogs = !summary;
    //});
  }

}
