import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { DashboardResolveService } from './dashboard-resolve.service';
import { SprintSummaryComponent } from './analysis/sprint-summary/sprint-summary.component';
import { AnalysisComponent } from './analysis/analysis.component';
import { EstimateCostComponent } from './analysis/money/estimate-cost.component';
import { PointCostComponent } from './analysis/money/point-cost.component';

const routes:Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'analysis',
        component: AnalysisComponent,
        children: [
          {
            path: 'summary',
            component: SprintSummaryComponent,
            resolve: { updateView: DashboardResolveService }
          },
          {
            path: 'estimate-cost',
            component: EstimateCostComponent,
            resolve: { updateView: DashboardResolveService }
          },
          {
            path: 'point-cost',
            component: PointCostComponent,
            resolve: { updateView: DashboardResolveService }
          }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [DashboardResolveService]
})
export class DashboardRoutingModule {
}
