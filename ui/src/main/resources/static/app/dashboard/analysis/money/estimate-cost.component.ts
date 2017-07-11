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

export class EstimateCostComponent {
  board: JiraBoard[] = null;
  analysis: JiraSprintPointAnalysis[];
  loading: Boolean;
  type: string = 'line';
  chartData:object = undefined;
  chartColors: string[];
  chartOptions:object;
  view:string = 'estimate-cost';

  constructor(private jiraService: JiraService, private chartService: MoneyChartService) {
  }

  ngOnInit() {
    this.loading = true;
    this.chartOptions = this.chartService.getDollarsByPointEstimateOptions();
    this.chartColors = this.chartService.getChartColors();

    this.jiraService.currentPointAnalysisLoading.subscribe(loading => this.loading = loading);

    this.jiraService.currentPointAnalysis.subscribe((analysis: JiraSprintPointAnalysis[]) => {
      this.analysis = analysis;
      this.chartData = analysis ? this.chartService.formatEstimateCostData(analysis) : undefined;
    });
  }


}
