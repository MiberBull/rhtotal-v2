import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { InsuranceDetailPage } from './insurance-detail.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: InsuranceDetailPage }]),
  ],
  declarations: [InsuranceDetailPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class InsuranceDetailPageModule {}
