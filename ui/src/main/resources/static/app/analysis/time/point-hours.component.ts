import {Component} from '@angular/core';
import {JiraBoard, JiraSprintPointAnalysis} from '../../jira/jira.model';
import {JiraService} from '../../jira/jira.service';
import {TimeChartService} from "./time-chart.service";

@Component({
  selector: 'money',
  template: '<analysis-chart [loading]="loading" [routeLinks]="chartLinks" [chartType]="type" [chartData]="chartData" [chartOptions]="chartOptions"></analysis-chart>',
  providers: [TimeChartService]
})

export class PointHoursComponent {
  board: JiraBoard[] = null;
  analysis: JiraSprintPointAnalysis[];
  loading: Boolean;
  type: string = 'bar';
  chartData: object = undefined;
  chartColors: string[];
  chartOptions: object;
  view:string = 'point-hours';
  chartLinks:object[] = [
    {route: '/dashboard/analysis/estimate-hours', classes: 'fa fa-line-chart analysis__icon--inactive'},
    {route: '/dashboard/analysis/point-hours', classes: 'fa fa-bar-chart pl-1'}
    ];

  constructor(private jiraService: JiraService, private chartService: TimeChartService) {
  }

  ngOnInit() {
    this.chartOptions = this.chartService.getAveragePointHoursOptions();
    this.chartColors = this.chartService.getChartColors();

    this.jiraService.currentPointAnalysisLoading.subscribe(loading => this.loading = loading);

    this.jiraService.currentPointAnalysis.subscribe((analysis: JiraSprintPointAnalysis[]) => {
      this.analysis = analysis;
      this.loading = !analysis;
      this.chartData = analysis ? this.chartService.formatPointHoursData(analysis) : undefined;
    });
  }
}