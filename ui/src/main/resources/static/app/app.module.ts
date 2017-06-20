import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { HomeComponent } from './home.component';
import { DashboardModule } from "./dashboard/dashboard.module";
import { NavigationModule } from "./navigation/navigation.module";
import { LoginComponent } from "./login/login.component";
import { AuthService } from "./shared/auth.service";
import { AppRoutingModule } from "./app.routing.module";
import { MultiselectDropdownModule } from 'angular-2-dropdown-multiselect';
import { ChartModule } from 'angular2-chartjs';

@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
    ]),
    HttpModule,
    DashboardModule,
    NavigationModule,
    AppRoutingModule,
    MultiselectDropdownModule,
    ChartModule
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/a/'},
    AuthService
  ],
  exports: [
    RouterModule,
    ChartModule
  ],
  bootstrap: [HomeComponent],
})
export class AppModule {
}