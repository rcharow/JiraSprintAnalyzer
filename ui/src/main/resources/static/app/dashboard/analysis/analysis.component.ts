import {Component, OnInit, OnDestroy} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {DashboardService} from "../../dashboard/dashboard.service";
import {JiraService} from "../../jira/jira.service";
import {difference} from "lodash";


@Component({
  selector: 'analysis',
  templateUrl: '/app/dashboard/analysis/analysis.component.html'
})

export class AnalysisComponent implements OnInit {

  private currentBoardId:string;
  private currentSprints:string[] = [];

  constructor(private route: ActivatedRoute, private router:Router, private location: Location, private dashboardService: DashboardService, private jiraService: JiraService) {
  }

  ngOnInit() {

    this.dashboardService.currentSprints.subscribe((sprints: string[]) => {
      this.currentSprints = sprints;
    });

    this.route.queryParams.subscribe((queryParams: any) => {
      let sprints = typeof queryParams['sprints'] === 'string' ? [queryParams['sprints']] : queryParams['sprints'];
      let board = queryParams['board'];

      //TODO: Is there a better way to do this? Can angular handle it?
      if(difference(sprints, this.currentSprints).length || difference(this.currentSprints, sprints).length) {
        this.dashboardService.setCurrentSprints(sprints);
        this.jiraService.setCurrentSummary(sprints);
      }

      if(this.currentBoardId !== board) {
        this.jiraService.setCurrentPointAnalysis(board);
        this.currentBoardId = board;
      }
    });
  }
}