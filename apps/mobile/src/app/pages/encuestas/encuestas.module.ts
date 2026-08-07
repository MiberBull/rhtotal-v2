import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { EncuestasPage } from './encuestas.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: EncuestasPage }]),
  ],
  declarations: [EncuestasPage],
})
export class EncuestasPageModule {}
