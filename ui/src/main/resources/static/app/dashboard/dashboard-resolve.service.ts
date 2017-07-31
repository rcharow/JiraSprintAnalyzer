
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { DashboardService } from './dashboard.service';

@Injectable()
export class DashboardResolveService implements Resolve<boolean> {

    constructor(private service: DashboardService){}

    resolve(route: ActivatedRouteSnapshot): boolean {
        this.service.setCurrentView(route.url[0].path);
        return true;
    }
}
