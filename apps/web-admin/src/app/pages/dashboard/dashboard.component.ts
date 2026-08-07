import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { HttpClient } from '@angular/common/http';
import { forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { AuthService } from '../../core/auth/auth.service';
import { environment } from '../../../environments/environment';

interface MetricCard {
  icon: string;
  label: string;
  value: number | string;
  color: string;
  bgColor: string;
  route: string;
}

interface QuickAction {
  icon: string;
  label: string;
  route: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule, MatProgressSpinnerModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  private authService = inject(AuthService);
  private http = inject(HttpClient);

  loading = signal(true);
  currentUser = this.authService.currentUser;

  metrics = signal<MetricCard[]>([
    {
      icon: 'people',
      label: 'Colaboradores activos',
      value: '—',
      color: '#005696',
      bgColor: '#e3f0fa',
      route: '/colaboradores',
    },
    {
      icon: 'beach_access',
      label: 'Vacaciones pendientes',
      value: '—',
      color: '#F18A21',
      bgColor: '#fff5e6',
      route: '/vacaciones',
    },
    {
      icon: 'support_agent',
      label: 'Tickets abiertos',
      value: '—',
      color: '#2e7d32',
      bgColor: '#e8f5e9',
      route: '/tickets',
    },
    {
      icon: 'business',
      label: 'Clientes activos',
      value: '—',
      color: '#6a1b9a',
      bgColor: '#f3e5f5',
      route: '/clients',
    },
  ]);

  quickActions: QuickAction[] = [
    { icon: 'person_add', label: 'Nuevo colaborador', route: '/users' },
    { icon: 'campaign', label: 'Nuevo comunicado', route: '/comunicados' },
    { icon: 'upload_file', label: 'Subir documento', route: '/documents/upload' },
    { icon: 'verified', label: 'REPSE', route: '/repse' },
  ];

  ngOnInit(): void {
    this.loadMetrics();
  }

  private loadMetrics(): void {
    const gw = environment.gatewayUrl;

    const headcount$ = this.http
      .get<{ count: number }>(`${gw}/api/user/user/getNumberRow`)
      .pipe(catchError(() => of(null)));

    const vacaciones$ = this.http.get<any[]>(`${gw}/api/hr/vacation/request/pending`).pipe(
      map((list) => ({ count: list?.length ?? 0 })),
      catchError(() => of(null))
    );

    const tickets$ = this.http.get<any[]>(`${gw}/api/hr/ticket/status/ABIERTO`).pipe(
      map((list) => ({ count: list?.length ?? 0 })),
      catchError(() => of(null))
    );

    const clientes$ = this.http
      .get<{ count: number }>(`${gw}/api/application/client/getNumberRow`)
      .pipe(catchError(() => of(null)));

    forkJoin([headcount$, vacaciones$, tickets$, clientes$]).subscribe(
      ([headcount, vacaciones, tickets, clientes]) => {
        this.metrics.update((cards) => [
          { ...cards[0], value: headcount?.count ?? '—' },
          { ...cards[1], value: vacaciones?.count ?? '—' },
          { ...cards[2], value: tickets?.count ?? '—' },
          { ...cards[3], value: clientes?.count ?? '—' },
        ]);
        this.loading.set(false);
      }
    );
  }

  getUserName(): string {
    const email = this.currentUser()?.email;
    if (!email) return 'Admin';
    return email.split('@')[0];
  }

  getGreeting(): string {
    const hour = new Date().getHours();
    if (hour < 12) return 'Buenos días';
    if (hour < 18) return 'Buenas tardes';
    return 'Buenas noches';
  }
}
