import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { MyCvPage } from './my-cv.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: MyCvPage }]),
  ],
  declarations: [MyCvPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class MyCvPageModule {}
