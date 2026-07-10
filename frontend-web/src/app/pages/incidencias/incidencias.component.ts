import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { Router } from '@angular/router';
import { IncidentService } from '../../services/incident/incident.service';
import { UserService } from '../../services/user/user.service';
import { DataService } from '../../services/data.service';
import { BREADCRUMB } from '../../../environments/environment';
import { IncidentTO } from '../../models/hr.model';
import { EmployeeTO } from '../../models/employee.model';
import { DialogCreateIncidentComponent } from './dialog-create-incident/dialog-create-incident.component';

@Component({
  selector: 'app-incidencias',
  templateUrl: './incidencias.component.html',
  styleUrls: ['./incidencias.component.css'],
})
export class IncidenciasComponent implements OnInit {
  incidents: IncidentTO[] = [];
  filteredIncidents: IncidentTO[] = [];
  employeeNameMap: { [id: number]: string } = {};
  displayedColumns = [
    'id',
    'idEmployee',
    'dsType',
    'dtIncidentDate',
    'dtEndDate',
    'dsNotes',
    'dsStatus',
  ];

  fromDate: string;
  toDate: string;
  selectedType = '';

  readonly INCIDENT_TYPES = ['FALTA', 'RETARDO', 'PERMISO', 'INCAPACIDAD', 'CAMBIO_TURNO'];

  constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _router: Router,
    private _incident: IncidentService,
    private _user: UserService,
    private _data: DataService,
    private _dialog: MatDialog
  ) {
    this._breadcrumb.setRouteText({ title: BREADCRUMB.INCIDENCIAS, arrow: false });
    this._toolbar.setVisible(this._router.url.toString());

    const now = new Date();
    const firstDay = new Date(now.getFullYear(), now.getMonth(), 1);
    this.fromDate = this.toInputDate(firstDay);
    this.toDate = this.toInputDate(now);
  }

  ngOnInit() {
    this.loadEmployeeNames();
    this.loadIncidents();
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

  private toInputDate(d: Date): string {
    return d.toISOString().split('T')[0];
  }

  loadIncidents() {
    if (!this.fromDate || !this.toDate) return;
    this._data.setIsLoadingEvent(true);
    this._incident.getByPeriod(this.fromDate, this.toDate).subscribe(
      (resp: IncidentTO[]) => {
        this._data.setIsLoadingEvent(false);
        this.incidents = resp || [];
        this.applyTypeFilter();
      },
      () => this._data.setIsLoadingEvent(false)
    );
  }

  applyTypeFilter() {
    if (this.selectedType) {
      this.filteredIncidents = this.incidents.filter((i) => i.dsType === this.selectedType);
    } else {
      this.filteredIncidents = [...this.incidents];
    }
  }

  selectType(type: string) {
    this.selectedType = this.selectedType === type ? '' : type;
    this.applyTypeFilter();
  }

  openCreateDialog() {
    const dialogRef = this._dialog.open(DialogCreateIncidentComponent, {
      width: '480px',
      disableClose: false,
    });
    dialogRef.afterClosed().subscribe((created) => {
      if (created) {
        this.loadIncidents();
      }
    });
  }

  exportExcel() {
    this._incident.exportExcel(this.fromDate, this.toDate).subscribe(
      (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `incidencias_${this.fromDate}_${this.toDate}.xlsx`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      (err) => console.error('Error al exportar Excel', err)
    );
  }
}
