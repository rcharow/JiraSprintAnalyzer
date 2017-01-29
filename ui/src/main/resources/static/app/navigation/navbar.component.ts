import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { JiraService, JiraBoard } from '../jira/jira.service';

@Component({
    selector: 'navbar',
    templateUrl: '/app/navigation/navbar.component.html'
})

export class NavBarComponent{
    @Input() boards: JiraBoard[];
    @Output() selectBoard: EventEmitter<JiraBoard> = new EventEmitter<JiraBoard>();

    selectedBoard: JiraBoard;

    onBoardSelect() {
        this.selectBoard.emit(this.selectedBoard);
    }
}