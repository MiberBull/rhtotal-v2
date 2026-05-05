import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';
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

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: false,
})
export class HomePage implements OnInit {
  banners: Banner[] = [];
  userName = '';

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
}
