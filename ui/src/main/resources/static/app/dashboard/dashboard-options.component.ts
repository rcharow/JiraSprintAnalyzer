import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { JiraService } from '../jira/jira.service';
import { JiraBoard } from '../jira/jira.model';
import { JiraSprint } from "../jira/jira.model";

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
  multiSprint:Boolean;

  constructor(private jiraService:JiraService) {

  }

  ngOnInit() {
    this.jiraService.getScrumBoards()
      .subscribe(boards => {
        this.boards = boards;
      });
  }

  onBoardSelect() {
    this.jiraService.getSprintsByBoardId(this.selectedBoard.id)
      .subscribe(sprints => {
        this.sprints = sprints;
      })
  }

  submitEnabled() {
    if(this.multiSprint) return this.startSprint && this.endSprint;
    return this.startSprint;
  }

}