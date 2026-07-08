import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { HrEmployeeProvider } from '../../providers/hr-employee/hr-employee';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Component({
  selector: 'page-mis-vacaciones',
  templateUrl: 'mis-vacaciones.html',
})
export class MisVacacionesPage {
  balance: any = null;
  requests: any[] = [];
  employeeId: number;
  showForm = false;
  form = { dtStartDate: '', dtEndDate: '', dsNotes: '' };

  constructor(
    public navCtrl: NavController,
    private hr: HrEmployeeProvider,
    private storage: StorageProvider,
    private events: EventsManagerProvider
  ) {
    const user = this.storage.getItem(KEYS_STORAGE.USER);
    this.employeeId = user ? user.idEmployee || user.id : null;
  }

  ionViewDidLoad() {
    this.load();
  }

  load() {
    if (!this.employeeId) return;
    this.events.setIsLoadingEvent(true);
    this.hr.getBalance(this.employeeId).subscribe(
      (data: any) => {
        this.balance = data;
      },
      () => {}
    );
    this.hr.getMyRequests(this.employeeId).subscribe(
      (data: any) => {
        this.requests = Array.isArray(data) ? data : data.data || [];
        this.events.setIsLoadingEvent(false);
      },
      () => this.events.setIsLoadingEvent(false)
    );
  }

  solicitarVacaciones() {
    if (!this.form.dtStartDate || !this.form.dtEndDate) {
      this.showAlert('', 'Ingresa las fechas de inicio y fin.');
      return;
    }
    const body = {
      idEmployee: this.employeeId,
      dtStartDate: this.form.dtStartDate,
      dtEndDate: this.form.dtEndDate,
      dsNotes: this.form.dsNotes,
    };
    this.events.setIsLoadingEvent(true);
    this.hr.requestVacation(body).subscribe(
      () => {
        this.showForm = false;
        this.form = { dtStartDate: '', dtEndDate: '', dsNotes: '' };
        this.load();
      },
      () => {
        this.events.setIsLoadingEvent(false);
        this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
      }
    );
  }

  statusClass(status: string) {
    const map = {
      PENDIENTE: 'badge-pendiente',
      APROBADA: 'badge-aprobada',
      RECHAZADA: 'badge-rechazada',
    };
    return map[status] || 'badge-pendiente';
  }

  showAlert(title: string, msg: string) {
    const objMessage: MessageGeneral = { msg, title };
    this.events.setGeneralNotificationMessage(objMessage);
  }

  back() {
    this.navCtrl.pop();
  }
}
