import { NgModule } from '@angular/core';
import { CommonModule as AngularCommon } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from "./auth.service";
import { LoadingComponent } from "./widgets/loading.component";

@NgModule({
  declarations: [
    LoadingComponent
  ],
  imports: [
    AngularCommon,
    ReactiveFormsModule
  ],
  exports: [
    AngularCommon,
    ReactiveFormsModule,
    FormsModule,
    LoadingComponent
  ],
  providers: [
    AuthService
  ]
})
export class SharedModule {
}
