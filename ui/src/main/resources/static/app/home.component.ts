import { Component } from '@angular/core';

@Component({
    selector: 'home',
    templateUrl: '/app/home.component.html'
})
export class HomeComponent{
    constructor() {
        console.debug('Home component loaded!');
    };

}