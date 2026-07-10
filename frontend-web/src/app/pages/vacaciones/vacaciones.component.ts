import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { Router } from '@angular/router';
import { VacationService } from '../../services/vacation/vacation.service';
import { UserService } from '../../services/user/user.service';
import { DataService } from '../../services/data.service';
import { BREADCRUMB } from '../../../environments/environment';
import { VacationRequestTO } from '../../models/hr.model';
import { EmployeeTO } from '../../models/employee.model';

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
  employeeNameMap: { [id: number]: string } = {};

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
    private _user: UserService,
    private _data: DataService,
    private _dialog: MatDialog
  ) {
    this._breadcrumb.setRouteText({ title: BREADCRUMB.VACACIONES, arrow: false });
    this._toolbar.setVisible(this._router.url.toString());
  }

  ngOnInit() {
    this.loadEmployeeNames();
    this.loadRequests();
  }

  loadEmployeeNames() {
    this._user.getEmployeeAll().subscribe(
      (employees: EmployeeTO[]) => {
        (employees || []).forEach((e) => {
          const full = [e.name, e.lastName, e.lastMName].filter(Boolean).join(' ');
          this.employeeNameMap[e.id] = full || `#${e.id}`;
        });
      },
      () => {}
    );
  }

  getEmployeeName(idEmployee: number): string {
    return this.employeeNameMap[idEmployee] || `#${idEmployee}`;
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
    const name = this.getEmployeeName(request.idEmployee);
    const confirmed = window.confirm(
      `¿Aprobar la solicitud de vacaciones de ${name}?\n${request.nuDays} días del ${new Date(request.dtStartDate).toLocaleDateString('es-MX')} al ${new Date(request.dtEndDate).toLocaleDateString('es-MX')}.`
    );
    if (!confirmed) return;
    this._vacation.approve(request.id, 'ADMIN').subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Solicitud aprobada correctamente');
        this.loadRequests();
      },
      (err) => console.error('Error al aprobar solicitud', err)
    );
  }

  reject(request: VacationRequestTO) {
    const name = this.getEmployeeName(request.idEmployee);
    const confirmed = window.confirm(
      `¿Rechazar la solicitud de vacaciones de ${name}?\nEsta acción no se puede deshacer.`
    );
    if (!confirmed) return;
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
