import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { JiraBoard, JiraSprint } from './jira.model';

@Injectable()
export class JiraService {
    constructor (private http: Http) {
        console.debug('JiraService initialized.')
    }

    getBoards (): Observable<JiraBoard[]> {
        return this.http.get('/api/jira/board')
            .map(res => res.json())
    }

    getScrumBoards (): Observable<JiraBoard[]> {
        return this.http.get('/api/jira/board/type/scrum')
          .map(res => res.json())
    }

    getSprintsByBoardId (boardId: String): Observable<JiraSprint[]> {
        return this.http.get('/api/jira/board/' + boardId + '/sprints')
            .map(res => res.json());
    }

};