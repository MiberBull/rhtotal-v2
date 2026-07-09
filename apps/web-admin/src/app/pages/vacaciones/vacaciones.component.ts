import { Component, OnInit, inject, signal, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import {
  MatDialog,
  MatDialogModule,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-reject-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
  ],
  template: `
    <h2 mat-dialog-title>Rechazar Solicitud</h2>
    <mat-dialog-content>
      <p>Ingresa el motivo del rechazo (opcional):</p>
      <mat-form-field appearance="outline" style="width:100%">
        <mat-label>Comentario</mat-label>
        <textarea matInput [(ngModel)]="comment" rows="3"></textarea>
      </mat-form-field>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button mat-flat-button color="warn" [mat-dialog-close]="comment">Rechazar</button>
    </mat-dialog-actions>
  `,
})
export class RejectDialogComponent {
  comment = '';
}

@Component({
  selector: 'app-vacaciones',
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
  ],
  templateUrl: './vacaciones.component.html',
  styleUrl: './vacaciones.component.scss',
})
export class VacacionesComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);
  private destroyRef = inject(DestroyRef);

  activeTab = signal<'PENDIENTE' | 'APROBADA' | 'RECHAZADA'>('PENDIENTE');
  requests = signal<any[]>([]);
  loading = signal(true);
  totalCount = signal(0);
  pageIndex = signal(0);
  pageSize = signal(10);

  displayedColumns = ['employee', 'days', 'dateFrom', 'dateTo', 'requestDate', 'status', 'actions'];

  ngOnInit(): void {
    this.loadRequests();
  }

  onTabChange(index: number): void {
    const tabs: ('PENDIENTE' | 'APROBADA' | 'RECHAZADA')[] = ['PENDIENTE', 'APROBADA', 'RECHAZADA'];
    this.activeTab.set(tabs[index]);
    this.pageIndex.set(0);
    this.loadRequests();
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadRequests();
  }

  approve(request: any): void {
    this.http
      .put(`${environment.gatewayUrl}/api/hr/vacation/request/${request.id}/approve`, null, {
        params: { approvedBy: 'sistema' },
      })
      .subscribe({
        next: () => {
          this.snackBar.open('Solicitud aprobada', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadRequests();
        },
        error: () =>
          this.snackBar.open('Error al aprobar', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
  }

  reject(request: any): void {
    const ref = this.dialog.open(RejectDialogComponent, { width: '400px' });
    ref.afterClosed().subscribe((comment) => {
      if (comment === undefined) return;
      this.http
        .put(`${environment.gatewayUrl}/api/hr/vacation/request/${request.id}/reject`, null, {
          params: { approvedBy: 'sistema', reason: comment || 'Sin comentario' },
        })
        .subscribe({
          next: () => {
            this.snackBar.open('Solicitud rechazada', 'Cerrar', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
            });
            this.loadRequests();
          },
          error: () =>
            this.snackBar.open('Error al rechazar', 'Cerrar', {
              duration: 4000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
            }),
        });
    });
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

  private loadRequests(): void {
    this.loading.set(true);
    const status = this.activeTab();
    const page = this.pageIndex();
    const size = this.pageSize();

    // Backend: PENDIENTE → /request/pending; otros → /request/status/{status}
    const url =
      status === 'PENDIENTE'
        ? `${environment.gatewayUrl}/api/hr/vacation/request/pending`
        : `${environment.gatewayUrl}/api/hr/vacation/request/status/${status}`;

    this.http
      .get<any>(url, {
        params: { page: page.toString(), size: size.toString() },
      })
      .subscribe({
        next: (res) => {
          const data = Array.isArray(res) ? res : (res?.content ?? []);
          this.requests.set(data);
          this.totalCount.set(res?.totalElements ?? data.length);
          this.loading.set(false);
        },
        error: () => {
          this.requests.set([]);
          this.loading.set(false);
        },
      });
  }
}
