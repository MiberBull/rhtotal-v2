import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-notifications-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatTooltipModule,
    MatSnackBarModule,
  ],
  templateUrl: './notifications-list.component.html',
  styleUrl: './notifications-list.component.scss',
})
export class NotificationsListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);

  notifications = signal<any[]>([]);
  loading = signal(true);

  displayedColumns = ['title', 'message', 'type', 'dateCreated', 'status', 'actions'];

  ngOnInit(): void {
    this.loadNotifications();
  }

  navigateToNew(): void {
    this.router.navigate(['/notifications/new']);
  }

  navigateToEdit(notification: any): void {
    this.router.navigate(['/notifications', notification.idNotification]);
  }

  navigateToAssign(notification: any): void {
    this.router.navigate(['/notifications', notification.idNotification, 'assign']);
  }

  confirmDelete(notification: any): void {
    if (confirm(`¿Estas seguro de eliminar la notificacion "${notification.title}"?`)) {
      this.deleteNotification(notification);
    }
  }

  getStatusLabel(status: string): string {
    return status === 'A' ? 'Activa' : 'Inactiva';
  }

  getTypeLabel(type: string): string {
    switch (type) {
      case 'PUSH': return 'Push';
      case 'EMAIL': return 'Email';
      case 'BOTH': return 'Ambos';
      default: return type || '—';
    }
  }

  truncateMessage(message: string): string {
    if (!message) return '—';
    return message.length > 60 ? message.substring(0, 60) + '...' : message;
  }

  private loadNotifications(): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/notification/getAllNotifications`)
      .subscribe({
        next: (response) => {
          const data = Array.isArray(response) ? response : response?.content ?? [];
          this.notifications.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.notifications.set([]);
          this.loading.set(false);
        },
      });
  }

  private deleteNotification(notification: any): void {
    this.http
      .post(`${environment.gatewayUrl}/api/application/notification/saveOrUpdateNotification`, {
        ...notification,
        status: 'I',
      })
      .subscribe({
        next: () => {
          this.notifications.update((list) =>
            list.filter((n) => n.idNotification !== notification.idNotification)
          );
          this.snackBar.open('Notificacion eliminada', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
        error: () => {
          this.snackBar.open('Error al eliminar la notificacion', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
      });
  }
}
