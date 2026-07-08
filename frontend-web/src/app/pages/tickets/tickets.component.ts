import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { Router } from '@angular/router';
import { TicketService } from '../../services/ticket/ticket.service';
import { DataService } from '../../services/data.service';
import { BREADCRUMB } from '../../../environments/environment';
import { TicketTO, TicketCommentTO } from '../../models/hr.model';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css'],
})
export class TicketsComponent implements OnInit {
  openTickets: TicketTO[] = [];
  inProgressTickets: TicketTO[] = [];
  closedTickets: TicketTO[] = [];

  displayedColumns = [
    'id',
    'idEmployee',
    'dsSubject',
    'dsCategory',
    'dsPriority',
    'dsStatus',
    'assignedTo',
  ];

  selectedTab = 0;
  selectedTicket: TicketTO = null;
  comments: TicketCommentTO[] = [];
  newComment = '';
  newAssignedTo = '';

  readonly STATUS_MAP = ['ABIERTO', 'EN_PROCESO', 'CERRADO'];

  constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _router: Router,
    private _ticket: TicketService,
    private _data: DataService
  ) {
    this._breadcrumb.setRouteText({ title: BREADCRUMB.TICKETS, arrow: false });
    this._toolbar.setVisible(this._router.url.toString());
  }

  ngOnInit() {
    this.loadByTab(0);
  }

  loadByTab(tab: number) {
    this.selectedTab = tab;
    this.selectedTicket = null;
    this.comments = [];
    const status = this.STATUS_MAP[tab];
    this._data.setIsLoadingEvent(true);
    this._ticket.getByStatus(status).subscribe(
      (resp: TicketTO[]) => {
        this._data.setIsLoadingEvent(false);
        if (tab === 0) this.openTickets = resp || [];
        else if (tab === 1) this.inProgressTickets = resp || [];
        else this.closedTickets = resp || [];
      },
      () => this._data.setIsLoadingEvent(false)
    );
  }

  onTabChange(index: number) {
    this.loadByTab(index);
  }

  selectTicket(ticket: TicketTO) {
    this.selectedTicket = ticket;
    this.newAssignedTo = ticket.assignedTo || '';
    this.loadComments(ticket.id);
  }

  loadComments(ticketId: number) {
    this._ticket.getComments(ticketId).subscribe(
      (resp: TicketCommentTO[]) => (this.comments = resp || []),
      (err) => console.error('Error cargando comentarios', err)
    );
  }

  updateStatus(newStatus: string) {
    if (!this.selectedTicket) return;
    this._ticket.updateStatus(this.selectedTicket.id, newStatus, this.newAssignedTo).subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Estatus actualizado');
        this.loadByTab(this.selectedTab);
      },
      (err) => console.error('Error actualizando estatus', err)
    );
  }

  sendComment() {
    if (!this.newComment.trim() || !this.selectedTicket) return;
    const comment: TicketCommentTO = {
      idTicket: this.selectedTicket.id,
      dsAuthor: 'ADMIN',
      dsContent: this.newComment.trim(),
    };
    this._ticket.addComment(comment).subscribe(
      () => {
        this.newComment = '';
        this.loadComments(this.selectedTicket.id);
      },
      (err) => console.error('Error enviando comentario', err)
    );
  }

  closeDetail() {
    this.selectedTicket = null;
    this.comments = [];
    this.newComment = '';
  }

  statusClass(status: string): string {
    const map = {
      ABIERTO: 'dch-badge-abierto',
      EN_PROCESO: 'dch-badge-proceso',
      CERRADO: 'dch-badge-cerrado',
    };
    return map[status] || '';
  }

  priorityClass(priority: string): string {
    const map = {
      CRITICA: 'dch-badge-rechazada',
      ALTA: 'dch-badge-pendiente',
      MEDIA: 'dch-badge-proceso',
      BAJA: 'dch-badge-cerrado',
    };
    return map[priority] || '';
  }
}
