
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { JiraService } from '../jira/jira.service';
import { JiraSprint } from '../jira/jira.model';
import {JiraSprintStats} from "../jira/jira.model";

@Injectable()
export class StatsResolveService implements Resolve<Observable<JiraSprintStats[]>> {

    constructor(private service: JiraService){}

    resolve(route: ActivatedRouteSnapshot): Observable<JiraSprintStats[]> {
        let boardId: String = route.params['boardId'];
        let startSprintCompleteDate: Date = route.params['start'];
        let endSprintCompleteDate: Date = route.params['end'];
        return this.service.getSprintStats(boardId, startSprintCompleteDate, endSprintCompleteDate);
    }
}
