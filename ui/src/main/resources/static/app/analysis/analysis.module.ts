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

@NgModule({
  imports: [
    RouterModule,
    SharedModule,
    ChartModule
  ],
  declarations: [
    SprintSummaryComponent,
    AnalysisComponent,
    EstimateCostComponent,
    PointCostComponent,
    AnalysisChartComponent
  ],
  exports: [
    SprintSummaryComponent,
    AnalysisComponent,
    EstimateCostComponent,
    PointCostComponent,
    AnalysisChartComponent
  ],
  providers: [
    JiraService,
    DashboardService,
    MoneyChartService
  ]
})
export class AnalysisModule {
}