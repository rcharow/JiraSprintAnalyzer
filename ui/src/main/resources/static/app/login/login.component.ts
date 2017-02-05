import { Component } from '@angular/core';
import {AuthService} from "../shared/auth.service";
import {JiraService} from "../jira/jira.service";


@Component({
  selector: 'login',
  templateUrl: '/app/login/login.component.html',
  providers: [
    AuthService
  ]
})

export class LoginComponent {
  constructor (private authService: AuthService, private jiraService: JiraService) {

  }

  authenticate () {
    this.authService.login()
      .subscribe(result => {
      let test = result;
    });
  }
}