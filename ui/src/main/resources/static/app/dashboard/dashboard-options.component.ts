import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { JiraService } from '../jira/jira.service';
import { JiraBoard } from '../jira/jira.model';
import { JiraSprint } from "../jira/jira.model";
import { DashboardService } from "./dashboard.service";
import { Router } from '@angular/router';
import { each  } from 'lodash';
import { IMultiSelectOption, IMultiSelectSettings } from 'angular-2-dropdown-multiselect';

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
  loading:boolean;
  sprintOptions:IMultiSelectOption[];
  sprintOptionSettings:IMultiSelectSettings = {
    checkedStyle: 'fontawesome',
    enableSearch: false

  };
  sprintsModel:string[] = [];

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
    this.sprints = undefined;
    this.sprintsModel = [];

    this.jiraService.getClosedSprintsByBoardId(this.selectedBoard.id)
      .subscribe(sprints => {
        this.sprints = sprints;
        this.loading = false;

        this.sprintOptions = [];
        each(sprints, (sprint) => {
          this.sprintOptions.push({
            id: sprint.id,
            name: sprint.name
          })
        });

      })
  }

  submitEnabled() {
    return this.sprintsModel && this.sprintsModel.length;
  }

  submitOptions() {
    this.router.navigate(['/dashboard/analysis/summary/'], { queryParams: {board: this.selectedBoard.id, sprints: this.sprintsModel} });
  }


}