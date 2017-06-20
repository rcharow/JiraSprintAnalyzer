import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { JiraBoard } from "../jira/jira.model";
import { DashboardService } from "../dashboard/dashboard.service";
import { JiraSprint } from "../jira/jira.model";

@Component({
    selector: 'vertical-nav',
    templateUrl: '/app/navigation/vertical-nav.component.html'
})

export class VerticalNavComponent{
    currentBoard:string;
    currentSprints:string[];
    summarySelected:boolean = true;
    moneySelected:boolean = false;
    timeSelected:boolean = false;

    constructor(private router:Router, private dashboardService:DashboardService){}

    ngOnInit(){
        this.dashboardService.currentBoard.subscribe((board:JiraBoard) => {
            this.currentBoard = board ? board.id : null;
        });

        this.dashboardService.currentSprints.subscribe((sprints:string[]) => {
            this.currentSprints = sprints;
        });

        this.dashboardService.currentView.subscribe((view:string) => {
            this.summarySelected = false;
            this.moneySelected = false;
            this.timeSelected = false;
            this[view + 'Selected'] = true;
        });
    }

    selectView(view:string) {
        this.summarySelected = false;
        this.moneySelected = false;
        this.timeSelected = false;
        this[view + 'Selected'] = true;

        this.router.navigate([`/dashboard/analysis/${view}/`], { queryParams: {board: this.currentBoard, sprints: this.currentSprints} });
    }
}