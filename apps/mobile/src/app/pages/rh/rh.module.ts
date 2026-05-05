import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { RhPage } from './rh.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: RhPage }]),
  ],
  declarations: [RhPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class RhPageModule {}
