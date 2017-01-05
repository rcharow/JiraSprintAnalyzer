import { Component, OnInit } from '@angular/core';
import { AnalysisOptionsService, JiraBoard } from "../jira/jira.service";

@Component({
    selector: 'navbar',
    templateUrl: '/app/navbar/navbar.component.html',
    providers: [ AnalysisOptionsService ]
})
export class NavBarComponent{
    analysisOptions: JiraBoard[];
    constructor(private optionsService: AnalysisOptionsService) { }

    ngOnInit() {
        this.optionsService.getAnalysisOptions()
            .subscribe(options => {
                this.analysisOptions = options;
            });
    }
}