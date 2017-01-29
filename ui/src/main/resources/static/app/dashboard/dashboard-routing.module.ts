
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardResolveService } from './dashboard-resolve.service';
import { SingleSprintStatsComponent } from "./single-sprint-stats.component";


let routes: Routes = [
    {
        path: 'single/:boardId',
        component: SingleSprintStatsComponent,
        resolve: { boards: DashboardResolveService },
        children: [
            {
                path: ':boardId',
                component: SingleSprintStatsComponent,
                resolve: { sprints: DashboardResolveService }
            }
        ]
    }];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [DashboardResolveService]
})
export class DashboardRoutingModule { }
