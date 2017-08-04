import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';

import { MultiselectDropdownModule } from 'angular-2-dropdown-multiselect';
import { ChartModule } from 'angular2-chartjs';
import { ToastModule, ToastOptions } from 'ng2-toastr/ng2-toastr';

import { HomeComponent } from './home.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { NavigationModule } from './navigation/navigation.module';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './app.routing.module';
import {AnalysisModule} from './analysis/analysis.module';
import {JiraService} from './jira/jira.service';
import {DashboardService} from './dashboard/dashboard.service';
import {AuthService} from './shared/auth.service';
import { GlobalToastOptions } from './shared/toast-options';
import {HealthComponent} from "./health.component";

@NgModule({
  declarations: [
    HomeComponent,
    HealthComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([
      {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
    ]),
    HttpModule,
    DashboardModule,
    NavigationModule,
    AnalysisModule,
    AppRoutingModule,
    MultiselectDropdownModule,
    ChartModule,
    ToastModule.forRoot()
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/a/'},
    AuthService,
    JiraService,
    DashboardService,
    { provide: ToastOptions, useClass: GlobalToastOptions }
  ],
  exports: [
    RouterModule,
    ChartModule
  ],
  bootstrap: [HomeComponent],
})
export class AppModule {
}