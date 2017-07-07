import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { BehaviorSubject } from "rxjs/BehaviorSubject";
import { JiraBoard, JiraSprint } from './jira.model';
import { JiraSprintSummary, JiraWorklogSummary, JiraSprintPointAnalysis } from "./jira.model";
import { each, join } from "lodash";

@Injectable()
export class JiraService {
  private _currentSummary:BehaviorSubject<JiraSprintSummary[]> = new BehaviorSubject(null);
  private _currentWorklogSummary:BehaviorSubject<JiraWorklogSummary[]> = new BehaviorSubject(null);
  private _currentPointAnalysis:BehaviorSubject<JiraSprintPointAnalysis[]> = new BehaviorSubject(null);

  public currentSummary:Observable<JiraSprintSummary[]> = this._currentSummary.asObservable();
  public currentWorklogSummary:Observable<JiraWorklogSummary[]> = this._currentWorklogSummary.asObservable();
  public currentPointAnalysis:Observable<JiraSprintPointAnalysis[]> = this._currentPointAnalysis.asObservable();

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

  getSingleSprintSummary(boardId:string, sprintId:string):Observable<JiraSprintSummary> {
    return this.http.get('/api/analysis/summary/' + boardId + '/' + sprintId)
      .map(res => res.json());
  }

  setCurrentSummary(boardId:string, sprintIds:string[]) {
      sprintIds = typeof sprintIds === 'string' ? [sprintIds] : sprintIds;
      this._currentSummary.next(null);

      let requests:Observable<JiraSprintSummary>[] = [];
      each(sprintIds, (sprintId) => {
        requests.push(this.http.get('/api/analysis/summary/worklogs/' + boardId + '/' + sprintId).map(res => res.json()));
      });

      Observable.forkJoin(requests).subscribe((results:JiraSprintSummary[]) => {
        results.sort((a:JiraSprintSummary, b: JiraSprintSummary) => {
          if(a.completeDate > b.completeDate) return 1;
          if(a.completeDate < b.completeDate) return -1;
          return 0;
        });
        this._currentSummary.next(results);
      });
  }

  setCurrentWorklogSummary(boardId:string, sprintIds:string[]) {
    if(!this._currentWorklogSummary.getValue()){
      this._currentWorklogSummary.next(null);

      this.http.get('/api/analysis/worklogs/' + boardId + '/' + join(sprintIds,','))
        .map(res => res.json())
        .subscribe((summary:JiraWorklogSummary[]) => this._currentWorklogSummary.next(summary));
    }
  }

  setCurrentPointAnalysisByBoard(boardId:string) {
    this._currentPointAnalysis.next(null);
    this.http.get('/api/analysis/point/' + boardId)
      .map(res => res.json())
      .subscribe((data:JiraSprintPointAnalysis[]) => this._currentPointAnalysis.next(data));
  }

  setCurrentPointAnalysisBySprints(boardId:string, sprintIds:string[]) {
    sprintIds = typeof sprintIds === 'string' ? [sprintIds] : sprintIds;
    this._currentPointAnalysis.next(null);

    let requests:Observable<JiraSprintPointAnalysis>[] = [];
    each(sprintIds, (sprintId) => {
      requests.push(this.http.get('/api/analysis/point/' + boardId + '/sprint/' + sprintId).map(res => res.json()));
    });

    Observable.forkJoin(requests).subscribe((results:JiraSprintPointAnalysis[]) => {
      results.sort((a:JiraSprintPointAnalysis, b: JiraSprintPointAnalysis) => {
        if(a.endDate > b.endDate) return 1;
        if(a.endDate < b.endDate) return -1;
        return 0;
      });
      this._currentPointAnalysis.next(results);
    });
  }

}