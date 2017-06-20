import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DashboardRoutingModule } from "./dashboard-routing.module";
import { JiraService } from "../jira/jira.service";
import { DashboardComponent } from "./dashboard.component";
import { SprintSummaryComponent } from './analysis/sprint-summary/sprint-summary.component';
import { AnalysisComponent } from './analysis/analysis.component';
import { MoneyComponent } from './analysis/money/money.component';
import { NavigationModule } from "../navigation/navigation.module";
import { DashboardOptionsComponent } from "./dashboard-options.component";
import { SharedModule } from "../shared/shared.module";
import { DashboardService } from "./dashboard.service";
import { MultiselectDropdownModule } from 'angular-2-dropdown-multiselect';
import { ChartModule } from 'angular2-chartjs';

@NgModule({
  imports: [
    RouterModule,
    SharedModule,
    DashboardRoutingModule,
    NavigationModule,
    MultiselectDropdownModule,
    ChartModule
  ],
  declarations: [
    DashboardComponent,
    SprintSummaryComponent,
    DashboardOptionsComponent,
    AnalysisComponent,
    MoneyComponent
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