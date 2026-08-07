import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { BibliotecaPage } from './biblioteca.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: BibliotecaPage }]),
  ],
  declarations: [BibliotecaPage],
})
export class BibliotecaPageModule {}
