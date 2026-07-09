import { Component, OnInit, inject, signal } from '@angular/core';
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
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatChipsModule } from '@angular/material/chips';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-ticket-comment-dialog',
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
    <h2 mat-dialog-title>Agregar Comentario — Ticket #{{ data.ticketId }}</h2>
    <mat-dialog-content>
      <p class="ticket-title">{{ data.ticketTitle }}</p>
      <mat-form-field appearance="outline" style="width:100%">
        <mat-label>Comentario</mat-label>
        <textarea
          matInput
          [(ngModel)]="comment"
          rows="4"
          placeholder="Escribe tu respuesta..."
        ></textarea>
      </mat-form-field>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button
        mat-flat-button
        color="primary"
        [mat-dialog-close]="{ comment, status }"
        [disabled]="!comment.trim()"
      >
        Enviar
      </button>
    </mat-dialog-actions>
  `,
  styles: ['.ticket-title { font-size: 0.875rem; color: #616161; margin-bottom: 12px; }'],
})
export class TicketCommentDialogComponent {
  data = inject(MAT_DIALOG_DATA);
  comment = '';
  status = '';
}

@Component({
  selector: 'app-tickets',
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
    MatChipsModule,
  ],
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss',
})
export class TicketsComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  readonly tabs: { label: string; status: string }[] = [
    { label: 'Abiertos', status: 'ABIERTO' },
    { label: 'En Proceso', status: 'EN_PROCESO' },
    { label: 'Resueltos', status: 'RESUELTO' },
    { label: 'Cerrados', status: 'CERRADO' },
  ];

  activeStatus = signal('ABIERTO');
  tickets = signal<any[]>([]);
  loading = signal(true);
  totalCount = signal(0);
  pageIndex = signal(0);
  pageSize = signal(10);

  displayedColumns = [
    'folio',
    'employee',
    'category',
    'title',
    'priority',
    'createdAt',
    'status',
    'actions',
  ];

  ngOnInit(): void {
    this.loadTickets();
  }

  onTabChange(index: number): void {
    this.activeStatus.set(this.tabs[index].status);
    this.pageIndex.set(0);
    this.loadTickets();
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadTickets();
  }

  openCommentDialog(ticket: any): void {
    const ref = this.dialog.open(TicketCommentDialogComponent, {
      width: '500px',
      data: { ticketId: ticket.id, ticketTitle: ticket.dsTitle },
    });

    ref.afterClosed().subscribe((result) => {
      if (!result?.comment?.trim()) return;
      // Backend: POST /ticket/comment con idTicket en body (no en path)
      this.http
        .post(`${environment.gatewayUrl}/api/hr/ticket/comment`, {
          idTicket: ticket.id,
          dsComment: result.comment,
        })
        .subscribe({
          next: () => {
            this.snackBar.open('Comentario enviado', 'Cerrar', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
            });
            this.loadTickets();
          },
          error: () =>
            this.snackBar.open('Error al enviar el comentario', 'Cerrar', {
              duration: 4000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
            }),
        });
    });
  }

  updateStatus(ticket: any, newStatus: string): void {
    // Backend espera newStatus como query param, no en body
    this.http
      .put(`${environment.gatewayUrl}/api/hr/ticket/${ticket.id}/status`, null, {
        params: { newStatus },
      })
      .subscribe({
        next: () => {
          this.snackBar.open(`Ticket marcado como ${newStatus}`, 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadTickets();
        },
        error: () =>
          this.snackBar.open('Error al actualizar el ticket', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
  }

  getPriorityClass(priority: string): string {
    switch (priority) {
      case 'ALTA':
        return 'priority-high';
      case 'MEDIA':
        return 'priority-medium';
      case 'BAJA':
        return 'priority-low';
      default:
        return '';
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'ABIERTO':
        return 'status-open';
      case 'EN_PROCESO':
        return 'status-inprogress';
      case 'RESUELTO':
        return 'status-resolved';
      case 'CERRADO':
        return 'status-closed';
      default:
        return '';
    }
  }

  private loadTickets(): void {
    this.loading.set(true);
    const status = this.activeStatus();
    const page = this.pageIndex();
    const size = this.pageSize();

    this.http
      .get<any>(`${environment.gatewayUrl}/api/hr/ticket/status/${status}`, {
        params: { page: page.toString(), size: size.toString() },
      })
      .subscribe({
        next: (res) => {
          const data = Array.isArray(res) ? res : (res?.content ?? []);
          this.tickets.set(data);
          this.totalCount.set(res?.totalElements ?? data.length);
          this.loading.set(false);
        },
        error: () => {
          this.tickets.set([]);
          this.loading.set(false);
        },
      });
  }
}
