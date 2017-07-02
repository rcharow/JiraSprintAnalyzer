import {Injectable} from '@angular/core';
import {JiraBoard} from "../jira/jira.model";
import {JiraSprint} from "../jira/jira.model";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/Observable";
import {difference} from "lodash";

@Injectable()
export class DashboardService {
  private _currentBoard: BehaviorSubject<JiraBoard> = new BehaviorSubject(null);
  private _currentSprints: BehaviorSubject<Array<string>> = new BehaviorSubject([]);
  private _isDashboardLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  private _currentView: BehaviorSubject<string> = new BehaviorSubject('summary');
  private _chartAllSprints: BehaviorSubject<boolean> = new BehaviorSubject(false);

  public currentBoard: Observable<JiraBoard> = this._currentBoard.asObservable();
  public currentSprints: Observable<Array<string>> = this._currentSprints.asObservable();
  public isDashboardLoading: Observable<boolean> = this._isDashboardLoading.asObservable();
  public currentView: Observable<string> = this._currentView.asObservable();
  public chartAllSprints: Observable<boolean> = this._chartAllSprints.asObservable();

  setCurrentBoard(board: JiraBoard) {
    this._currentBoard.next(board);
  }

  setCurrentSprints(sprintIds: Array<string>) {
    //TODO: Do I need to convert to array here?
    this._currentSprints.next(sprintIds);
  }

  setDashboardLoading(loading: boolean) {
    this._isDashboardLoading.next(loading);
  }

  setCurrenView(view: string) {
    this._currentView.next(view);
  }

  setChartAll(chartAll: boolean) {
    this._chartAllSprints.next(chartAll);
  }

}