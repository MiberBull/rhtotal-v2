import { Component, OnInit, inject, signal, computed, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { AuthService } from '../../core/auth/auth.service';
import { environment } from '../../../environments/environment';

interface ColaboradorRow {
  idUser: number;
  dsEmployeeNumber: string;
  fullName: string;
  dsJobTitle: string;
  dsArea: string;
  tenantId: string;
  dsClientName: string;
  dsProjectName: string;
  fgActive: boolean;
}

@Component({
  selector: 'app-colaboradores',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatButtonModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
  ],
  templateUrl: './colaboradores.component.html',
  styleUrl: './colaboradores.component.scss',
})
export class ColaboradoresComponent implements OnInit {
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  colaboradores = signal<ColaboradorRow[]>([]);
  loading = signal(true);
  totalCount = signal(0);
  searchTerm = signal('');
  pageIndex = signal(0);
  pageSize = signal(10);
  selectedTenant = signal<string>('ALL');

  canSwitchTenant = this.authService.canSwitchTenant;
  availableTenants = this.authService.availableTenants;

  displayedColumns = [
    'employeeNumber',
    'fullName',
    'jobTitle',
    'area',
    'tenant',
    'assignment',
    'status',
    'actions',
  ];

  filteredColaboradores = computed(() => {
    const term = this.searchTerm().toLowerCase();
    if (!term) return this.colaboradores();
    return this.colaboradores().filter(
      (c) =>
        c.fullName.toLowerCase().includes(term) ||
        c.dsEmployeeNumber.toLowerCase().includes(term) ||
        c.dsJobTitle.toLowerCase().includes(term)
    );
  });

  private searchSubject = new Subject<string>();

  private get apiHeaders(): Record<string, string> {
    const h: Record<string, string> = {
      Authorization: `Bearer ${this.authService.token()}`,
    };
    const tid =
      this.selectedTenant() !== 'ALL' ? this.selectedTenant() : this.authService.tenantId();
    if (tid && tid !== 'ALL') h['X-Tenant-ID'] = tid;
    return h;
  }

  ngOnInit(): void {
    this.selectedTenant.set(this.authService.tenantId() || 'ALL');

    this.searchSubject
      .pipe(debounceTime(400), distinctUntilChanged(), takeUntilDestroyed(this.destroyRef))
      .subscribe((term) => {
        this.searchTerm.set(term);
        this.pageIndex.set(0);
      });

    this.loadColaboradores();
  }

  onSearchChange(value: string): void {
    this.searchSubject.next(value);
  }

  onTenantChange(tenantId: string): void {
    this.selectedTenant.set(tenantId);
    this.pageIndex.set(0);
    this.loadColaboradores();
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadColaboradores();
  }

  navigateToDetalle(row: ColaboradorRow): void {
    this.router.navigate(['/colaboradores', row.idUser]);
  }

  navigateToNuevo(): void {
    this.router.navigate(['/users/new']);
  }

  private loadColaboradores(): void {
    this.loading.set(true);

    const url = `${environment.gatewayUrl}/api/user/user/getAllEmployees`;

    this.http.get<any>(url, { headers: this.apiHeaders }).subscribe({
      next: (response) => {
        const data: any[] = response?.content ?? response ?? [];
        const rows: ColaboradorRow[] = data.map((emp: any) => ({
          idUser: emp.idUser ?? emp.user?.id ?? emp.id,
          dsEmployeeNumber: emp.dsEmployeeNumber ?? emp.employeeNumber ?? '—',
          fullName: [
            emp.dsName ?? emp.name ?? '',
            emp.dsLastName ?? emp.lastName ?? '',
            emp.dsLastMName ?? emp.lastMName ?? '',
          ]
            .filter(Boolean)
            .join(' '),
          dsJobTitle: emp.complementary?.dsJobTitle ?? emp.dsJobTitle ?? '—',
          dsArea: emp.complementary?.dsArea ?? emp.dsArea ?? '—',
          tenantId: emp.tenantId ?? '—',
          dsClientName: emp.assignment?.dsClientName ?? emp.dsClientName ?? '—',
          dsProjectName: emp.assignment?.dsProjectName ?? emp.dsProjectName ?? '—',
          fgActive: emp.fgActive ?? emp.active ?? emp.status ?? true,
        }));
        this.colaboradores.set(rows);
        if (response?.totalElements != null) {
          this.totalCount.set(response.totalElements);
        } else {
          this.totalCount.set(rows.length);
        }
        this.loading.set(false);
      },
      error: () => {
        this.colaboradores.set([]);
        this.loading.set(false);
      },
    });
  }

  getInitials(fullName: string): string {
    const parts = fullName.trim().split(' ');
    if (parts.length === 0) return 'C';
    if (parts.length === 1) return parts[0].charAt(0).toUpperCase();
    return (parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
  }

  getTenantLabel(tenantId: string): string {
    const t = this.availableTenants.find((x) => x.id === tenantId);
    return t?.label ?? tenantId;
  }
}
