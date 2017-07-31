import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SprintSummaryComponent } from '../analysis/sprint-summary/sprint-summary.component';
import { AnalysisComponent } from '../analysis/analysis.component';
import { EstimateCostComponent } from '../analysis/money/estimate-cost.component';
import { PointCostComponent } from '../analysis/money/point-cost.component';
import { SharedModule } from "../shared/shared.module";
import { ChartModule } from 'angular2-chartjs';
import { AnalysisChartComponent } from '../analysis/analysis-chart.component';
import { EstimateHoursComponent } from "./time/estimate-hours.component";
import { PointHoursComponent } from "./time/point-hours.component";
import {AnalysisMessageComponent} from "./analysis-message.component";

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
    PointHoursComponent,
    AnalysisMessageComponent
  ],
  exports: [
    SprintSummaryComponent,
    AnalysisComponent,
    AnalysisChartComponent,
    EstimateCostComponent,
    PointCostComponent,
    EstimateHoursComponent,
    AnalysisMessageComponent
  ]
})
export class AnalysisModule {
}