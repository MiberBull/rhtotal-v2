import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { PersonalInfoPage } from './personal-info.page';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: PersonalInfoPage }]),
  ],
  declarations: [PersonalInfoPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PersonalInfoPageModule {}
