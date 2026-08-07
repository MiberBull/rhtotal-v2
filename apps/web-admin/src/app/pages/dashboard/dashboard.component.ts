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

interface KpiCard {
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

  kpis = signal<KpiCard[]>([
    {
      icon: 'people',
      label: 'Colaboradores activos',
      value: '—',
      color: '#005696',
      bgColor: '#e3f0fa',
      route: '/colaboradores',
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
      icon: 'beach_access',
      label: 'Vacaciones pendientes',
      value: '—',
      color: '#F18A21',
      bgColor: '#fff5e6',
      route: '/vacaciones',
    },
    {
      icon: 'mark_email_unread',
      label: 'Reportes buzon nuevos',
      value: '—',
      color: '#6a1b9a',
      bgColor: '#f3e5f5',
      route: '/buzon',
    },
  ]);

  quickActions: QuickAction[] = [
    { icon: 'person_add', label: 'Nuevo colaborador', route: '/users' },
    { icon: 'campaign', label: 'Nuevo comunicado', route: '/comunicados' },
    { icon: 'upload_file', label: 'Subir documento', route: '/documents/upload' },
    { icon: 'verified', label: 'REPSE', route: '/repse' },
  ];

  ngOnInit(): void {
    this.loadKpis();
  }

  private loadKpis(): void {
    const gw = environment.gatewayUrl;

    // 1. Headcount: GET /api/user/employee/list → array
    const colaboradores$ = this.http.get<any[]>(`${gw}/api/user/employee/list`).pipe(
      map((list) => (Array.isArray(list) ? list.length : ((list as any)?.count ?? 0))),
      catchError(() => of(0))
    );

    // 2. Tickets abiertos: GET /api/hr/ticket/status/ABIERTO → array
    const tickets$ = this.http.get<any[]>(`${gw}/api/hr/ticket/status/ABIERTO`).pipe(
      map((list) => (Array.isArray(list) ? list.length : 0)),
      catchError(() => of(0))
    );

    // 3. Vacaciones pendientes: GET /api/hr/vacation/request/status/PENDIENTE → array
    const vacaciones$ = this.http.get<any[]>(`${gw}/api/hr/vacation/request/status/PENDIENTE`).pipe(
      map((list) => (Array.isArray(list) ? list.length : 0)),
      catchError(() => of(0))
    );

    // 4. Buzon nuevos: GET /api/hr/buzon/list → array, filtrar dsEstatus === 'NUEVO'
    const buzon$ = this.http.get<any[]>(`${gw}/api/hr/buzon/list`).pipe(
      map((list) =>
        Array.isArray(list) ? list.filter((r) => r?.dsEstatus === 'NUEVO').length : 0
      ),
      catchError(() => of(0))
    );

    forkJoin([colaboradores$, tickets$, vacaciones$, buzon$]).subscribe(
      ([colaboradores, tickets, vacaciones, buzonNuevos]) => {
        this.kpis.update((cards) => [
          { ...cards[0], value: colaboradores },
          { ...cards[1], value: tickets },
          { ...cards[2], value: vacaciones },
          { ...cards[3], value: buzonNuevos },
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
    if (hour < 12) return 'Buenos dias';
    if (hour < 18) return 'Buenas tardes';
    return 'Buenas noches';
  }
}
