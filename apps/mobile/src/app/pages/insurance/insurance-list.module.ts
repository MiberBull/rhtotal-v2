import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { InsuranceListPage } from './insurance-list.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: InsuranceListPage }]),
  ],
  declarations: [InsuranceListPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class InsuranceListPageModule {}
