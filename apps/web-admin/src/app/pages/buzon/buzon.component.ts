import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';

import { environment } from '../../../environments/environment';

interface BuzonItem {
  idBuzon: number;
  dsCategoria: string;
  dsDescripcion: string;
  fgAnonimo: boolean;
  dsNombreReportante?: string;
  dsEstatus: string;
  dsComentarioRh?: string;
  dtCreacion: string;
}

@Component({
  selector: 'app-buzon',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatChipsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
  ],
  styles: [
    `
      .page-container {
        max-width: 1400px;
      }

      .page-header {
        display: flex;
        align-items: flex-start;
        justify-content: space-between;
        margin-bottom: 24px;
        gap: 16px;

        h1 {
          margin: 0;
          font-size: 1.75rem;
          font-weight: 700;
          color: #1a1a2e;
          display: flex;
          align-items: center;
          gap: 8px;
        }

        .subtitle {
          margin: 4px 0 0;
          font-size: 0.875rem;
          color: #757575;
        }
      }

      .layout {
        display: flex;
        gap: 24px;
        align-items: flex-start;
      }

      .table-card {
        flex: 1;
        background: white;
        border-radius: 12px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06);
        overflow: hidden;
        min-width: 0;
      }

      .loading-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 80px 24px;
        gap: 16px;

        .loading-text {
          font-size: 0.875rem;
          color: #757575;
        }
      }

      .empty-state {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 80px 24px;
        text-align: center;

        mat-icon {
          font-size: 56px;
          width: 56px;
          height: 56px;
          color: #bdbdbd;
          margin-bottom: 16px;
        }

        h3 {
          margin: 0 0 8px;
          font-size: 1.125rem;
          font-weight: 600;
          color: #424242;
        }

        p {
          margin: 0;
          font-size: 0.875rem;
          color: #757575;
        }
      }

      .table-wrapper {
        overflow-x: auto;

        table {
          width: 100%;
          min-width: 700px;
        }
      }

      .mat-mdc-header-row {
        background: #f8f9fa;
      }

      .mat-mdc-header-cell {
        font-weight: 600;
        font-size: 0.8125rem;
        color: #616161;
        padding: 0 16px;
      }

      .table-row {
        transition: background-color 0.15s;
        cursor: pointer;

        &:hover {
          background-color: #f5f7ff;
        }

        .mat-mdc-cell {
          padding: 12px 16px;
          font-size: 0.875rem;
          color: #424242;
        }
      }

      .status-chip {
        display: inline-flex;
        align-items: center;
        padding: 4px 12px;
        border-radius: 20px;
        font-size: 0.75rem;
        font-weight: 600;

        &.chip-warn {
          background: #fff3e0;
          color: #e65100;
        }

        &.chip-accent {
          background: #e3f2fd;
          color: #1565c0;
        }

        &.chip-primary {
          background: #e8f5e9;
          color: #2e7d32;
        }
      }

      .chip-anon-yes {
        display: inline-flex;
        align-items: center;
        padding: 2px 10px;
        border-radius: 20px;
        font-size: 0.7rem;
        font-weight: 600;
        background: #f3e5f5;
        color: #6a1b9a;
      }

      .chip-anon-no {
        display: inline-flex;
        align-items: center;
        padding: 2px 10px;
        border-radius: 20px;
        font-size: 0.7rem;
        font-weight: 600;
        background: #f5f5f5;
        color: #757575;
      }

      .folio-text {
        font-weight: 600;
        color: #005696;
        font-size: 0.8125rem;
      }

      /* Detail panel */
      .detail-panel {
        width: 380px;
        flex-shrink: 0;
        background: white;
        border-radius: 12px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06);
        padding: 24px;
      }

      .detail-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 20px;

        h3 {
          margin: 0;
          font-size: 1.1rem;
          font-weight: 700;
          color: #1a1a2e;
        }
      }

      .detail-field {
        margin-bottom: 16px;

        label {
          display: block;
          font-size: 0.75rem;
          font-weight: 600;
          color: #9e9e9e;
          text-transform: uppercase;
          letter-spacing: 0.5px;
          margin-bottom: 4px;
        }

        .detail-value {
          font-size: 0.9rem;
          color: #424242;
          line-height: 1.5;
        }
      }

      .detail-divider {
        border: none;
        border-top: 1px solid #f0f0f0;
        margin: 20px 0;
      }

      .update-section {
        h4 {
          margin: 0 0 16px;
          font-size: 0.875rem;
          font-weight: 600;
          color: #616161;
          text-transform: uppercase;
          letter-spacing: 0.5px;
        }
      }

      .form-field-full {
        width: 100%;
        margin-bottom: 12px;
      }

      .update-btn {
        width: 100%;
        margin-top: 4px;
      }
    `,
  ],
  template: `
    <div class="page-container">
      <!-- Header -->
      <div class="page-header">
        <div>
          <h1>
            <mat-icon>lock</mat-icon>
            Buzón Confidencial
          </h1>
          <p class="subtitle">
            Gestiona los reportes confidenciales del tenant. Los reportes anónimos no muestran el
            nombre del reportante.
          </p>
        </div>
      </div>

      <!-- Main layout: table + detail panel -->
      <div class="layout">
        <!-- Table card -->
        <div class="table-card">
          @if (loading()) {
            <div class="loading-container">
              <mat-spinner diameter="40"></mat-spinner>
              <span class="loading-text">Cargando reportes...</span>
            </div>
          } @else if (items().length === 0) {
            <div class="empty-state">
              <mat-icon>inbox</mat-icon>
              <h3>Sin reportes</h3>
              <p>No hay reportes en el buzón confidencial por el momento.</p>
            </div>
          } @else {
            <div class="table-wrapper">
              <table mat-table [dataSource]="items()">
                <!-- Folio -->
                <ng-container matColumnDef="folio">
                  <th mat-header-cell *matHeaderCellDef>Folio</th>
                  <td mat-cell *matCellDef="let row">
                    <span class="folio-text">#{{ row.idBuzon }}</span>
                  </td>
                </ng-container>

                <!-- Categoria -->
                <ng-container matColumnDef="categoria">
                  <th mat-header-cell *matHeaderCellDef>Categoria</th>
                  <td mat-cell *matCellDef="let row">{{ row.dsCategoria }}</td>
                </ng-container>

                <!-- Anonimo -->
                <ng-container matColumnDef="anonimo">
                  <th mat-header-cell *matHeaderCellDef>Anonimo</th>
                  <td mat-cell *matCellDef="let row">
                    @if (row.fgAnonimo) {
                      <span class="chip-anon-yes">Si</span>
                    } @else {
                      <span class="chip-anon-no">No</span>
                    }
                  </td>
                </ng-container>

                <!-- Estatus -->
                <ng-container matColumnDef="estatus">
                  <th mat-header-cell *matHeaderCellDef>Estatus</th>
                  <td mat-cell *matCellDef="let row">
                    <span class="status-chip" [ngClass]="'chip-' + getColor(row.dsEstatus)">
                      {{ row.dsEstatus | titlecase }}
                    </span>
                  </td>
                </ng-container>

                <!-- Fecha -->
                <ng-container matColumnDef="fecha">
                  <th mat-header-cell *matHeaderCellDef>Fecha</th>
                  <td mat-cell *matCellDef="let row">{{ row.dtCreacion | date: 'dd/MM/yyyy' }}</td>
                </ng-container>

                <!-- Acciones -->
                <ng-container matColumnDef="acciones">
                  <th mat-header-cell *matHeaderCellDef>Acciones</th>
                  <td mat-cell *matCellDef="let row">
                    <button
                      mat-icon-button
                      color="primary"
                      matTooltip="Ver detalle"
                      (click)="verDetalle(row); $event.stopPropagation()"
                    >
                      <mat-icon>open_in_new</mat-icon>
                    </button>
                  </td>
                </ng-container>

                <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
                <tr
                  mat-row
                  *matRowDef="let row; columns: displayedColumns"
                  class="table-row"
                  (click)="verDetalle(row)"
                ></tr>
              </table>
            </div>
          }
        </div>

        <!-- Detail panel -->
        @if (selected()) {
          <div class="detail-panel">
            <div class="detail-header">
              <h3>Reporte #{{ selected()!.idBuzon }}</h3>
              <button mat-icon-button matTooltip="Cerrar" (click)="selected.set(null)">
                <mat-icon>close</mat-icon>
              </button>
            </div>

            <div class="detail-field">
              <label>Categoria</label>
              <span class="detail-value">{{ selected()!.dsCategoria }}</span>
            </div>

            <div class="detail-field">
              <label>Descripcion</label>
              <span class="detail-value">{{ selected()!.dsDescripcion }}</span>
            </div>

            @if (!selected()!.fgAnonimo && selected()!.dsNombreReportante) {
              <div class="detail-field">
                <label>Reportante</label>
                <span class="detail-value">{{ selected()!.dsNombreReportante }}</span>
              </div>
            }

            <div class="detail-field">
              <label>Estatus actual</label>
              <span class="status-chip" [ngClass]="'chip-' + getColor(selected()!.dsEstatus)">
                {{ selected()!.dsEstatus | titlecase }}
              </span>
            </div>

            @if (selected()!.dsComentarioRh) {
              <div class="detail-field">
                <label>Comentario RH</label>
                <span class="detail-value">{{ selected()!.dsComentarioRh }}</span>
              </div>
            }

            <div class="detail-field">
              <label>Fecha de reporte</label>
              <span class="detail-value">{{
                selected()!.dtCreacion | date: 'dd/MM/yyyy HH:mm'
              }}</span>
            </div>

            <hr class="detail-divider" />

            <div class="update-section">
              <h4>Actualizar estatus</h4>

              <mat-form-field appearance="outline" class="form-field-full">
                <mat-label>Nuevo estatus</mat-label>
                <mat-select [(ngModel)]="nuevoEstatus">
                  <mat-option value="NUEVO">Nuevo</mat-option>
                  <mat-option value="EN_REVISION">En revision</mat-option>
                  <mat-option value="CERRADO">Cerrado</mat-option>
                </mat-select>
              </mat-form-field>

              <mat-form-field appearance="outline" class="form-field-full">
                <mat-label>Comentario RH</mat-label>
                <textarea
                  matInput
                  [(ngModel)]="comentario"
                  rows="3"
                  placeholder="Agrega un comentario interno..."
                ></textarea>
              </mat-form-field>

              <button
                mat-flat-button
                color="primary"
                class="update-btn"
                [disabled]="updating() || !nuevoEstatus"
                (click)="actualizarEstatus()"
              >
                @if (updating()) {
                  <mat-spinner
                    diameter="18"
                    style="display:inline-block;margin-right:8px"
                  ></mat-spinner>
                }
                Actualizar
              </button>
            </div>
          </div>
        }
      </div>
    </div>
  `,
})
export class BuzonComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  items = signal<BuzonItem[]>([]);
  loading = signal(true);
  selected = signal<BuzonItem | null>(null);
  updating = signal(false);

  comentario = '';
  nuevoEstatus = '';

  displayedColumns = ['folio', 'categoria', 'anonimo', 'estatus', 'fecha', 'acciones'];

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.http.get<BuzonItem[]>(`${environment.gatewayUrl}/api/hr/buzon/list`).subscribe({
      next: (data) => {
        this.items.set(Array.isArray(data) ? data : []);
        this.loading.set(false);
      },
      error: () => {
        this.items.set([]);
        this.loading.set(false);
      },
    });
  }

  verDetalle(item: BuzonItem): void {
    this.selected.set(item);
    this.nuevoEstatus = item.dsEstatus;
    this.comentario = item.dsComentarioRh ?? '';
  }

  actualizarEstatus(): void {
    const current = this.selected();
    if (!current || !this.nuevoEstatus) return;

    this.updating.set(true);
    this.http
      .put(`${environment.gatewayUrl}/api/hr/buzon/${current.idBuzon}/estatus`, {
        dsEstatus: this.nuevoEstatus,
        dsComentarioRh: this.comentario,
      })
      .subscribe({
        next: () => {
          this.snackBar.open('Estatus actualizado', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.updating.set(false);
          this.selected.set(null);
          this.load();
        },
        error: () => {
          this.snackBar.open('Error al actualizar el estatus', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.updating.set(false);
        },
      });
  }

  getColor(estatus: string): string {
    switch (estatus) {
      case 'NUEVO':
        return 'warn';
      case 'EN_REVISION':
        return 'accent';
      case 'CERRADO':
        return 'primary';
      default:
        return 'warn';
    }
  }
}
