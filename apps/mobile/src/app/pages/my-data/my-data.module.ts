import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { MyDataPage } from './my-data.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: MyDataPage }]),
  ],
  declarations: [MyDataPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class MyDataPageModule {}
