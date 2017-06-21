import {Component, OnInit} from '@angular/core';
import {JiraBoard, JiraSprintPointAnalysis} from "../../../jira/jira.model";
import {JiraService} from "../../../jira/jira.service";
import {forIn, each} from "lodash";


@Component({
  selector: 'money',
  templateUrl: '/app/dashboard/analysis/money/money.component.html'
})

export class MoneyComponent {
  board: JiraBoard[] = null;
  analysis: JiraSprintPointAnalysis[];
  loading: Boolean;
  type: string = 'line';
  chartData:object = {};
  chartColors: string[] = ['#F19F4D', '#4484CE', '#344D68', '#9FD356', '#034748', '#3C1518', '#A14EBF', '#A63D40', '#EE6C4D'];
  chartOptions:object = {
    responsive: true,
    title: {
      display: true,
      text: 'Dollars Spent by Point Estimate',
      position: 'top'
    },
    scales: {
      yAxes: [{
        ticks: {
          callback(value:number){
            return '$' + value;
          }
        }
      }]
    },
    legend: {
      position: 'bottom'
    },
    tooltips: {
      callbacks: {
        label(tooltipItem:any, data:any){
          return '$' + tooltipItem.yLabel.toFixed(2);
        }
      }
    }
  };

  constructor(private jiraService: JiraService) {
  }

  ngOnInit() {
    this.loading = true;

    this.jiraService.currentPointAnalysis.subscribe((analysis: JiraSprintPointAnalysis[]) => {
      this.analysis = analysis;
      this.loading = !analysis;
      this.formatData(analysis);
    });
  }

  formatData(data: JiraSprintPointAnalysis[]) {
    let pointValues = {};
    let labels: string[] = [];
    each(data, sprint => {
      labels.push(sprint.sprintName);
      each(sprint.pointAnalysis, analysis => {
        if (!pointValues[analysis.pointValue]) {
          pointValues[analysis.pointValue] = [{x: sprint.sprintName, y: analysis.avgDollarsSpent}];
        } else {
          pointValues[analysis.pointValue].push({x: sprint.sprintName, y: analysis.avgDollarsSpent});
        }
      });
    });

    let datasets: Object[] = [];
    let idx: number = 0;
    forIn(pointValues, (value, key) => {
      let color = idx < this.chartColors.length ? this.chartColors[idx] : null;
      datasets.push({
        label: key,
        data: value,
        fill: false,
        borderColor: color,
        backgroundColor: color
      });
      idx++;
    });

    this.chartData = {
      labels: labels,
      datasets: datasets

    }
  }
}

// let datasets = map(data, sprint => {
//   let data = map(sprint.pointAnaylis, analysis => {
//     return {
//       x: analysis.pointValue,
//       y: analysis.avgDollars
//     }
//   });
//
//   return {
//     label: sprint.sprintName,
//     data: data
//   };
// });