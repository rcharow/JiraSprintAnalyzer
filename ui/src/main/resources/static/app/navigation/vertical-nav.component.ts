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
    private currentBoard:string;
    private currentSprints:string[];
    private summarySelected:boolean = true;
    private moneySelected:boolean = false;
    private timeSelected:boolean = false;
    private chartAll:boolean = false;

    constructor(private router:Router, private dashboardService:DashboardService){}

    ngOnInit(){
        this.dashboardService.currentBoard.subscribe((board:JiraBoard) => {
            this.currentBoard = board ? board.id : null;
        });

        this.dashboardService.currentSprints.subscribe((sprints:string[]) => {
            this.currentSprints = sprints;
        });

        this.dashboardService.chartAllSprints.subscribe((chartAll:boolean) => this.chartAll = chartAll);

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

        this.router.navigate([`/dashboard/analysis/${view}/`], { queryParams: {board: this.currentBoard, sprints: this.currentSprints, allSprints: this.chartAll} });
    }
}