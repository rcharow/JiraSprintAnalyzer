import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { SprintStatsComponent } from './sprint-stats.component';
import { StatsResolveService } from "./stats-resolve.service";

const routes:Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'stats/:boardId/:start/:end/:multi',
        component: SprintStatsComponent,
        resolve: { stats: StatsResolveService }
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
