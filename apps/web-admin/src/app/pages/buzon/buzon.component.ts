import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';

import { environment } from '../../../environments/environment';

interface BuzonReporte {
  idBuzon: number;
  tenantId: string;
  dsCategoria: string;
  dsDescripcion: string;
  fgAnonimo: boolean;
  idUsuario: number;
  dsNombreReportante: string;
  dsEstatus: 'ABIERTO' | 'EN_PROCESO' | 'CERRADO';
  dsComentarioRh: string;
  dtCreacion: string;
  dtActualizacion: string;
}

@Component({
  selector: 'app-buzon-detail-dialog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
  ],
  template: `
    <h2 mat-dialog-title>Reporte #{{ data.reporte.idBuzon }} — {{ data.reporte.dsCategoria }}</h2>
    <mat-dialog-content style="min-width:480px">
      <div class="detail-row">
        <span class="detail-label">Reportante</span>
        <span class="detail-value">
          {{ data.reporte.fgAnonimo ? 'Anónimo' : data.reporte.dsNombreReportante || '—' }}
        </span>
      </div>
      <div class="detail-row">
        <span class="detail-label">Categoría</span>
        <span class="detail-value">{{ data.reporte.dsCategoria }}</span>
      </div>
      <div class="detail-row">
        <span class="detail-label">Descripción</span>
        <span class="detail-value desc">{{ data.reporte.dsDescripcion }}</span>
      </div>
      <div class="detail-row">
        <span class="detail-label">Fecha</span>
        <span class="detail-value">{{ data.reporte.dtCreacion | date: 'dd/MM/yyyy HH:mm' }}</span>
      </div>
      @if (data.reporte.dsComentarioRh) {
        <div class="detail-row">
          <span class="detail-label">Comentario RH anterior</span>
          <span class="detail-value">{{ data.reporte.dsComentarioRh }}</span>
        </div>
      }
      <hr style="margin:16px 0;border:none;border-top:1px solid #e0e0e0" />
      <mat-form-field appearance="outline" style="width:100%;margin-bottom:8px">
        <mat-label>Nuevo estatus</mat-label>
        <mat-select [(ngModel)]="nuevoEstatus">
          <mat-option value="ABIERTO">Abierto</mat-option>
          <mat-option value="EN_PROCESO">En proceso</mat-option>
          <mat-option value="CERRADO">Cerrado</mat-option>
        </mat-select>
      </mat-form-field>
      <mat-form-field appearance="outline" style="width:100%">
        <mat-label>Comentario de RH</mat-label>
        <textarea
          matInput
          [(ngModel)]="comentarioRh"
          rows="3"
          placeholder="Escribe tu respuesta o seguimiento..."
        ></textarea>
      </mat-form-field>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button
        mat-flat-button
        color="primary"
        [mat-dialog-close]="{ dsEstatus: nuevoEstatus, dsComentarioRh: comentarioRh }"
        [disabled]="!nuevoEstatus"
      >
        Guardar
      </button>
    </mat-dialog-actions>
  `,
  styles: [
    `
      .detail-row {
        display: flex;
        gap: 12px;
        margin-bottom: 10px;
        font-size: 0.875rem;
      }
      .detail-label {
        min-width: 140px;
        font-weight: 600;
        color: #616161;
      }
      .detail-value {
        color: #1a1a2e;
      }
      .detail-value.desc {
        white-space: pre-wrap;
        word-break: break-word;
      }
    `,
  ],
})
export class BuzonDetailDialogComponent {
  data = inject(MAT_DIALOG_DATA) as { reporte: BuzonReporte };
  nuevoEstatus = this.data.reporte.dsEstatus;
  comentarioRh = this.data.reporte.dsComentarioRh ?? '';
}

@Component({
  selector: 'app-buzon',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatTabsModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatDialogModule,
    MatChipsModule,
  ],
  templateUrl: './buzon.component.html',
  styleUrl: './buzon.component.scss',
})
export class BuzonComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  readonly tabs = [
    { label: 'Abiertos', status: 'ABIERTO' },
    { label: 'En Proceso', status: 'EN_PROCESO' },
    { label: 'Cerrados', status: 'CERRADO' },
  ];

  activeStatus = signal<string>('ABIERTO');
  allReportes = signal<BuzonReporte[]>([]);
  loading = signal(true);

  readonly displayedColumns = [
    'folio',
    'categoria',
    'descripcion',
    'reportante',
    'fecha',
    'estatus',
    'acciones',
  ];

  get reportesFiltrados(): BuzonReporte[] {
    return this.allReportes().filter((r) => r.dsEstatus === this.activeStatus());
  }

  ngOnInit(): void {
    this.loadReportes();
  }

  onTabChange(index: number): void {
    this.activeStatus.set(this.tabs[index].status);
  }

  openDetalle(reporte: BuzonReporte): void {
    const ref = this.dialog.open(BuzonDetailDialogComponent, {
      width: '560px',
      data: { reporte },
    });

    ref
      .afterClosed()
      .subscribe((result: { dsEstatus: string; dsComentarioRh: string } | undefined) => {
        if (!result?.dsEstatus) return;
        this.http
          .put<BuzonReporte>(`${environment.gatewayUrl}/api/hr/buzon/${reporte.idBuzon}/estatus`, {
            dsEstatus: result.dsEstatus,
            dsComentarioRh: result.dsComentarioRh,
          })
          .subscribe({
            next: () => {
              this.snackBar.open('Reporte actualizado', 'Cerrar', {
                duration: 3000,
                horizontalPosition: 'end',
                verticalPosition: 'top',
              });
              this.loadReportes();
            },
            error: () =>
              this.snackBar.open('Error al actualizar el reporte', 'Cerrar', {
                duration: 4000,
                horizontalPosition: 'end',
                verticalPosition: 'top',
              }),
          });
      });
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'ABIERTO':
        return 'status-open';
      case 'EN_PROCESO':
        return 'status-inprogress';
      case 'CERRADO':
        return 'status-closed';
      default:
        return '';
    }
  }

  private loadReportes(): void {
    this.loading.set(true);
    this.http.get<BuzonReporte[]>(`${environment.gatewayUrl}/api/hr/buzon/list`).subscribe({
      next: (data) => {
        this.allReportes.set(Array.isArray(data) ? data : []);
        this.loading.set(false);
      },
      error: () => {
        this.allReportes.set([]);
        this.loading.set(false);
      },
    });
  }
}
