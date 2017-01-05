import { Component, OnInit, Input } from '@angular/core';
import { JiraService, JiraBoard } from "../jira/jira.service";

@Component({
    selector: 'navbar',
    templateUrl: '/app/navbar/navbar.component.html'
})
export class NavBarComponent{
    @Input() boards: JiraBoard[];

    constructor() { }

    ngOnInit() {

    }
}