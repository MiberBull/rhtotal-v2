import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NotificationsPage } from './notifications.page';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    RouterModule.forChild([{ path: '', component: NotificationsPage }]),
  ],
  declarations: [NotificationsPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class NotificationsPageModule {}
