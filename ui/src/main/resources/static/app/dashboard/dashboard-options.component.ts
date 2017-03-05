import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { JiraService } from '../jira/jira.service';
import { JiraBoard } from '../jira/jira.model';
import { JiraSprint } from "../jira/jira.model";
import { DashboardService } from "./dashboard.service";

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
  startSprint:JiraSprint;
  endSprint:JiraSprint;
  isMultiSprint:boolean;

  constructor(private jiraService:JiraService, private dashboardService:DashboardService) {

  }

  ngOnInit() {
    this.jiraService.getScrumBoards()
      .subscribe(boards => {
        this.boards = boards;
      });
  }

  onBoardSelect() {
    this.dashboardService.setCurrentBoard(this.selectedBoard);
    this.jiraService.getSprintsByBoardId(this.selectedBoard.id)
      .subscribe(sprints => {
        this.sprints = sprints;
      })
  }

  submitEnabled() {
    if(this.isMultiSprint) return this.startSprint && this.endSprint;
    return this.startSprint;
  }

  submitOptions() {
    this.dashboardService.setStartSprint(this.startSprint);
    this.dashboardService.setEndSprint(this.endSprint);
    this.dashboardService.setMultiSprint(this.isMultiSprint);
  }

}