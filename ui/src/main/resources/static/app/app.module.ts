import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { HomeComponent } from './home.component';
import  { NavBarComponent } from "./navbar/navbar.component";
import  { VerticalNavComponent } from "./vertical-nav/vertical-nav.component";
import { PillNavComponent } from "./pill-nav/pill-nav.component";

@NgModule({
    declarations: [
        HomeComponent,
        NavBarComponent,
        VerticalNavComponent,
        PillNavComponent
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot([
            { path: '', redirectTo: '/', pathMatch: 'full'},
        ]),
        HttpModule,
        FormsModule
    ],
    providers: [
        { provide: APP_BASE_HREF, useValue: '/' }
    ],
    exports: [
        RouterModule
    ],
    bootstrap: [HomeComponent],
})
export class AppModule { }