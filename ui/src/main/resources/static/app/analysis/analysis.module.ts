import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JiraService } from "../jira/jira.service";
import { SprintSummaryComponent } from '../analysis/sprint-summary/sprint-summary.component';
import { AnalysisComponent } from '../analysis/analysis.component';
import { EstimateCostComponent } from '../analysis/money/estimate-cost.component';
import { PointCostComponent } from '../analysis/money/point-cost.component';
import { SharedModule } from "../shared/shared.module";
import { DashboardService } from "../dashboard/dashboard.service";
import { ChartModule } from 'angular2-chartjs';
import { AnalysisChartComponent } from '../analysis/analysis-chart.component';
import {MoneyChartService} from "./money/money-chart.service";
import {EstimateHoursComponent} from "./time/estimate-hours.component";
import {PointHoursComponent} from "./time/point-hours.component";
import {TimeChartService} from "./time/time-chart.service";

@NgModule({
  imports: [
    RouterModule,
    SharedModule,
    ChartModule
  ],
  declarations: [
    SprintSummaryComponent,
    AnalysisComponent,
    AnalysisChartComponent,
    EstimateCostComponent,
    PointCostComponent,
    EstimateHoursComponent,
    PointHoursComponent
  ],
  exports: [
    SprintSummaryComponent,
    AnalysisComponent,
    AnalysisChartComponent,
    EstimateCostComponent,
    PointCostComponent,
    EstimateHoursComponent,
    PointHoursComponent
  ],
  providers: [
    JiraService,
    DashboardService,
    MoneyChartService,
    TimeChartService
  ]
})
export class AnalysisModule {
}