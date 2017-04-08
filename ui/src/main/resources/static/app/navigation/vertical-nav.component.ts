import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JiraBoard } from "../jira/jira.model";
import { DashboardService } from "../dashboard/dashboard.service";
import { JiraSprint } from "../jira/jira.model";

@Component({
    selector: 'vertical-nav',
    templateUrl: '/app/navigation/vertical-nav.component.html'
})

export class VerticalNavComponent{
    currentBoard:String;
    startSprint:String;
    endSprint:String;
    isMultiSprint:boolean;
    summarySelected:boolean = true;
    moneySelected:boolean = false;
    timeSelected:boolean = false;

    constructor(private dashboardService:DashboardService){}

    ngOnInit(){
        this.dashboardService.currentBoard.subscribe((board:JiraBoard) => {
            this.currentBoard = board ? board.id : null;
        });

        this.dashboardService.startSprint.subscribe((sprint:JiraSprint) => {
            this.startSprint = sprint ? sprint.id : null;
        });

        this.dashboardService.endSprint.subscribe((sprint:JiraSprint) => {
            this.endSprint = sprint ? sprint.id : null;
        });

        this.dashboardService.isMultiSprint.subscribe((isMultiSprint:boolean) => {
            this.isMultiSprint = isMultiSprint;
        });
    }

    selectView(view:string) {
        this.summarySelected = false;
        this.moneySelected = false;
        this.timeSelected = false;
        this[view] = true;
    }
}