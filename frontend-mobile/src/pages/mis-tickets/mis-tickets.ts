import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { HrEmployeeProvider } from '../../providers/hr-employee/hr-employee';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Component({
  selector: 'page-mis-tickets',
  templateUrl: 'mis-tickets.html',
})
export class MisTicketsPage {
  tickets: any[] = [];
  selectedTicket: any = null;
  comments: any[] = [];
  employeeId: number;
  userEmail: string;
  showForm = false;
  newComment = '';
  form = {
    dsSubject: '',
    dsCategory: 'GENERAL',
    dsPriority: 'NORMAL',
    dsDescription: '',
  };

  categories = ['GENERAL', 'NOMINA', 'PRESTACIONES', 'ACOSO', 'OTRO'];
  priorities = ['BAJA', 'NORMAL', 'ALTA', 'URGENTE'];

  constructor(
    public navCtrl: NavController,
    private hr: HrEmployeeProvider,
    private storage: StorageProvider,
    private events: EventsManagerProvider
  ) {
    const user = this.storage.getItem(KEYS_STORAGE.USER);
    this.employeeId = user ? user.idEmployee || user.id : null;
    this.userEmail = user ? user.email : 'employee';
  }

  ionViewDidLoad() {
    this.loadTickets();
  }

  loadTickets() {
    if (!this.employeeId) return;
    this.events.setIsLoadingEvent(true);
    this.hr.getMyTickets(this.employeeId).subscribe(
      (data: any) => {
        this.tickets = Array.isArray(data) ? data : data.data || [];
        this.events.setIsLoadingEvent(false);
      },
      () => this.events.setIsLoadingEvent(false)
    );
  }

  selectTicket(ticket: any) {
    this.selectedTicket = ticket;
    this.comments = [];
    this.hr.getComments(ticket.id).subscribe(
      (data: any) => {
        this.comments = Array.isArray(data) ? data : data.data || [];
      },
      () => {}
    );
  }

  sendComment() {
    if (!this.newComment.trim()) return;
    const comment = {
      idTicket: this.selectedTicket.id,
      dsComment: this.newComment,
      dsCreatedBy: this.userEmail,
    };
    this.hr.addComment(comment).subscribe(
      () => {
        this.newComment = '';
        this.hr.getComments(this.selectedTicket.id).subscribe((data: any) => {
          this.comments = Array.isArray(data) ? data : data.data || [];
        });
      },
      () => this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE)
    );
  }

  crearTicket() {
    if (!this.form.dsSubject || !this.form.dsDescription) {
      this.showAlert('', MSG_DIALOG.REQUIRED);
      return;
    }
    const body = {
      ...this.form,
      idEmployee: this.employeeId,
      dsStatus: 'ABIERTO',
    };
    this.events.setIsLoadingEvent(true);
    this.hr.createTicket(body).subscribe(
      () => {
        this.showForm = false;
        this.form = {
          dsSubject: '',
          dsCategory: 'GENERAL',
          dsPriority: 'NORMAL',
          dsDescription: '',
        };
        this.loadTickets();
      },
      () => {
        this.events.setIsLoadingEvent(false);
        this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
      }
    );
  }

  statusClass(s: string) {
    const map = {
      ABIERTO: 'badge-abierto',
      EN_PROCESO: 'badge-proceso',
      CERRADO: 'badge-cerrado',
    };
    return map[s] || 'badge-abierto';
  }

  priorityClass(p: string) {
    const map = {
      BAJA: 'priority-low',
      NORMAL: 'priority-normal',
      ALTA: 'priority-high',
      URGENTE: 'priority-urgent',
    };
    return map[p] || 'priority-normal';
  }

  showAlert(title: string, msg: string) {
    const objMessage: MessageGeneral = { msg, title };
    this.events.setGeneralNotificationMessage(objMessage);
  }

  back() {
    if (this.selectedTicket) {
      this.selectedTicket = null;
      return;
    }
    this.navCtrl.pop();
  }
}
