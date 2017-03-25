import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { StatsResolveService } from "./stats-resolve.service";
import { SprintSummaryComponent } from "./sprint-summary.component";

const routes:Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'summary/:boardId/:start/:end/:multi',
        component: SprintSummaryComponent,
        resolve: { summary: StatsResolveService }
      },
      {
        path: 'summary/:sprintId',
        component: SprintSummaryComponent,
        resolve: { summary: StatsResolveService }
      },
      {
        path: 'summary',
        redirectTo: '/dashboard',
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
