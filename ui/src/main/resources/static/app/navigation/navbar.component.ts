import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AuthService } from "../shared/auth.service";

@Component({
  selector: 'navbar',
  templateUrl: '/app/navigation/navbar.component.html',
  providers: [
    AuthService
  ]
})

export class NavBarComponent {

  constructor(private authService:AuthService) {

  }

  logout() {
    this.authService.logout().subscribe(result => {
      let test = result;
    });
  }


}