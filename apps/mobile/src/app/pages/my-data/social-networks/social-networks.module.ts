import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { SocialNetworksPage } from './social-networks.page';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: SocialNetworksPage }]),
  ],
  declarations: [SocialNetworksPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SocialNetworksPageModule {}
