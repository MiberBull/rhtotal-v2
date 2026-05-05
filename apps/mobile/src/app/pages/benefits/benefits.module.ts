import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { BenefitsPage } from './benefits.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: BenefitsPage }]),
  ],
  declarations: [BenefitsPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class BenefitsPageModule {}
