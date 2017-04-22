import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JiraSprintSummary, JiraWorklogSummary } from "../jira/jira.model";
import { JiraService } from "../jira/jira.service";

@Component({
  selector: 'sprint-stats',
  templateUrl: '/app/dashboard/sprint-summary.component.html'
})

export class SprintSummaryComponent implements OnInit {
  summary:JiraSprintSummary = new JiraSprintSummary();
  worklogs:JiraWorklogSummary = new JiraWorklogSummary();
  loadingSummary:boolean;
  loadingWorklogs:boolean;

  constructor(private route: ActivatedRoute, private jiraService: JiraService){}

  ngOnInit(){
    this.loadingSummary = true;
    this.loadingWorklogs = true;

    this.jiraService.currentSummary.subscribe((summary:JiraSprintSummary) => {
      this.summary = summary ? summary : this.summary;
      this.loadingSummary = !summary;
    });

    this.jiraService.currentWorklogSummary.subscribe((summary:JiraWorklogSummary) => {
      this.worklogs = summary ? summary : this.worklogs;
      this.loadingWorklogs = !summary;
    });
  }

}
