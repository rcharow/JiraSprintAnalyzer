import {Component, OnInit, OnDestroy} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {DashboardService} from "../dashboard/dashboard.service";
import {JiraService} from "../jira/jira.service";
import {difference} from "lodash";


@Component({
  selector: 'analysis',
  templateUrl: '/app/analysis/analysis.component.html'
})

export class AnalysisComponent implements OnInit {

  private currentBoardId: string;
  private currentSprints: string[] = [];
  private chartAll: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router, private location: Location, private dashboardService: DashboardService, private jiraService: JiraService) {
  }

  ngOnInit() {

    this.dashboardService.currentSprints.subscribe((sprints: string[]) => {
      this.currentSprints = sprints;
    });

    this.dashboardService.chartAllSprints.subscribe((chartAll: boolean) => this.chartAll = chartAll );

    this.route.queryParams.subscribe((queryParams: any) => {
      let sprints = typeof queryParams['sprints'] === 'string' ? [queryParams['sprints']] : queryParams['sprints'];
      let board = queryParams['board'];
      let chartAll = queryParams['allSprints'] === "true";

      //TODO: Is there a better way to do this? Can angular handle it?
      if (this.requestParamsChanged(sprints,chartAll)) {
        this.dashboardService.setCurrentSprints(sprints);
        this.dashboardService.setChartAll((chartAll));
        this.dashboardService.setCurrentBoard(board);
        this.jiraService.setCurrentSummary(board, sprints);

        if (chartAll) {
          this.jiraService.setCurrentPointAnalysisByBoard(board);
        } else {
          this.jiraService.setCurrentPointAnalysisBySprints(board, sprints);
        }
      }

      if (this.currentBoardId !== board) {
        this.currentBoardId = board;
      }
    });
  }

  requestParamsChanged(sprints: string[], chartAll: boolean) {
    return difference(sprints, this.currentSprints).length || difference(this.currentSprints, sprints).length || this.chartAll !== chartAll;
  }
}