import { Component, OnInit, inject, signal, computed, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { EmployeeTO, UserApiService } from '@dch/shared';
import { environment } from '../../../environments/environment';

interface EmployeeRow {
  id: number;
  idUser: number;
  name: string;
  lastName: string;
  email: string;
  rfc: string;
  curp: string;
  status: boolean;
  role: string;
}

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatChipsModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatTooltipModule,
  ],
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss',
})
export class UsersListComponent implements OnInit {
  private router = inject(Router);
  private dialog = inject(MatDialog);
  private userApi = inject(UserApiService);
  private destroyRef = inject(DestroyRef);

  // State signals
  employees = signal<EmployeeRow[]>([]);
  loading = signal(true);
  totalCount = signal(0);
  searchTerm = signal('');
  pageIndex = signal(0);
  pageSize = signal(10);

  // Table configuration
  displayedColumns = ['name', 'rfc', 'curp', 'role', 'status', 'actions'];

  // Search debounce
  private searchSubject = new Subject<string>();

  ngOnInit(): void {
    this.userApi.configure({ gatewayUrl: environment.gatewayUrl });

    this.searchSubject
      .pipe(
        debounceTime(400),
        distinctUntilChanged(),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe((term) => {
        this.searchTerm.set(term);
        this.pageIndex.set(0);
        this.loadEmployees();
      });

    this.loadEmployees();
    this.loadCount();
  }

  onSearchChange(value: string): void {
    this.searchSubject.next(value);
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadEmployees();
  }

  onSortChange(sort: Sort): void {
    this.pageIndex.set(0);
    this.loadEmployees();
  }

  navigateToNew(): void {
    this.router.navigate(['/users/new']);
  }

  navigateToEdit(employee: EmployeeRow): void {
    this.router.navigate(['/users', employee.idUser]);
  }

  confirmDelete(employee: EmployeeRow): void {
    import('./confirm-delete-dialog.component').then((m) => {
      const dialogRef = this.dialog.open(m.ConfirmDeleteDialogComponent, {
        width: '400px',
        data: { name: `${employee.name} ${employee.lastName}` },
      });

      dialogRef.afterClosed().subscribe((confirmed) => {
        if (confirmed) {
          this.deleteEmployee(employee);
        }
      });
    });
  }

  getStatusLabel(active: boolean): string {
    return active ? 'Activo' : 'Inactivo';
  }

  private loadEmployees(): void {
    this.loading.set(true);
    const page = this.pageIndex();
    const size = this.pageSize();
    const search = this.searchTerm() || undefined;

    this.userApi.getAllEmployees(page, size, search).subscribe({
      next: (response: any) => {
        const data = response?.content ?? response ?? [];
        const rows: EmployeeRow[] = data.map((emp: any) => ({
          id: emp.id,
          idUser: emp.user?.id ?? emp.idUser ?? emp.id,
          name: emp.name ?? '',
          lastName: emp.lastName ?? '',
          email: emp.user?.email ?? emp.email ?? '',
          rfc: emp.rfc ?? emp.complementary?.rfc ?? '—',
          curp: emp.curp ?? emp.complementary?.curp ?? '—',
          status: emp.status ?? emp.active ?? true,
          role: emp.user?.userType === 'IN' ? 'Empleado' : 'Externo',
        }));
        this.employees.set(rows);
        if (response?.totalElements != null) {
          this.totalCount.set(response.totalElements);
        }
        this.loading.set(false);
      },
      error: () => {
        this.employees.set([]);
        this.loading.set(false);
      },
    });
  }

  private loadCount(): void {
    this.userApi.getCountRow().subscribe({
      next: (res) => {
        this.totalCount.set(res.count ?? 0);
      },
      error: () => {},
    });
  }

  private deleteEmployee(employee: EmployeeRow): void {
    // TODO: Implement actual delete call when endpoint is available
    this.employees.update((list) => list.filter((e) => e.id !== employee.id));
    this.totalCount.update((c) => c - 1);
  }
}
