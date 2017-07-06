import {Component} from '@angular/core';
import {JiraBoard, JiraSprintPointAnalysis} from "../../../jira/jira.model";
import {JiraService} from "../../../jira/jira.service";
import {MoneyChartService} from "./money-chart.service";
import {forIn, each} from "lodash";


@Component({
  selector: 'money',
  templateUrl: '/app/dashboard/analysis/money/money-chart.html',
  providers: [MoneyChartService]
})

export class PointCostComponent {
  board: JiraBoard[] = null;
  analysis: JiraSprintPointAnalysis[];
  loading: Boolean;
  type: string = 'bar';
  chartData: object = {};
  chartColors: string[];
  chartOptions: object;
  view:string = 'point-cost';

  constructor(private jiraService: JiraService, private chartService: MoneyChartService) {
  }

  ngOnInit() {
    this.loading = true;
    this.chartOptions = this.chartService.getAveragePointCostOptions();
    this.chartColors = this.chartService.getChartColors();

    this.jiraService.currentPointAnalysis.subscribe((analysis: JiraSprintPointAnalysis[]) => {
      this.analysis = analysis;
      this.loading = !analysis;
      this.chartData = analysis ? this.chartService.formatPointCostData(analysis) : null;
    });
  }
}