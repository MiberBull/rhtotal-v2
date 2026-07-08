import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { AttendanceProvider } from '../../providers/attendance/attendance';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Component({
  selector: 'page-asistencia',
  templateUrl: 'asistencia.html',
})
export class AsistenciaPage {
  todayRecord: any = null;
  employeeId: number;
  geoLoading = false;

  constructor(
    public navCtrl: NavController,
    private attendance: AttendanceProvider,
    private storage: StorageProvider,
    private events: EventsManagerProvider
  ) {
    const user = this.storage.getItem(KEYS_STORAGE.USER);
    this.employeeId = user ? user.idEmployee || user.id : null;
  }

  ionViewDidLoad() {
    this.loadToday();
  }

  loadToday() {
    if (!this.employeeId) return;
    this.events.setIsLoadingEvent(true);
    this.attendance.getToday(this.employeeId).subscribe(
      (data: any) => {
        this.todayRecord = data;
        this.events.setIsLoadingEvent(false);
      },
      () => {
        this.events.setIsLoadingEvent(false);
      }
    );
  }

  doCheckIn() {
    this.geoLoading = true;
    navigator.geolocation.getCurrentPosition(
      (pos) => {
        this.attendance
          .checkIn(this.employeeId, pos.coords.latitude, pos.coords.longitude)
          .subscribe(
            (data: any) => {
              this.todayRecord = data;
              this.geoLoading = false;
            },
            () => {
              this.geoLoading = false;
              this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
            }
          );
      },
      () => {
        this.geoLoading = false;
        this.showAlert(
          MSG_DIALOG.ERROR_TITLE,
          'No se pudo obtener tu ubicación. Verifica los permisos de GPS.'
        );
      },
      { enableHighAccuracy: true, timeout: 10000 }
    );
  }

  doCheckOut() {
    this.geoLoading = true;
    navigator.geolocation.getCurrentPosition(
      (pos) => {
        this.attendance
          .checkOut(this.employeeId, pos.coords.latitude, pos.coords.longitude)
          .subscribe(
            (data: any) => {
              this.todayRecord = data;
              this.geoLoading = false;
            },
            () => {
              this.geoLoading = false;
              this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
            }
          );
      },
      () => {
        this.geoLoading = false;
        this.showAlert(
          MSG_DIALOG.ERROR_TITLE,
          'No se pudo obtener tu ubicación. Verifica los permisos de GPS.'
        );
      },
      { enableHighAccuracy: true, timeout: 10000 }
    );
  }

  get canCheckIn(): boolean {
    return !this.geoLoading && (!this.todayRecord || !this.todayRecord.dtCheckIn);
  }

  get canCheckOut(): boolean {
    return (
      !this.geoLoading &&
      !!this.todayRecord &&
      !!this.todayRecord.dtCheckIn &&
      !this.todayRecord.dtCheckOut
    );
  }

  showAlert(title: string, msg: string) {
    const objMessage: MessageGeneral = { msg, title };
    this.events.setGeneralNotificationMessage(objMessage);
  }

  back() {
    this.navCtrl.pop();
  }
}
