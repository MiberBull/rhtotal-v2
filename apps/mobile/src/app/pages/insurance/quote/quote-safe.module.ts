import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { QuoteSafePage } from './quote-safe.page';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: QuoteSafePage }]),
  ],
  declarations: [QuoteSafePage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class QuoteSafePageModule {}
