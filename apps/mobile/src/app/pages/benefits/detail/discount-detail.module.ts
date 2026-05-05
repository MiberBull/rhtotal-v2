import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { DiscountDetailPage } from './discount-detail.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: DiscountDetailPage }]),
  ],
  declarations: [DiscountDetailPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DiscountDetailPageModule {}
