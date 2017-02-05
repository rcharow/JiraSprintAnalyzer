import { Component } from '@angular/core';
import { JiraService, JiraBoard } from "./jira/jira.service";

@Component({
    selector: 'home',
    templateUrl: '/app/home.component.html',
    providers: [ JiraService ]
})
export class HomeComponent{
    boards: JiraBoard[] = [];
    selectedBoard: JiraBoard = new JiraBoard();

    constructor(private jiraService: JiraService) {
        console.debug('Home component loaded!');
    };

    ngOnInit() {
        this.jiraService.getBoards()
            .subscribe(boards => {
                this.boards = boards;
            });
    }

    onBoardSelect(board: JiraBoard) {
        this.selectedBoard = board;
    }

}