import {Component, Input} from '@angular/core';

@Component({
  selector: 'analysis-chart',
  templateUrl: '/app/dashboard/analysis/analysis-chart.component.html'
})

export class AnalysisChartComponent {
  @Input() loading:boolean;
  @Input() routerLinks:string[];
  @Input() chartType:string;
  @Input() chartData:any;
  @Input() chartOptions:object;
  @Input() view:string;
}