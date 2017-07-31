import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Router} from '@angular/router';
import {JiraBoard} from "../jira/jira.model";
import {DashboardService} from "../dashboard/dashboard.service";
import {JiraSprint} from "../jira/jira.model";

@Component({
  selector: 'vertical-nav',
  templateUrl: '/app/navigation/vertical-nav.component.html'
})

export class VerticalNavComponent {
  private currentBoard: string;
  private currentSprints: string[];
  private currentView: string;
  private urlMap: object = {
    'money': 'estimate-cost',
    'summary': 'summary',
    'time': 'estimate-hours'
  };
  private chartAll: boolean = false;

  constructor(private router: Router, private dashboardService: DashboardService) {
  }

  ngOnInit() {
    this.dashboardService.currentBoard.subscribe(board => {
      this.currentBoard = board;
    });

    this.dashboardService.currentSprints.subscribe((sprints: string[]) => {
      this.currentSprints = sprints;
    });

    this.dashboardService.chartAllSprints.subscribe((chartAll: boolean) => this.chartAll = chartAll);

    this.dashboardService.currentView.subscribe((view: string) => {
     this.currentView = view;
    });
  }

  viewSelected(view:string) {
    if(this.currentView === 'estimate-cost' || this.currentView === 'point-cost') {
      return view === 'money';
    }
    if(this.currentView === 'estimate-hours' || this.currentView === 'point-hours') {
      return view === 'time';
    }
    return this.currentView === view;
  }

  selectView(view: string) {
    this.currentView = view;

    this.router.navigate([`/dashboard/analysis/${this.urlMap[view]}/`], {
      queryParams: {
        board: this.currentBoard,
        sprints: this.currentSprints,
        allSprints: this.chartAll
      }
    });
  }
}