import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { JiraBoard, JiraSprint } from './jira.model';
import { JiraSprintSummary } from "./jira.model";

@Injectable()
export class JiraService {
  constructor(private http:Http) {}

  getBoards():Observable<JiraBoard[]> {
    return this.http.get('/api/jira/board')
      .map(res => res.json())
  }

  getScrumBoards():Observable<JiraBoard[]> {
    return this.http.get('/api/jira/board/type/scrum')
      .map(res => res.json())
  }

  getClosedSprintsByBoardId(boardId:String):Observable<JiraSprint[]> {
    return this.http.get('/api/jira/board/' + boardId + '/sprints/closed')
      .map(res => res.json());
  }

  getSingleSprintSummary(sprintId:String):Observable<JiraSprintSummary> {
    return this.http.get('/api/analysis/summary/' + sprintId)
      .map(res => res.json());
  }

}