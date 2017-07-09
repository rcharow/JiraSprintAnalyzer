import { Component } from '@angular/core';
import { JiraService } from '../jira/jira.service';
import { JiraBoard } from '../jira/jira.model';
import { JiraSprint } from "../jira/jira.model";
import { DashboardService } from "./dashboard.service";
import { Router, ActivatedRoute } from '@angular/router';
import { each, find  } from 'lodash';
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
  chartAll:boolean = false;
  initialized:boolean = false;

  constructor(private jiraService:JiraService, private dashboardService:DashboardService, private router:Router, private route:ActivatedRoute) {

  }

  ngOnInit() {
    this.loading = true;
     if (this.route.snapshot.queryParams['board']) {
       this.dashboardService.setCurrentBoard(this.route.snapshot.queryParams['board']);
     }


    this.jiraService.getScrumBoards()
      .flatMap(boards => {
        this.boards = boards;
        this.loading = false;
        return this.dashboardService.currentBoard;
      })
      .subscribe(currentBoard => {
        if(currentBoard && !this.initialized){
          this.selectedBoard = find(this.boards, b => { return b.id === currentBoard});
          this.dashboardService.chartAllSprints.subscribe(chartAll => this.chartAll = chartAll);
          this.onBoardSelect();
        }
      });
  }

  onBoardSelect() {
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
        if(!this.initialized) {
          let routeSprints = this.route.snapshot.queryParams['sprints'] ? this.route.snapshot.queryParams['sprints'] : [];
          this.sprintsModel = typeof routeSprints === 'string' ? [routeSprints] : routeSprints;
          this.initialized = true;
        }
      })
  }

  submitEnabled() {
    return this.sprintsModel && this.sprintsModel.length;
  }

  submitOptions() {
    this.router.navigate(['/dashboard/analysis/summary/'], { queryParams: {board: this.selectedBoard.id, sprints: this.sprintsModel, allSprints: this.chartAll} });
  }


}