import {Component, Input} from '@angular/core';

@Component({
  selector: 'analysis-chart',
  templateUrl: '/app/analysis/analysis-chart.component.html'
})

export class AnalysisChartComponent {
  @Input() loading:boolean;
  @Input() routeLinks:string[];
  @Input() chartType:string;
  @Input() chartData:any;
  @Input() chartOptions:object;
}