import { Component, OnInit } from '@angular/core';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import { DashboardService } from './dashboard.service';
import { split } from 'lodash';

@Component({
  selector: 'dashboard',
  templateUrl: '/app/dashboard/dashboard.component.html'
})

export class DashboardComponent {

  private loading:boolean = false;

  constructor(private router:Router, private dashboardService:DashboardService) {
  }

  ngOnInit() {
    //TODO: Should this happen in a higher-level component?
    //this.router.events.subscribe((event) => {
    //  if (split(event.url, '/')[1] === 'dashboard') {
    //    switch (event.constructor) {
    //      case NavigationStart:
    //        this.loading = true;
    //        this.dashboardService.setDashboardLoading(true);
    //        break;
    //      case NavigationEnd:
    //        this.loading = false;
    //        this.dashboardService.setDashboardLoading(false);
    //        break;
    //    }
    //  }
    //});

    this.dashboardService.isDashboardLoading.subscribe((loading:boolean) => {
      this.loading = loading;
    });
  }

}