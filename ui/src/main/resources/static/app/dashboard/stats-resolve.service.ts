
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { JiraService } from '../jira/jira.service';
import { JiraSprint } from '../jira/jira.model';
import {JiraSprintSummary} from "../jira/jira.model";

@Injectable()
export class StatsResolveService implements Resolve<Observable<JiraSprintSummary[]>> {

    constructor(private service: JiraService){}

    resolve(route: ActivatedRouteSnapshot): void {
        let sprintId: String = route.params['sprintId'];
        let startSprintCompleteDate: Date = route.params['start'];
        let endSprintCompleteDate: Date = route.params['end'];

        this.service.setCurrentSummary(sprintId);
        this.service.setCurrentWorklogSummary(sprintId);
    }
}
