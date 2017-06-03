import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DashboardService } from "../../dashboard/dashboard.service";
import { JiraService } from "../../jira/jira.service";


@Component({
  selector: 'analysis',
  templateUrl: '/app/dashboard/analysis/analysis.component.html'
})

export class AnalysisComponent implements OnInit{

  constructor(private route:ActivatedRoute, private dashboardService:DashboardService, private jiraService:JiraService) {}

  ngOnInit(){
      this.route.queryParams.subscribe((queryParams:any) => {
        let sprints = queryParams['sprints'];
        this.dashboardService.setCurrentSprints(sprints);
        this.jiraService.setCurrentSummary(sprints);
      });
  }
}