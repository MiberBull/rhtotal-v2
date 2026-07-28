import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-shift-dialog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
  ],
  template: `
    <h2 mat-dialog-title>{{ data.shift ? 'Editar Turno' : 'Nuevo Turno' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="form" style="display:flex;flex-direction:column;gap:8px;padding-top:8px">
        <mat-form-field appearance="outline">
          <mat-label>Nombre</mat-label>
          <input matInput formControlName="dsName" />
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Tipo</mat-label>
          <mat-select formControlName="dsType">
            <mat-option value="FIJO">Fijo</mat-option>
            <mat-option value="ROTATIVO">Rotativo</mat-option>
            <mat-option value="HOME_OFFICE">Home Office</mat-option>
          </mat-select>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Entrada</mat-label>
          <input matInput type="time" formControlName="dsStartTime" />
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Salida</mat-label>
          <input matInput type="time" formControlName="dsEndTime" />
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button
        mat-flat-button
        color="primary"
        [mat-dialog-close]="form.value"
        [disabled]="form.invalid"
      >
        {{ data.shift ? 'Guardar' : 'Crear' }}
      </button>
    </mat-dialog-actions>
  `,
})
export class ShiftDialogComponent implements OnInit {
  data = inject(MAT_DIALOG_DATA);
  private fb = inject(FormBuilder);
  form!: FormGroup;

  ngOnInit(): void {
    this.form = this.fb.group({
      dsName: [this.data.shift?.dsName ?? '', Validators.required],
      dsType: [this.data.shift?.dsType ?? 'FIJO', Validators.required],
      dsStartTime: [this.data.shift?.dsStartTime ?? '09:00'],
      dsEndTime: [this.data.shift?.dsEndTime ?? '18:00'],
    });
  }
}

@Component({
  selector: 'app-asistencia',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatTabsModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './asistencia.component.html',
  styleUrl: './asistencia.component.scss',
})
export class AsistenciaComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  // Tabs
  activeTab = signal(0);

  // Registros
  records = signal<any[]>([]);
  recordsLoading = signal(true);
  recordsTotal = signal(0);
  recordsPage = signal(0);
  recordsSize = signal(10);
  dateFrom = signal<Date | null>(null);
  dateTo = signal<Date | null>(null);
  recordColumns = ['employee', 'project', 'date', 'checkIn', 'checkOut', 'hours', 'geofence'];

  // Turnos
  shifts = signal<any[]>([]);
  shiftsLoading = signal(true);
  shiftColumns = ['name', 'type', 'startTime', 'endTime', 'actions'];

  // Overtime pendiente
  overtime = signal<any[]>([]);
  overtimeLoading = signal(true);
  overtimeTotal = signal(0);
  overtimePage = signal(0);
  overtimeSize = signal(10);
  overtimeColumns = ['employee', 'date', 'extraMinutes', 'status', 'actions'];

  ngOnInit(): void {
    const now = new Date();
    this.dateFrom.set(new Date(now.getFullYear(), now.getMonth(), 1));
    this.dateTo.set(now);
    this.loadRecords();
    this.loadShifts();
    this.loadOvertime();
  }

  onTabChange(index: number): void {
    this.activeTab.set(index);
  }

  onDateFromChange(date: Date | null): void {
    this.dateFrom.set(date);
    this.recordsPage.set(0);
    this.loadRecords();
  }

  onDateToChange(date: Date | null): void {
    this.dateTo.set(date);
    this.recordsPage.set(0);
    this.loadRecords();
  }

  onRecordsPageChange(event: PageEvent): void {
    this.recordsPage.set(event.pageIndex);
    this.recordsSize.set(event.pageSize);
    this.loadRecords();
  }

  onOvertimePageChange(event: PageEvent): void {
    this.overtimePage.set(event.pageIndex);
    this.overtimeSize.set(event.pageSize);
    this.loadOvertime();
  }

  openShiftDialog(shift: any = null): void {
    const ref = this.dialog.open(ShiftDialogComponent, { width: '400px', data: { shift } });
    ref.afterClosed().subscribe((data) => {
      if (!data) return;
      // Backend: PUT /shift toma idShift en body (no en path); POST /shift crea
      const req = shift
        ? this.http.put(`${environment.gatewayUrl}/api/attendance/shift`, {
            idShift: shift.id,
            ...data,
          })
        : this.http.post(`${environment.gatewayUrl}/api/attendance/shift`, data);
      req.subscribe({
        next: () => {
          this.snackBar.open(shift ? 'Turno actualizado' : 'Turno creado', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadShifts();
        },
        error: () =>
          this.snackBar.open('Error al guardar turno', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
    });
  }

  approveOvertime(ot: any): void {
    this.http
      .put(`${environment.gatewayUrl}/api/attendance/overtime/${ot.id}/approve`, null, {
        params: { approvedBy: 'sistema' },
      })
      .subscribe({
        next: () => {
          this.snackBar.open('Hora extra aprobada', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadOvertime();
        },
        error: () =>
          this.snackBar.open('Error al aprobar', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
  }

  rejectOvertime(ot: any): void {
    this.http
      .put(`${environment.gatewayUrl}/api/attendance/overtime/${ot.id}/reject`, null, {
        params: { approvedBy: 'sistema' },
      })
      .subscribe({
        next: () => {
          this.snackBar.open('Hora extra rechazada', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadOvertime();
        },
        error: () =>
          this.snackBar.open('Error al rechazar', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
  }

  getShiftTypeLabel(type: string): string {
    switch (type) {
      case 'FIJO':
        return 'Fijo';
      case 'ROTATIVO':
        return 'Rotativo';
      case 'HOME_OFFICE':
        return 'Home Office';
      default:
        return type ?? '—';
    }
  }

  private loadRecords(): void {
    this.recordsLoading.set(true);
    let params = new HttpParams()
      .set('page', this.recordsPage().toString())
      .set('size', this.recordsSize().toString());
    if (this.dateFrom()) params = params.set('from', this.formatDate(this.dateFrom()!));
    if (this.dateTo()) params = params.set('to', this.formatDate(this.dateTo()!));

    // Backend expone /report (no /admin/records)
    this.http.get<any>(`${environment.gatewayUrl}/api/attendance/report`, { params }).subscribe({
      next: (res) => {
        const data = Array.isArray(res) ? res : (res?.content ?? []);
        this.records.set(data);
        this.recordsTotal.set(res?.totalElements ?? data.length);
        this.recordsLoading.set(false);
      },
      error: () => {
        this.records.set([]);
        this.recordsLoading.set(false);
      },
    });
  }

  private loadShifts(): void {
    this.shiftsLoading.set(true);
    // Backend expone /shift/all (no /shift)
    this.http.get<any[]>(`${environment.gatewayUrl}/api/attendance/shift/all`).subscribe({
      next: (res) => {
        this.shifts.set(Array.isArray(res) ? res : []);
        this.shiftsLoading.set(false);
      },
      error: () => this.shiftsLoading.set(false),
    });
  }

  private loadOvertime(): void {
    this.overtimeLoading.set(true);
    // Backend: /overtime?status=PENDIENTE (sin path /pending)
    this.http
      .get<any>(`${environment.gatewayUrl}/api/attendance/overtime`, {
        params: {
          status: 'PENDIENTE',
          page: this.overtimePage().toString(),
          size: this.overtimeSize().toString(),
        },
      })
      .subscribe({
        next: (res) => {
          const data = Array.isArray(res) ? res : (res?.content ?? []);
          this.overtime.set(data);
          this.overtimeTotal.set(res?.totalElements ?? data.length);
          this.overtimeLoading.set(false);
        },
        error: () => this.overtimeLoading.set(false),
      });
  }

  private formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }
}
