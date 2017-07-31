import { Component } from '@angular/core';
import { DashboardService } from './dashboard.service';

@Component({
  selector: 'dashboard',
  templateUrl: '/app/dashboard/dashboard.component.html'
})

export class DashboardComponent {

  private loading:boolean = false;

  constructor(private dashboardService:DashboardService) {
  }

  ngOnInit() {
    this.dashboardService.isDashboardLoading.subscribe((loading:boolean) => {
      this.loading = loading;
    });
  }

}