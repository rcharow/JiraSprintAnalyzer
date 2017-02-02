import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardResolveService } from './dashboard-resolve.service'
import { DashboardComponent } from './dashboard.component';
import { SingleSprintStatsComponent } from './single-sprint-stats.component';

const routes:Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'stats',
        component: SingleSprintStatsComponent,
        resolve: {sprints: DashboardResolveService}
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
