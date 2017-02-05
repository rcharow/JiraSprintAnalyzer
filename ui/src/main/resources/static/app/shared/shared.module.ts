import { NgModule } from '@angular/core';
import { CommonModule as AngularCommon } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {AuthService} from "./auth.service";

@NgModule({
  declarations: [],
  imports: [
    AngularCommon,
    ReactiveFormsModule
  ],
  exports: [
    AngularCommon,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    AuthService
  ]
})
export class SharedModule {
}
