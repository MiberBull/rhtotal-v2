import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { AboutPage } from './about.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: AboutPage }]),
  ],
  declarations: [AboutPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AboutPageModule {}
