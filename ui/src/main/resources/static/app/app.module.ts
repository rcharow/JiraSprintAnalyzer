import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { HomeComponent } from './home';

@NgModule({
    imports: [BrowserModule,
        RouterModule.forRoot([
            { path: '', redirectTo: 'revenue', pathMatch: 'full'},
        ]),
        HttpModule
    ],
    providers: [
        { provide: APP_BASE_HREF, useValue: '/analyzer/' }
    ],
    exports: [
        RouterModule
    ],
    declarations: [HomeComponent],
    bootstrap: [HomeComponent],
})
export class AppModule { }