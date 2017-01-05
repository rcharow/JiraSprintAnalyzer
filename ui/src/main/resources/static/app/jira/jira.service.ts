import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

export class JiraBoard {
    public id: String;
    public self: String;
    public name: String;
    public type: String;

}

@Injectable()
export class AnalysisOptionsService {
    constructor (private http: Http) {
        console.debug('AnalysisOptionsService initialized.')
    }

    getAnalysisOptions (): Observable<JiraBoard[]> {
        return this.http.get('/api/jira/board')
            .map(res => res.json())
    }
};