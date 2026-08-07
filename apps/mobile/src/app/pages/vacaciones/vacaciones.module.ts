import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { VacacionesPage } from './vacaciones.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: VacacionesPage }]),
  ],
  declarations: [VacacionesPage],
})
export class VacacionesPageModule {}
