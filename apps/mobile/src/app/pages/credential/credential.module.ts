import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { CredentialPage } from './credential.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: CredentialPage }]),
  ],
  declarations: [CredentialPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class CredentialPageModule {}
