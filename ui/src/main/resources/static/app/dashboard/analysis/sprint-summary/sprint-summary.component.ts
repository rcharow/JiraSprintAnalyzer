import { Component, OnInit } from '@angular/core';
import { JiraSprintSummary } from "../../../jira/jira.model";
import { JiraService } from "../../../jira/jira.service";

@Component({
  selector: 'sprint-stats',
  templateUrl: '/app/dashboard/analysis/sprint-summary/sprint-summary.component.html'
})

export class SprintSummaryComponent implements OnInit {
  summaries:JiraSprintSummary[] = null;
  loadingSummary:boolean;

  constructor(private jiraService: JiraService){}

  ngOnInit(){
    this.loadingSummary = true;

    this.jiraService.currentSummary.subscribe((summary:JiraSprintSummary[]) => {
      this.summaries = summary;
      this.loadingSummary = !summary;
    });

  }

}
