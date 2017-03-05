import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {JiraSprintStats} from "../jira/jira.model";

@Component({
  selector: 'sprint-stats',
  templateUrl: '/app/dashboard/sprint-stats.component.html'
})

export class SprintStatsComponent implements OnInit {
  stats: JiraSprintStats[];
  constructor(private route: ActivatedRoute){}

  ngOnInit(){
    this.route.data.subscribe((data:{ stats: JiraSprintStats[] }) => {
      this.stats = data.stats;
    });
  }

}
