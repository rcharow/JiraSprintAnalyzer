import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {JiraSprintSummary} from "../jira/jira.model";

@Component({
  selector: 'sprint-stats',
  templateUrl: '/app/dashboard/sprint-summary.component.html'
})

export class SprintSummaryComponent implements OnInit {
  summary: JiraSprintSummary;
  constructor(private route: ActivatedRoute){}

  ngOnInit(){
    this.route.data.subscribe((data:{ summary: JiraSprintSummary }) => {
      this.summary = data.summary;
    });
  }

}
