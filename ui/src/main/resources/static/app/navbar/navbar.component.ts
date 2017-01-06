import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { JiraService, JiraBoard } from "../jira/jira.service";
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'navbar',
    templateUrl: '/app/navbar/navbar.component.html'
})
export class NavBarComponent{
    @Input() boards: JiraBoard[];
    @Output() selectBoard: EventEmitter<JiraBoard> = new EventEmitter<JiraBoard>();

    private selectedBoard: JiraBoard;

    constructor() { }

    ngOnInit() {

    }

    onBoardSelect() {
        this.selectBoard.emit(this.selectedBoard);
    }
}