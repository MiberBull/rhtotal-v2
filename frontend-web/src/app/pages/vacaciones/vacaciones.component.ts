import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { Router } from '@angular/router';
import { VacationService } from '../../services/vacation/vacation.service';
import { DataService } from '../../services/data.service';
import { BREADCRUMB } from '../../../environments/environment';
import { VacationRequestTO } from '../../models/hr.model';

@Component({
  selector: 'app-vacaciones',
  templateUrl: './vacaciones.component.html',
  styleUrls: ['./vacaciones.component.css'],
})
export class VacacionesComponent implements OnInit {
  allRequests: VacationRequestTO[] = [];
  pendingRequests: VacationRequestTO[] = [];
  approvedRequests: VacationRequestTO[] = [];
  rejectedRequests: VacationRequestTO[] = [];

  displayedColumns = [
    'idEmployee',
    'dtStartDate',
    'dtEndDate',
    'nuDays',
    'dsNotes',
    'dsStatus',
    'acciones',
  ];
  displayedColumnsReadOnly = [
    'idEmployee',
    'dtStartDate',
    'dtEndDate',
    'nuDays',
    'dsNotes',
    'dsStatus',
  ];

  selectedTab = 0;

  constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _router: Router,
    private _vacation: VacationService,
    private _data: DataService
  ) {
    this._breadcrumb.setRouteText({ title: BREADCRUMB.VACACIONES, arrow: false });
    this._toolbar.setVisible(this._router.url.toString());
  }

  ngOnInit() {
    this.loadRequests();
  }

  loadRequests() {
    this._data.setIsLoadingEvent(true);
    this._vacation.getAll().subscribe(
      (resp: VacationRequestTO[]) => {
        this._data.setIsLoadingEvent(false);
        this.allRequests = resp || [];
        this.pendingRequests = this.allRequests.filter((r) => r.dsStatus === 'PENDIENTE');
        this.approvedRequests = this.allRequests.filter((r) => r.dsStatus === 'APROBADA');
        this.rejectedRequests = this.allRequests.filter((r) => r.dsStatus === 'RECHAZADA');
      },
      () => this._data.setIsLoadingEvent(false)
    );
  }

  onTabChange(index: number) {
    this.selectedTab = index;
  }

  approve(request: VacationRequestTO) {
    this._vacation.approve(request.id, 'ADMIN').subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Solicitud aprobada correctamente');
        this.loadRequests();
      },
      (err) => console.error('Error al aprobar solicitud', err)
    );
  }

  reject(request: VacationRequestTO) {
    this._vacation.reject(request.id, 'ADMIN', 'Rechazada por administrador').subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Solicitud rechazada');
        this.loadRequests();
      },
      (err) => console.error('Error al rechazar solicitud', err)
    );
  }

  statusClass(status: string): string {
    const map = {
      PENDIENTE: 'dch-badge-pendiente',
      APROBADA: 'dch-badge-aprobada',
      RECHAZADA: 'dch-badge-rechazada',
    };
    return map[status] || '';
  }
}
