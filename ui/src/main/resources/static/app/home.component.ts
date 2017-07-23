import { Component, ViewContainerRef } from '@angular/core';
import {JiraService} from './jira/jira.service';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

@Component({
    selector: 'home',
    templateUrl: '/app/home.component.html'
})
export class HomeComponent{

    constructor(private jiraService:JiraService, private toastr:ToastsManager, private vcr: ViewContainerRef) {
        this.toastr.setRootViewContainerRef(vcr);
    };

    ngOnInit() {

        this.jiraService.jiraServiceError.subscribe(error => {
            if(error) {this.toastr.error(error.userMessage,error.title);}
        });

        //Initialize bootstrap tooltips
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        });
    }
}