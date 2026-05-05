import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { BenefitsCategoryPage } from './benefits-category.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: BenefitsCategoryPage }]),
  ],
  declarations: [BenefitsCategoryPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class BenefitsCategoryPageModule {}
