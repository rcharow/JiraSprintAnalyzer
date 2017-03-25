import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { JiraService } from '../jira/jira.service';
import { JiraBoard } from '../jira/jira.model';
import { JiraSprint } from "../jira/jira.model";
import { DashboardService } from "./dashboard.service";
import { Router } from '@angular/router';
import { filter } from 'lodash';

@Component({
  selector: 'dashboard-options',
  templateUrl: '/app/dashboard/dashboard-options.component.html',
  providers: [
    JiraService
  ]
})

export class DashboardOptionsComponent {

  boards:JiraBoard[];
  selectedBoard:JiraBoard;
  sprints: JiraSprint[];
  endSprints: JiraSprint[];
  startSprint:JiraSprint;
  endSprint:JiraSprint;
  isMultiSprint:boolean;
  loading:boolean;

  constructor(private jiraService:JiraService, private dashboardService:DashboardService, private router:Router) {

  }

  ngOnInit() {
    this.loading = true;
    this.jiraService.getScrumBoards()
      .subscribe(boards => {
        this.boards = boards;
        this.loading = false;
      });
  }

  onBoardSelect() {
    this.dashboardService.setCurrentBoard(this.selectedBoard);
    this.loading = true;
    this.startSprint = undefined;
    this.endSprint = undefined;

    this.jiraService.getClosedSprintsByBoardId(this.selectedBoard.id)
      .subscribe(sprints => {
        this.sprints = sprints;
        this.loading = false;
      })
  }

  onStartSprintSelect() {
    this.endSprint = undefined;
    this.endSprints = filter(this.sprints, (sprint) => {
      return sprint.startDate > this.startSprint.startDate;
    });
  }

  onMultiSprintSelect() {
    if(!this.isMultiSprint) {
      this.endSprint = undefined;
    }
  }

  submitEnabled() {
    if(this.isMultiSprint) return this.startSprint && this.endSprint;
    return this.startSprint;
  }

  submitOptions() {
    this.dashboardService.setStartSprint(this.startSprint);
    this.dashboardService.setEndSprint(this.endSprint);
    this.dashboardService.setMultiSprint(this.isMultiSprint);

    var url = '/dashboard/summary/' + this.startSprint.id;
    if(typeof(this.endSprint) !== 'undefined') {
      url += '/' + this.endSprint.id + '/true';
    }
    this.router.navigateByUrl(url);
  }

}