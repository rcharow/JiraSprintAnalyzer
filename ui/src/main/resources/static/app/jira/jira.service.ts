import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

export class JiraBoard {
    public id: String;
    public self: String;
    public name: String;
    public type: String;

}

export class JiraSprint {
    public id: String;
    public self: String;
    public state: String;
    public name: String;
    public startDate: Date;
    public endDate: Date;
    public completeDate: Date;
    public originBoardId: String;
}

@Injectable()
export class JiraService {
    constructor (private http: Http) {
        console.debug('JiraService initialized.')
    }

    getBoards (): Observable<JiraBoard[]> {
        return this.http.get('/api/jira/board')
            .map(res => res.json())
    }

    getSprintsByBoardId (boardId: String): Observable<JiraSprint[]> {
        return this.http.get('/api/jira/board/' + boardId + '/sprints')
            .map(res => res.json());
    }

};