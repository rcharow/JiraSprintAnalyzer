import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { JiraService } from '../jira/jira.service';
import { NavBarComponent } from './navbar.component';
import { PillNavComponent } from './pill-nav.component';
import { VerticalNavComponent } from './vertical-nav.component';
import { DashboardService } from "../dashboard/dashboard.service";

@NgModule({
    imports: [
      SharedModule,
      RouterModule
    ],
    declarations: [
       NavBarComponent,
       PillNavComponent,
       VerticalNavComponent
    ],
    exports: [
      NavBarComponent,
      PillNavComponent,
      VerticalNavComponent
    ]
})
export class NavigationModule { }