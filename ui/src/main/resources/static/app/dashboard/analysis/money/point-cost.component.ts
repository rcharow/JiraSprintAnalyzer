import {Component} from '@angular/core';
import {JiraBoard, JiraSprintPointAnalysis} from '../../../jira/jira.model';
import {JiraService} from '../../../jira/jira.service';
import {MoneyChartService} from './money-chart.service';

@Component({
  selector: 'money',
  template: '<analysis-chart [loading]="loading" [routeLinks]="chartLinks" [chartType]="type" [chartData]="chartData" [chartOptions]="chartOptions"></analysis-chart>',
  providers: [MoneyChartService]
})

export class PointCostComponent {
  board: JiraBoard[] = null;
  analysis: JiraSprintPointAnalysis[];
  loading: Boolean;
  type: string = 'bar';
  chartData: object = undefined;
  chartColors: string[];
  chartOptions: object;
  view:string = 'point-cost';
  chartLinks:object[] = [
    {route: '/dashboard/analysis/estimate-cost', view: 'estimate-cost', classes: 'fa fa-line-chart analysis__icon--inactive'},
    {route: '/dashboard/analysis/point-cost', view:'point-cost', classes: 'fa fa-bar-chart pl-1'}
    ];

  constructor(private jiraService: JiraService, private chartService: MoneyChartService) {
  }

  ngOnInit() {
    this.chartOptions = this.chartService.getAveragePointCostOptions();
    this.chartColors = this.chartService.getChartColors();

    this.jiraService.currentPointAnalysisLoading.subscribe(loading => this.loading = loading);

    this.jiraService.currentPointAnalysis.subscribe((analysis: JiraSprintPointAnalysis[]) => {
      this.analysis = analysis;
      this.loading = !analysis;
      this.chartData = analysis ? this.chartService.formatPointCostData(analysis) : undefined;
    });
  }
}