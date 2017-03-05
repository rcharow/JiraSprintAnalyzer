import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {JiraBoard} from "../jira/jira.model";
import {DashboardService} from "../dashboard/dashboard.service";
import {JiraSprint} from "../jira/jira.model";

@Component({
    selector: 'vertical-nav',
    templateUrl: '/app/navigation/vertical-nav.component.html'
})

export class VerticalNavComponent{
    currentBoard:JiraBoard;
    startSprint:JiraSprint;
    endSprint:JiraSprint;
    isMultiSprint:boolean;

    constructor(private dashboardService:DashboardService){}

    ngOnInit(){
        this.dashboardService.currentBoard.subscribe((board:JiraBoard) => {
            this.currentBoard = board;
        });

        this.dashboardService.startSprint.subscribe((sprint:JiraSprint) => {
            this.startSprint = sprint;
        });

        this.dashboardService.endSprint.subscribe((sprint:JiraSprint) => {
            this.endSprint = sprint;
        });

        this.dashboardService.isMultiSprint.subscribe((isMultiSprint:boolean) => {
            this.isMultiSprint = isMultiSprint;
        });
    }
}