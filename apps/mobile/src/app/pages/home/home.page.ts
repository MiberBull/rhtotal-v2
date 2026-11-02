import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface Banner {
  idBanner: number;
  name: string;
  image: string;
  url?: string;
}

interface QuickTile {
  icon: string;
  label: string;
  color: string;
  route: string;
}

interface DashboardSummary {
  diasVacaciones: number | null;
  ticketsAbiertos: number | null;
  comunicadosSinLeer: number | null;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: false,
})
export class HomePage implements OnInit {
  banners: Banner[] = [];
  employeeName = '';
  loadingStats = true;

  summary: DashboardSummary = {
    diasVacaciones: null,
    ticketsAbiertos: null,
    comunicadosSinLeer: null,
  };

  tiles: QuickTile[] = [
    { icon: 'card-outline', label: 'Mi Credencial', color: '#6a1b9a', route: '/credential' },
    { icon: 'megaphone-outline', label: 'Comunicados', color: '#1565c0', route: '/comunicados' },
    { icon: 'calendar-outline', label: 'Vacaciones', color: '#2e7d32', route: '/vacaciones' },
    { icon: 'help-buoy-outline', label: 'Mesa de Ayuda', color: '#00838f', route: '/tickets' },
    { icon: 'folder-outline', label: 'Documentos', color: '#e65100', route: '/documents' },
    { icon: 'library-outline', label: 'Biblioteca', color: '#f57f17', route: '/biblioteca' },
    { icon: 'lock-closed-outline', label: 'Buzón', color: '#b71c1c', route: '/buzon' },
    { icon: 'gift-outline', label: 'Beneficios', color: '#e91e63', route: '/benefits' },
    { icon: 'shield-checkmark-outline', label: 'Seguros', color: '#4a148c', route: '/insurance' },
  ];

  constructor(
    private http: HttpClient,
    private router: Router,
    private menuCtrl: MenuController,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.authService.currentUser;
    if (!user) return;
    // Nombre: se usa prefijo del email como fallback hasta que user-service exponga nombre completo
    this.employeeName = user.email.split('@')[0];
    this.loadBanners(user.id);
    this.loadDashboardStats(user.id);
  }

  openMenu(): void {
    this.menuCtrl.open('mainMenu');
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

  private loadBanners(userId: number): void {
    const url = `${environment.gatewayUrl}/api/application/banner/showbanners?user=${userId}`;
    this.http.get<any[]>(url).subscribe({
      next: (data) => {
        // El backend puede retornar List<String> (URLs) o List<BannerTO>
        this.banners = data.map((item, i) =>
          typeof item === 'string' ? { idBanner: i, name: `Banner ${i + 1}`, image: item } : item
        );
      },
      error: () => (this.banners = []),
    });
  }

  private loadDashboardStats(userId: number): void {
    const base = environment.gatewayUrl;

    const vacBalance$ = this.http
      .get<any>(`${base}/api/hr/vacation/balance/${userId}`)
      .pipe(catchError(() => of(null)));

    const tickets$ = this.http
      .get<any[]>(`${base}/api/hr/ticket/employee/${userId}`)
      .pipe(catchError(() => of([])));

    const comunicados$ = this.http
      .get<any[]>(`${base}/api/application/comunicado/list?idUser=${userId}`)
      .pipe(catchError(() => of([])));

    forkJoin([vacBalance$, tickets$, comunicados$]).subscribe(([balance, tickets, comunicados]) => {
      this.summary = {
        diasVacaciones: balance?.diasDisponibles ?? null,
        ticketsAbiertos: tickets
          ? tickets.filter((t: any) =>
              ['ABIERTO', 'EN_PROCESO', 'EN_ESPERA'].includes(t.status ?? t.dsEstatus ?? '')
            ).length
          : null,
        comunicadosSinLeer: comunicados
          ? comunicados.filter((c: any) => !c.leido && !c.read).length
          : null,
      };
      this.loadingStats = false;
    });
  }
}
