import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { ComunicadosPage } from './comunicados.page';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: ComunicadosPage }]),
  ],
  declarations: [ComunicadosPage],
})
export class ComunicadosPageModule {}
