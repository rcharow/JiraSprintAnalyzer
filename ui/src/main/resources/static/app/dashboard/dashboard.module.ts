import { NgModule } from '@angular/core';
import { DashboardRoutingModule } from "./dashboard-routing.module";
import { JiraService } from "../jira/jira.service";
import { DashboardComponent } from "./dashboard.component";
import { SingleSprintStatsComponent } from './single-sprint-stats.component';
import { NavigationModule } from "../navigation/navigation.module";
import { DashboardOptionsComponent } from "./dashboard-options.component";
import { SharedModule } from "../shared/shared.module";

@NgModule({
  imports: [
    SharedModule,
    DashboardRoutingModule,
    NavigationModule
  ],
  declarations: [
    DashboardComponent,
    SingleSprintStatsComponent,
    DashboardOptionsComponent
  ],
  exports: [
    DashboardComponent,
    SingleSprintStatsComponent,
    DashboardOptionsComponent
  ],
  providers: [JiraService]
})
export class DashboardModule {
}