import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';
import { forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
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

interface ResumenPersonal {
  diasVacaciones: number;
  ticketsAbiertos: number;
  comunicadosPendientes: number;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: false,
})
export class HomePage implements OnInit {
  banners: Banner[] = [];
  userName = '';
  loadingKpis = true;

  resumen: ResumenPersonal = {
    diasVacaciones: 0,
    ticketsAbiertos: 0,
    comunicadosPendientes: 0,
  };

  tiles: QuickTile[] = [
    { icon: 'gift-outline', label: 'Beneficios', color: '#e91e63', route: '/benefits' },
    { icon: 'briefcase-outline', label: 'RH', color: '#1565c0', route: '/rh' },
    { icon: 'shield-checkmark-outline', label: 'Seguros', color: '#2e7d32', route: '/insurance' },
    { icon: 'card-outline', label: 'Mi Credencial', color: '#6a1b9a', route: '/credential' },
    { icon: 'document-text-outline', label: 'Documentos', color: '#e65100', route: '/documents' },
    { icon: 'help-circle-outline', label: 'Ayuda', color: '#00838f', route: '/help' },
  ];

  constructor(
    private http: HttpClient,
    private router: Router,
    private menuCtrl: MenuController,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.authService.currentUser;
    this.userName = user?.email?.split('@')[0] ?? '';
    this.loadBanners();
    this.loadResumen();
  }

  openMenu(): void {
    this.menuCtrl.open('mainMenu');
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

  private loadBanners(): void {
    const url = `${environment.gatewayUrl}/api/application/banner/showbanners`;
    this.http.get<Banner[]>(url).subscribe({
      next: (data) => (this.banners = data),
      error: () => (this.banners = []),
    });
  }

  private loadResumen(): void {
    const user = this.authService.currentUser;
    const idEmpleado = user?.id ?? 0;
    const gw = environment.gatewayUrl;

    // 1. Dias de vacaciones disponibles
    const vacaciones$ = this.http
      .get<{ nbDaysAvailable: number }>(`${gw}/api/hr/vacation/balance/${idEmpleado}`)
      .pipe(
        map((res) => res?.nbDaysAvailable ?? 0),
        catchError(() => of(0))
      );

    // 2. Tickets abiertos del empleado
    const tickets$ = this.http.get<any[]>(`${gw}/api/hr/ticket/employee/${idEmpleado}`).pipe(
      map((list) =>
        Array.isArray(list) ? list.filter((t) => t?.dsStatus === 'ABIERTO').length : 0
      ),
      catchError(() => of(0))
    );

    // 3. Comunicados no leidos
    const comunicados$ = this.http
      .get<any[]>(`${gw}/api/application/comunicado/list?idUser=${idEmpleado}`)
      .pipe(
        map((list) => (Array.isArray(list) ? list.filter((c) => c?.fgLeido === false).length : 0)),
        catchError(() => of(0))
      );

    forkJoin([vacaciones$, tickets$, comunicados$]).subscribe(
      ([diasVacaciones, ticketsAbiertos, comunicadosPendientes]) => {
        this.resumen = { diasVacaciones, ticketsAbiertos, comunicadosPendientes };
        this.loadingKpis = false;
      }
    );
  }
}
