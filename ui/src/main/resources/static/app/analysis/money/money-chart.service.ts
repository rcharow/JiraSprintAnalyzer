import {Injectable} from '@angular/core';
import {JiraSprintPointAnalysis} from '../../jira/jira.model';
import {each, forIn} from 'lodash';

@Injectable()
export class MoneyChartService {
  getDollarsByPointEstimateOptions(): object {
    return {
      responsive: true,
      title: {
        display: true,
        text: 'Client Dollars Spent by Completed Point Estimate',
        position: 'top',
        fontSize: 16
      },
      scales: {
        yAxes: [{
          ticks: {
            callback(value: number){
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
          label(tooltipItem: any, data: any){
            return '$' + tooltipItem.yLabel.toFixed(2);
          }
        }
      }
    };
  }

  getAveragePointCostOptions(): object {
    return {
      responsive: true,
      title: {
        display: true,
        text: 'Average Client Dollars Spent per Completed Point',
        position: 'top',
        fontSize: 16
      },
      scales: {
        yAxes: [{
          type: 'linear',
          display: true,
          position: 'left',
          id: 'y-axis-avg',
          ticks: {
            callback(value: number){
              return '$' + value.toFixed(2);
            }
          }
        },
          {
            type: 'linear',
            display: true,
            position: 'right',
            id: 'y-axis-total',
            ticks: {
              callback(value: number){
                return '$' + value.toFixed(2);
              }
            }
          }
        ]
      },
      legend: {
        display: true,
        position: 'bottom'
      },
      tooltips: {
        callbacks: {
          label(tooltipItem: any, data: any){
            return '$' + tooltipItem.yLabel.toFixed(2);
          }
        }
      }
    }
  }

  getChartColors(): string[] {
    return ['#F19F4D', '#4484CE', '#344D68', '#9FD356', '#034748', '#3C1518', '#A14EBF', '#A63D40', '#EE6C4D'];
  }

  formatEstimateCostData(data: JiraSprintPointAnalysis[]): { labels: string[], datasets: object[] } {
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

    let datasets: object[] = [];
    let idx: number = 0;
    forIn(pointValues, (value, key) => {
      let color = idx < this.getChartColors().length ? this.getChartColors()[idx] : null;
      datasets.push({
        label: key,
        data: value,
        fill: false,
        borderColor: color,
        backgroundColor: color
      });
      idx++;
    });

    return {
      labels: labels,
      datasets: datasets
    }
  }

  formatPointCostData(data: JiraSprintPointAnalysis[]): { labels: string[], datasets: object[] } {
    let labels: string[] = [];
    {
      let avgDataset: number[] = [];
      let totalDataset: number[] = [];

      each(data, sprint => {
        labels.push(sprint.sprintName);
        avgDataset.push(sprint.pointAverages.averageDollarsPerPoint);
        totalDataset.push(sprint.pointAverages.totalDollars);
      });

      return {
        labels: labels,
        datasets: [
          {
            label: 'Average Cost per Point',
            data: avgDataset,
            backgroundColor: 'rgba(68, 132, 206, 0.5)',
            borderColor: '#4484CE',
            yAxisID: 'y-axis-avg'
          },
          {
            label: 'Total Client Cost',
            data: totalDataset,
            type: 'line',
            yAxisID: 'y-axis-total'
          }
        ]
      }
    }
  }
}