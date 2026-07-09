import { Component, OnInit, inject, signal, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { provideNativeDateAdapter } from '@angular/material/core';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-incidencias',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './incidencias.component.html',
  styleUrl: './incidencias.component.scss',
})
export class IncidenciasComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  incidents = signal<any[]>([]);
  loading = signal(true);
  totalCount = signal(0);
  pageIndex = signal(0);
  pageSize = signal(10);
  exportLoading = signal(false);

  dateFrom = signal<Date | null>(null);
  dateTo = signal<Date | null>(null);
  selectedType = signal('');

  incidentTypes = [
    { value: '', label: 'Todos' },
    { value: 'FALTA', label: 'Falta' },
    { value: 'RETARDO', label: 'Retardo' },
    { value: 'PERMISO', label: 'Permiso' },
    { value: 'INCAPACIDAD', label: 'Incapacidad' },
    { value: 'OTRO', label: 'Otro' },
  ];

  displayedColumns = ['employee', 'type', 'date', 'reason', 'status', 'actions'];

  ngOnInit(): void {
    const now = new Date();
    const firstDay = new Date(now.getFullYear(), now.getMonth(), 1);
    this.dateFrom.set(firstDay);
    this.dateTo.set(now);
    this.loadIncidents();
  }

  onDateFromChange(date: Date | null): void {
    this.dateFrom.set(date);
    this.pageIndex.set(0);
    this.loadIncidents();
  }

  onDateToChange(date: Date | null): void {
    this.dateTo.set(date);
    this.pageIndex.set(0);
    this.loadIncidents();
  }

  onTypeChange(type: string): void {
    this.selectedType.set(type);
    this.pageIndex.set(0);
    this.loadIncidents();
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadIncidents();
  }

  exportExcel(): void {
    if (!this.dateFrom() || !this.dateTo()) return;
    this.exportLoading.set(true);
    const params = this.buildParams();

    this.http
      .get(`${environment.gatewayUrl}/api/hr/incident/export/excel`, {
        params,
        responseType: 'blob',
      })
      .subscribe({
        next: (blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `incidencias_${this.formatDate(this.dateFrom()!)}_${this.formatDate(this.dateTo()!)}.xlsx`;
          a.click();
          URL.revokeObjectURL(url);
          this.exportLoading.set(false);
        },
        error: () => {
          this.snackBar.open('Error al exportar', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.exportLoading.set(false);
        },
      });
  }

  getTypeLabel(type: string): string {
    return this.incidentTypes.find((t) => t.value === type)?.label ?? type ?? '—';
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'PENDIENTE':
        return 'status-pending';
      case 'APROBADA':
        return 'status-approved';
      case 'RECHAZADA':
        return 'status-rejected';
      default:
        return '';
    }
  }

  private loadIncidents(): void {
    this.loading.set(true);
    const params = this.buildParams()
      .set('page', this.pageIndex().toString())
      .set('size', this.pageSize().toString());

    this.http.get<any>(`${environment.gatewayUrl}/api/hr/incident/period`, { params }).subscribe({
      next: (res) => {
        const data = Array.isArray(res) ? res : (res?.content ?? []);
        this.incidents.set(data);
        this.totalCount.set(res?.totalElements ?? data.length);
        this.loading.set(false);
      },
      error: () => {
        this.incidents.set([]);
        this.loading.set(false);
      },
    });
  }

  private buildParams(): HttpParams {
    let params = new HttpParams();
    if (this.dateFrom()) params = params.set('from', this.formatDate(this.dateFrom()!));
    if (this.dateTo()) params = params.set('to', this.formatDate(this.dateTo()!));
    if (this.selectedType()) params = params.set('type', this.selectedType());
    return params;
  }

  private formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }
}
