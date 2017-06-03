import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { StatsResolveService } from './stats-resolve.service';
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
            component: SprintSummaryComponent
          },
          {
            path: 'money',
            component: MoneyComponent
          }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [StatsResolveService]
})
export class DashboardRoutingModule {
}
