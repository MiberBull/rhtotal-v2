import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';

import { environment } from '../../../../environments/environment';

interface LecturaRow {
  idEmployee: number;
  nombre: string;
  email: string;
  leido: boolean;
  fechaLectura: string | null;
}

@Component({
  selector: 'app-comunicado-lecturas',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatChipsModule,
  ],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h1 class="page-title">Seguimiento de lecturas</h1>
        <a mat-button routerLink="/comunicados"><mat-icon>arrow_back</mat-icon> Volver</a>
      </div>
      <div class="stats">
        <span class="stat read">{{ totalLeidos() }} leídos</span>
        <span class="stat total">{{ lecturas().length }} total</span>
      </div>
      @if (loading()) {
        <div style="display:flex;justify-content:center;padding:48px">
          <mat-spinner diameter="40" />
        </div>
      } @else {
        <table mat-table [dataSource]="lecturas()" class="mat-elevation-z1">
          <ng-container matColumnDef="nombre">
            <th mat-header-cell *matHeaderCellDef>Colaborador</th>
            <td mat-cell *matCellDef="let r">{{ r.nombre }}</td>
          </ng-container>
          <ng-container matColumnDef="email">
            <th mat-header-cell *matHeaderCellDef>Email</th>
            <td mat-cell *matCellDef="let r">{{ r.email }}</td>
          </ng-container>
          <ng-container matColumnDef="leido">
            <th mat-header-cell *matHeaderCellDef>Leído</th>
            <td mat-cell *matCellDef="let r">
              <mat-chip [color]="r.leido ? 'primary' : 'warn'" highlighted>
                {{ r.leido ? 'Sí' : 'No' }}
              </mat-chip>
            </td>
          </ng-container>
          <ng-container matColumnDef="fechaLectura">
            <th mat-header-cell *matHeaderCellDef>Fecha de lectura</th>
            <td mat-cell *matCellDef="let r">
              {{ r.fechaLectura ? (r.fechaLectura | date: 'dd/MM/yyyy HH:mm') : '—' }}
            </td>
          </ng-container>
          <tr mat-header-row *matHeaderRowDef="cols"></tr>
          <tr mat-row *matRowDef="let row; columns: cols"></tr>
        </table>
      }
    </div>
  `,
  styles: [
    `
      .page-container {
        padding: 24px;
      }
      .page-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 16px;
      }
      .page-title {
        margin: 0;
        font-size: 1.5rem;
        font-weight: 600;
      }
      .stats {
        display: flex;
        gap: 16px;
        margin-bottom: 16px;
      }
      .stat {
        padding: 6px 16px;
        border-radius: 20px;
        font-weight: 500;
      }
      .read {
        background: #e8f5e9;
        color: #2e7d32;
      }
      .total {
        background: #f5f5f5;
        color: #555;
      }
      table {
        width: 100%;
      }
    `,
  ],
})
export class ComunicadoLecturasComponent implements OnInit {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);

  lecturas = signal<LecturaRow[]>([]);
  loading = signal(true);
  totalLeidos = signal(0);
  cols = ['nombre', 'email', 'leido', 'fechaLectura'];

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.loadLecturas(Number(id));
  }

  private loadLecturas(id: number): void {
    this.http
      .get<LecturaRow[]>(`${environment.gatewayUrl}/api/application/comunicado/${id}/lecturas`)
      .subscribe({
        next: (data) => {
          const rows = Array.isArray(data) ? data : [];
          this.lecturas.set(rows);
          this.totalLeidos.set(rows.filter((r) => r.leido).length);
          this.loading.set(false);
        },
        error: () => {
          this.lecturas.set([]);
          this.loading.set(false);
        },
      });
  }
}
