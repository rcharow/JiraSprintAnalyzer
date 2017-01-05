import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { HomeComponent } from './home.component';
import {NavBarComponent} from "./navbar/navbar.component";

@NgModule({
    declarations: [
        HomeComponent,
        NavBarComponent
    ],
    imports: [BrowserModule,
        RouterModule.forRoot([
            { path: '', redirectTo: '/', pathMatch: 'full'},
        ]),
        HttpModule
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