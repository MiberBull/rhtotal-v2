import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { PositionPage } from './position.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: PositionPage }]),
  ],
  declarations: [PositionPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PositionPageModule {}
