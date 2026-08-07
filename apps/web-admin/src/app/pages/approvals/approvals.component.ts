import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterModule } from '@angular/router';
import { environment } from '../../../environments/environment';

interface SolicitudCambio {
  idSolicitud: number;
  idEmpleado: number;
  nombreEmpleado: string;
  campo: string;
  valorActual: string;
  valorNuevo: string;
  estatus: 'PENDIENTE' | 'APROBADA' | 'RECHAZADA';
  fechaSolicitud: string;
}

@Component({
  selector: 'app-approvals',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
  ],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h1 class="page-title">
          <mat-icon>rule</mat-icon>
          Aprobaciones de Cambios
        </h1>
        <p class="page-subtitle">
          Solicitudes de cambio de datos de empleados pendientes de revisión
        </p>
      </div>

      @if (loading()) {
        <div class="loading-center"><mat-spinner diameter="48"></mat-spinner></div>
      } @else if (solicitudes().length === 0) {
        <mat-card class="empty-card">
          <mat-card-content>
            <mat-icon class="empty-icon">check_circle</mat-icon>
            <p>No hay solicitudes pendientes de aprobación</p>
          </mat-card-content>
        </mat-card>
      } @else {
        <mat-card>
          <table mat-table [dataSource]="solicitudes()" class="full-width">
            <ng-container matColumnDef="empleado">
              <th mat-header-cell *matHeaderCellDef>Empleado</th>
              <td mat-cell *matCellDef="let row">{{ row.nombreEmpleado }}</td>
            </ng-container>
            <ng-container matColumnDef="campo">
              <th mat-header-cell *matHeaderCellDef>Campo</th>
              <td mat-cell *matCellDef="let row">{{ row.campo }}</td>
            </ng-container>
            <ng-container matColumnDef="valorActual">
              <th mat-header-cell *matHeaderCellDef>Valor Actual</th>
              <td mat-cell *matCellDef="let row">{{ row.valorActual }}</td>
            </ng-container>
            <ng-container matColumnDef="valorNuevo">
              <th mat-header-cell *matHeaderCellDef>Valor Nuevo</th>
              <td mat-cell *matCellDef="let row">
                <strong>{{ row.valorNuevo }}</strong>
              </td>
            </ng-container>
            <ng-container matColumnDef="estatus">
              <th mat-header-cell *matHeaderCellDef>Estatus</th>
              <td mat-cell *matCellDef="let row">
                <mat-chip [class]="'chip-' + row.estatus.toLowerCase()">{{ row.estatus }}</mat-chip>
              </td>
            </ng-container>
            <ng-container matColumnDef="acciones">
              <th mat-header-cell *matHeaderCellDef>Acciones</th>
              <td mat-cell *matCellDef="let row">
                @if (row.estatus === 'PENDIENTE') {
                  <button
                    mat-icon-button
                    color="primary"
                    (click)="aprobar(row)"
                    matTooltip="Aprobar"
                  >
                    <mat-icon>check</mat-icon>
                  </button>
                  <button
                    mat-icon-button
                    color="warn"
                    (click)="rechazar(row)"
                    matTooltip="Rechazar"
                  >
                    <mat-icon>close</mat-icon>
                  </button>
                }
              </td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
          </table>
        </mat-card>
      }
    </div>
  `,
  styles: [
    `
      .page-container {
        padding: 24px;
      }
      .page-header {
        margin-bottom: 24px;
      }
      .page-title {
        display: flex;
        align-items: center;
        gap: 8px;
        margin: 0 0 4px;
        font-size: 24px;
      }
      .page-subtitle {
        margin: 0;
        color: #666;
      }
      .loading-center {
        display: flex;
        justify-content: center;
        padding: 48px;
      }
      .empty-card {
        text-align: center;
        padding: 48px;
      }
      .empty-icon {
        font-size: 64px;
        width: 64px;
        height: 64px;
        color: #4caf50;
      }
      .full-width {
        width: 100%;
      }
      .chip-pendiente {
        background: #fff3e0 !important;
        color: #e65100 !important;
      }
      .chip-aprobada {
        background: #e8f5e9 !important;
        color: #2e7d32 !important;
      }
      .chip-rechazada {
        background: #ffebee !important;
        color: #c62828 !important;
      }
    `,
  ],
})
export class ApprovalsComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  loading = signal(true);
  solicitudes = signal<SolicitudCambio[]>([]);
  displayedColumns = ['empleado', 'campo', 'valorActual', 'valorNuevo', 'estatus', 'acciones'];

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.http
      .get<SolicitudCambio[]>(`${environment.gatewayUrl}/api/user/employee/changes/pending`)
      .subscribe({
        next: (data) => {
          this.solicitudes.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.solicitudes.set([]);
          this.loading.set(false);
        },
      });
  }

  aprobar(row: SolicitudCambio): void {
    this.http
      .put(`${environment.gatewayUrl}/api/user/employee/changes/${row.idSolicitud}/approve`, {})
      .subscribe({
        next: () => {
          this.snackBar.open('Cambio aprobado', 'Cerrar', { duration: 3000 });
          this.load();
        },
        error: () => this.snackBar.open('Error al aprobar', 'Cerrar', { duration: 3000 }),
      });
  }

  rechazar(row: SolicitudCambio): void {
    this.http
      .put(`${environment.gatewayUrl}/api/user/employee/changes/${row.idSolicitud}/reject`, {})
      .subscribe({
        next: () => {
          this.snackBar.open('Cambio rechazado', 'Cerrar', { duration: 3000 });
          this.load();
        },
        error: () => this.snackBar.open('Error al rechazar', 'Cerrar', { duration: 3000 }),
      });
  }
}
