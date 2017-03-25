import { Injectable } from '@angular/core';
import { JiraBoard } from "../jira/jira.model";
import { JiraSprint } from "../jira/jira.model";
import { BehaviorSubject } from "rxjs/BehaviorSubject";
import { Observable } from "rxjs/Observable";

@Injectable()
export class DashboardService {
  private _currentBoard: BehaviorSubject<JiraBoard> = new BehaviorSubject(null);
  private _startSprint: BehaviorSubject<JiraSprint> = new BehaviorSubject(null);
  private _endSprint: BehaviorSubject<JiraSprint> = new BehaviorSubject(null);
  private _isMultiSprint: BehaviorSubject<boolean> = new BehaviorSubject(false);
  private _isDashboardLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);

  public currentBoard: Observable<JiraBoard> = this._currentBoard.asObservable();
  public startSprint: Observable<JiraSprint> = this._startSprint.asObservable();
  public endSprint: Observable<JiraSprint> = this._endSprint.asObservable();
  public isMultiSprint: Observable<boolean> = this._isMultiSprint.asObservable();
  public isDashboardLoading: Observable<boolean> = this._isDashboardLoading.asObservable();

  setCurrentBoard(board:JiraBoard) {
    this._currentBoard.next(board);
  }

  setStartSprint(sprint:JiraSprint) {
    this._startSprint.next(sprint);
  }

  setEndSprint(sprint:JiraSprint) {
    this._endSprint.next(sprint);
  }

  setMultiSprint(isMultSprint:boolean) {
    this._isMultiSprint.next(isMultSprint);
  }

  setDashboardLoading(loading:boolean) {
    this._isDashboardLoading.next(loading);
  }

}