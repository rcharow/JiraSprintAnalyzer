import { NgModule } from '@angular/core';
import { DashboardRoutingModule } from "./dashboard-routing.module";
import { JiraService } from "../jira/jira.service";
import { DashboardComponent } from "./dashboard.component";
import { SingleSprintStatsComponent } from './single-sprint-stats.component';
import { NavigationModule } from "../navigation/navigation.module";

@NgModule({
    imports: [
        DashboardRoutingModule,
        NavigationModule
    ],
    declarations: [
        DashboardComponent,
        SingleSprintStatsComponent,
    ],
    exports: [
    DashboardComponent,
    SingleSprintStatsComponent,
],
    providers: [JiraService]
})
export class DashboardModule { }