
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { JiraService } from '../jira/jira.service';
import { JiraSprint } from '../jira/jira.model';
import {JiraSprintSummary} from "../jira/jira.model";

@Injectable()
export class StatsResolveService implements Resolve<boolean> {

    constructor(private service: JiraService){}

    resolve(route: ActivatedRouteSnapshot): boolean {
        let sprints = route.queryParams['sprints'];

        this.service.setCurrentSummary(sprints);
        //this.service.setCurrentWorklogSummary(sprints);

        return true;
    }
}
