import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { HomeComponent } from './home.component';
import { DashboardModule } from "./dashboard/dashboard.module";
import { NavigationModule } from "./navigation/navigation.module";

@NgModule({
    declarations: [
        HomeComponent
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot([
            { path: '', redirectTo: '/dashboard', pathMatch: 'full'},
        ]),
        HttpModule,
        DashboardModule,
        NavigationModule
    ],
    providers: [
        { provide: APP_BASE_HREF, useValue: '/a/' }
    ],
    exports: [
        RouterModule
    ],
    bootstrap: [HomeComponent],
})
export class AppModule { }