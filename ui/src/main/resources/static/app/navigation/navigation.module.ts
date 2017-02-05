import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { JiraService } from '../jira/jira.service';
import { NavBarComponent } from './navbar.component';
import { PillNavComponent } from './pill-nav.component';
import { VerticalNavComponent } from './vertical-nav.component';

@NgModule({
    imports: [ SharedModule ],
    declarations: [
       NavBarComponent,
       PillNavComponent,
       VerticalNavComponent
    ],
    exports: [
        NavBarComponent,
        PillNavComponent,
        VerticalNavComponent
    ],
    providers: [JiraService]
})
export class NavigationModule { }