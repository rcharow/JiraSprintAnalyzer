import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class AuthService {
  private authenticated:Boolean;

  constructor(private http:Http) {
    this.authenticated = false;
  }

  login():Observable<Response> {

    let headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa('email:password'));

    return this.http.get('/api/user', {headers: headers})
      .map(res => {
        this.authenticated = true;
        return res.json()
      })
      .catch(err => {
        console.log('Error logging in.');
        this.authenticated = false;
        return Observable.throw(err.toString());
      })
  }

  logout():Observable<Response> {
    return this.http.post('/logout', {}, {})
      .map(res => {
        this.authenticated = false;
        return res.json();
      })
      .catch(err => {
        console.log('Error logging out.');
        return Observable.throw(err.toString());
      })
  }



  isAuthenticated():Boolean {
    return this.authenticated;
  }
}
;