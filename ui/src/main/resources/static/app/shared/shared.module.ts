
import { NgModule } from '@angular/core';
import { CommonModule as AngularCommon } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
    declarations: [],
    imports: [AngularCommon, ReactiveFormsModule],
    exports: [ AngularCommon, ReactiveFormsModule, FormsModule ]
})
export class SharedModule { }
