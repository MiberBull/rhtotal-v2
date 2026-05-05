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

import { ClientTO } from '@dch/shared';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-clients-list',
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
  ],
  templateUrl: './clients-list.component.html',
  styleUrl: './clients-list.component.scss',
})
export class ClientsListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  clients = signal<ClientTO[]>([]);
  loading = signal(true);

  displayedColumns = ['name', 'rfc', 'phone', 'email', 'status', 'actions'];

  ngOnInit(): void {
    this.loadClients();
  }

  navigateToNew(): void {
    this.router.navigate(['/clients/new']);
  }

  navigateToEdit(client: ClientTO): void {
    this.router.navigate(['/clients', client.idClient]);
  }

  confirmDelete(client: ClientTO): void {
    import('./confirm-delete-client-dialog.component').then((m) => {
      const dialogRef = this.dialog.open(m.ConfirmDeleteClientDialogComponent, {
        width: '400px',
        data: { name: client.name },
      });

      dialogRef.afterClosed().subscribe((confirmed) => {
        if (confirmed) {
          this.deleteClient(client);
        }
      });
    });
  }

  getStatusLabel(status: string): string {
    return status === 'A' ? 'Activo' : 'Inactivo';
  }

  private loadClients(): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/client/getAllClients`)
      .subscribe({
        next: (response) => {
          const data = Array.isArray(response) ? response : response?.content ?? [];
          this.clients.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.clients.set([]);
          this.loading.set(false);
        },
      });
  }

  private deleteClient(client: ClientTO): void {
    this.http
      .post(`${environment.gatewayUrl}/api/application/client/saveOrUpdateClient`, {
        ...client,
        status: 'I',
      })
      .subscribe({
        next: () => {
          this.clients.update((list) => list.filter((c) => c.idClient !== client.idClient));
        },
        error: () => {},
      });
  }
}
