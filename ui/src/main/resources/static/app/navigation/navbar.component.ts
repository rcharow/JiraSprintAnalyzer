import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { JiraService, JiraBoard } from '../jira/jira.service';
import {AuthService} from "../shared/auth.service";

@Component({
    selector: 'navbar',
    templateUrl: '/app/navigation/navbar.component.html',
    providers: [
      AuthService
    ]
})

export class NavBarComponent{
    @Input() boards: JiraBoard[];
    @Output() selectBoard: EventEmitter<JiraBoard> = new EventEmitter<JiraBoard>();

    selectedBoard: JiraBoard;

    constructor(private authService:AuthService) {

    }

    onBoardSelect() {
        this.selectBoard.emit(this.selectedBoard);
    }

    logout() {
        this.authService.logout().subscribe(result => {
            let test = result;
        });
    }


}