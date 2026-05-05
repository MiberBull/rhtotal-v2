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

import { RoleTO } from '@dch/shared';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-roles-list',
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
  templateUrl: './roles-list.component.html',
  styleUrl: './roles-list.component.scss',
})
export class RolesListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  roles = signal<RoleTO[]>([]);
  loading = signal(true);

  displayedColumns = ['name', 'description', 'status', 'actions'];

  ngOnInit(): void {
    this.loadRoles();
  }

  navigateToNew(): void {
    this.router.navigate(['/roles/new']);
  }

  navigateToEdit(role: RoleTO): void {
    this.router.navigate(['/roles', role.idRole]);
  }

  confirmDelete(role: RoleTO): void {
    import('./confirm-delete-role-dialog.component').then((m) => {
      const dialogRef = this.dialog.open(m.ConfirmDeleteRoleDialogComponent, {
        width: '400px',
        data: { name: role.name },
      });

      dialogRef.afterClosed().subscribe((confirmed) => {
        if (confirmed) {
          this.deleteRole(role);
        }
      });
    });
  }

  getStatusLabel(status: string): string {
    return status === 'A' ? 'Activo' : 'Inactivo';
  }

  private loadRoles(): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/security/role/getAllRoles`)
      .subscribe({
        next: (response) => {
          const data = Array.isArray(response) ? response : response?.content ?? [];
          this.roles.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.roles.set([]);
          this.loading.set(false);
        },
      });
  }

  private deleteRole(role: RoleTO): void {
    this.http
      .post(`${environment.gatewayUrl}/api/security/role/saveOrUpdateRole`, {
        ...role,
        status: 'I',
      })
      .subscribe({
        next: () => {
          this.roles.update((list) => list.filter((r) => r.idRole !== role.idRole));
        },
        error: () => {},
      });
  }
}
