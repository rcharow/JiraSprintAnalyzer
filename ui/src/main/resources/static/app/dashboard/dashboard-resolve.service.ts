
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { JiraService, JiraSprint} from '../jira/jira.service';

@Injectable()
export class DashboardResolveService implements Resolve<Observable<JiraSprint[]>> {

    constructor(private service: JiraService){}

    resolve(route: ActivatedRouteSnapshot): Observable<JiraSprint[]> {
        let boardId: String = route.params['boardId'];
        return this.service.getSprintsByBoardId(boardId);
    }
}