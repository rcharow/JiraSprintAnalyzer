import {Component, Input} from '@angular/core';

@Component({
  selector: 'analysis-message',
  templateUrl: '/app/analysis/analysis-message.component.html'
})

export class AnalysisMessageComponent {
  @Input() message:{title:string,body:string};
  @Input() iconClasses:string[];

}