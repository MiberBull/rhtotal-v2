import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AuthService } from '../../core/auth/auth.service';
import { UserApiService } from '@dch/shared';
import { environment } from '../../../environments/environment';

interface MetricCard {
  icon: string;
  label: string;
  value: number | string;
  color: string;
  bgColor: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatProgressSpinnerModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  private authService = inject(AuthService);
  private userApi = inject(UserApiService);

  loading = signal(true);
  currentUser = this.authService.currentUser;

  metrics = signal<MetricCard[]>([
    { icon: 'people', label: 'Empleados activos', value: '—', color: '#1565c0', bgColor: '#e3f2fd' },
    { icon: 'pending_actions', label: 'Solicitudes pendientes', value: '—', color: '#e65100', bgColor: '#fff3e0' },
    { icon: 'description', label: 'Documentos', value: '—', color: '#2e7d32', bgColor: '#e8f5e9' },
    { icon: 'business', label: 'Clientes activos', value: '—', color: '#6a1b9a', bgColor: '#f3e5f5' },
  ]);

  ngOnInit(): void {
    this.userApi.configure({ gatewayUrl: environment.gatewayUrl });
    this.loadMetrics();
  }

  private loadMetrics(): void {
    this.userApi.getCountRow().subscribe({
      next: (response) => {
        const current = this.metrics();
        current[0] = { ...current[0], value: response.count ?? 0 };
        this.metrics.set([...current]);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
      },
    });
  }

  getUserName(): string {
    const email = this.currentUser()?.email;
    if (!email) return 'Admin';
    return email.split('@')[0];
  }

  getGreeting(): string {
    const hour = new Date().getHours();
    if (hour < 12) return 'Buenos dias';
    if (hour < 18) return 'Buenas tardes';
    return 'Buenas noches';
  }
}
