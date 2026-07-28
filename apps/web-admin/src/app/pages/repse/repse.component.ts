import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-repse',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatTabsModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatSelectModule,
    MatCardModule,
  ],
  templateUrl: './repse.component.html',
  styleUrl: './repse.component.scss',
})
export class RepseComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  // Dashboard KPIs
  kpis = signal<{ verde: number; amarillo: number; rojo: number; total: number } | null>(null);
  kpiLoading = signal(true);

  // Expiring profiles
  expiring = signal<any[]>([]);
  expiringLoading = signal(true);

  // Semáforo table
  profiles = signal<any[]>([]);
  profilesLoading = signal(true);

  // Export
  exportLoading = signal(false);
  pdfLoading = signal(false);

  // Period filter
  selectedPeriod = signal('');
  periods = this.buildPeriods();

  displayedProfileColumns = ['company', 'rfc', 'expiry', 'status', 'semaforo', 'actions'];
  displayedExpiringColumns = ['company', 'rfc', 'expiry', 'daysLeft', 'semaforo'];

  ngOnInit(): void {
    const now = new Date();
    this.selectedPeriod.set(`${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`);
    this.loadDashboard();
    this.loadExpiring();
    this.loadProfiles();
  }

  onPeriodChange(period: string): void {
    this.selectedPeriod.set(period);
    this.loadProfiles();
  }

  exportExcel(): void {
    this.exportLoading.set(true);
    this.http
      .get(`${environment.gatewayUrl}/api/application/repse/compliance/export`, {
        params: { period: this.selectedPeriod() },
        responseType: 'blob',
      })
      .subscribe({
        next: (blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `repse_cumplimiento_${this.selectedPeriod()}.xlsx`;
          a.click();
          URL.revokeObjectURL(url);
          this.exportLoading.set(false);
        },
        error: () => {
          this.snackBar.open('Error al exportar Excel', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.exportLoading.set(false);
        },
      });
  }

  exportPdf(): void {
    this.pdfLoading.set(true);
    this.http
      .get(`${environment.gatewayUrl}/api/application/repse/compliance/report`, {
        params: { period: this.selectedPeriod() },
        responseType: 'blob',
      })
      .subscribe({
        next: (blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `repse_reporte_${this.selectedPeriod()}.pdf`;
          a.click();
          URL.revokeObjectURL(url);
          this.pdfLoading.set(false);
        },
        error: () => {
          this.snackBar.open('Error al exportar PDF', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.pdfLoading.set(false);
        },
      });
  }

  getSemaforoClass(semaforo: string): string {
    switch (semaforo) {
      case 'VERDE':
        return 'semaforo-verde';
      case 'AMARILLO':
        return 'semaforo-amarillo';
      case 'ROJO':
        return 'semaforo-rojo';
      default:
        return '';
    }
  }

  private loadDashboard(): void {
    this.kpiLoading.set(true);
    // Backend requiere period como query param
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/repse/compliance/dashboard`, {
        params: { period: this.selectedPeriod() },
      })
      .subscribe({
        next: (res) => {
          this.kpis.set({
            verde: res.verde ?? 0,
            amarillo: res.amarillo ?? 0,
            rojo: res.rojo ?? 0,
            total: res.total ?? 0,
          });
          this.kpiLoading.set(false);
        },
        error: () => this.kpiLoading.set(false),
      });
  }

  private loadExpiring(): void {
    this.expiringLoading.set(true);
    this.http
      .get<any[]>(`${environment.gatewayUrl}/api/application/repse/compliance/expiring`)
      .subscribe({
        next: (res) => {
          this.expiring.set(Array.isArray(res) ? res : []);
          this.expiringLoading.set(false);
        },
        error: () => this.expiringLoading.set(false),
      });
  }

  private loadProfiles(): void {
    this.profilesLoading.set(true);
    const base = `${environment.gatewayUrl}/api/application/repse/compliance/semaforo`;
    // Backend: GET /semaforo/{semaforo} (path variable, no query param)
    // Combinamos los 3 estados para la tabla completa
    forkJoin([
      this.http.get<any[]>(`${base}/VERDE`),
      this.http.get<any[]>(`${base}/AMARILLO`),
      this.http.get<any[]>(`${base}/ROJO`),
    ]).subscribe({
      next: ([verde, amarillo, rojo]) => {
        this.profiles.set([
          ...(Array.isArray(verde) ? verde : []),
          ...(Array.isArray(amarillo) ? amarillo : []),
          ...(Array.isArray(rojo) ? rojo : []),
        ]);
        this.profilesLoading.set(false);
      },
      error: () => this.profilesLoading.set(false),
    });
  }

  private buildPeriods(): { value: string; label: string }[] {
    const periods = [];
    const now = new Date();
    for (let i = 0; i < 12; i++) {
      const d = new Date(now.getFullYear(), now.getMonth() - i, 1);
      const value = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`;
      const label = d.toLocaleDateString('es-MX', { month: 'long', year: 'numeric' });
      periods.push({ value, label: label.charAt(0).toUpperCase() + label.slice(1) });
    }
    return periods;
  }
}
