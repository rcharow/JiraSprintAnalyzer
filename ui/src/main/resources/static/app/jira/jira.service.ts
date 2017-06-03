import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { BehaviorSubject } from "rxjs/BehaviorSubject";
import { JiraBoard, JiraSprint } from './jira.model';
import { JiraSprintSummary, JiraWorklogSummary } from "./jira.model";
import { each, join } from "lodash";

@Injectable()
export class JiraService {
  private _currentSummary:BehaviorSubject<JiraSprintSummary[]> = new BehaviorSubject(null);
  private _currentWorklogSummary:BehaviorSubject<JiraWorklogSummary[]> = new BehaviorSubject(null);

  public currentSummary:Observable<JiraSprintSummary[]> = this._currentSummary.asObservable();
  public currentWorklogSummary:Observable<JiraWorklogSummary[]> = this._currentWorklogSummary.asObservable();

  constructor(private http:Http) {
  }


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

  setCurrentSummary(sprintIds:string[]) {
      sprintIds = typeof sprintIds === 'string' ? [sprintIds] : sprintIds;
      this._currentSummary.next(null);

      let requests:Observable<JiraSprintSummary>[] = [];
      each(sprintIds, (sprintId) => {
        requests.push(this.http.get('/api/analysis/summary/worklogs/' + sprintId).map(res => res.json()));
      });

      Observable.forkJoin(requests).subscribe((results:JiraSprintSummary[]) => {
        this._currentSummary.next(results);
      });
  }

  setCurrentWorklogSummary(sprintIds:string[]) {
    if(!this._currentWorklogSummary.getValue()){
      this._currentWorklogSummary.next(null);

      this.http.get('/api/analysis/worklogs/' + join(sprintIds,','))
        .map(res => res.json())
        .subscribe((summary:JiraWorklogSummary[]) => this._currentWorklogSummary.next(summary));
    }
  }

}