import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { PaymentsPage } from './payments.page';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: PaymentsPage }]),
  ],
  declarations: [PaymentsPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PaymentsPageModule {}
