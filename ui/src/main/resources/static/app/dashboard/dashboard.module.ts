import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DashboardRoutingModule } from "./dashboard-routing.module";
import { JiraService } from "../jira/jira.service";
import { DashboardComponent } from "./dashboard.component";
import { SprintSummaryComponent } from './sprint-summary.component';
import { NavigationModule } from "../navigation/navigation.module";
import { DashboardOptionsComponent } from "./dashboard-options.component";
import { SharedModule } from "../shared/shared.module";
import { DashboardService } from "./dashboard.service";

@NgModule({
  imports: [
    RouterModule,
    SharedModule,
    DashboardRoutingModule,
    NavigationModule
  ],
  declarations: [
    DashboardComponent,
    SprintSummaryComponent,
    DashboardOptionsComponent
  ],
  exports: [
    DashboardComponent,
    SprintSummaryComponent,
    DashboardOptionsComponent
  ],
  providers: [
    JiraService,
    DashboardService
  ]
})
export class DashboardModule {
}