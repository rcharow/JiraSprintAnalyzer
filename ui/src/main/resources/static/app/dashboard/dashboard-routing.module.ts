import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { DashboardResolveService } from './dashboard-resolve.service';
import { SprintSummaryComponent } from './analysis/sprint-summary/sprint-summary.component';
import { AnalysisComponent } from './analysis/analysis.component';
import { MoneyComponent } from './analysis/money/money.component';

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
            path: 'money',
            component: MoneyComponent,
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
