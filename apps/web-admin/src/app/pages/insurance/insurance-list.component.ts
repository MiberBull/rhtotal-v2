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
import { MatTooltipModule } from '@angular/material/tooltip';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-insurance-list',
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
    MatTooltipModule,
  ],
  templateUrl: './insurance-list.component.html',
  styleUrl: './insurance-list.component.scss',
})
export class InsuranceListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);

  insurances = signal<any[]>([]);
  loading = signal(true);

  displayedColumns = ['name', 'type', 'provider', 'status', 'actions'];

  ngOnInit(): void {
    this.loadInsurances();
  }

  navigateToNew(): void {
    this.router.navigate(['/insurance/new']);
  }

  navigateToEdit(insurance: any): void {
    this.router.navigate(['/insurance', insurance.idInsurance ?? insurance.id]);
  }

  getTypeLabel(type: string): string {
    switch (type?.toUpperCase()) {
      case 'AUTO':
        return 'Auto';
      case 'VIDA':
        return 'Vida';
      case 'GASTOS_MEDICOS':
        return 'Gastos Medicos';
      default:
        return type ?? '—';
    }
  }

  getTypeClass(type: string): string {
    switch (type?.toUpperCase()) {
      case 'AUTO':
        return 'type-auto';
      case 'VIDA':
        return 'type-vida';
      case 'GASTOS_MEDICOS':
        return 'type-gastos';
      default:
        return 'type-auto';
    }
  }

  getStatusLabel(status: string): string {
    return status === 'A' || status === 'ACTIVE' ? 'Activo' : 'Inactivo';
  }

  isActive(status: string): boolean {
    return status === 'A' || status === 'ACTIVE';
  }

  private loadInsurances(): void {
    this.loading.set(true);
    this.http
      .get<any[]>(`${environment.gatewayUrl}/api/application/insurance/getAllInsurance`)
      .subscribe({
        next: (data) => {
          this.insurances.set(data ?? []);
          this.loading.set(false);
        },
        error: () => {
          this.insurances.set([]);
          this.loading.set(false);
        },
      });
  }
}
