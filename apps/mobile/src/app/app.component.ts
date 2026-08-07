import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';
import { AuthService } from './core/services/auth.service';
import { PushNotificationService } from './core/services/push-notification.service';

interface MenuItem {
  title: string;
  icon: string;
  url: string;
}

interface MenuGroup {
  label: string;
  items: MenuItem[];
}

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  standalone: false,
})
export class AppComponent implements OnInit {
  userEmail = '';

  menuGroups: MenuGroup[] = [
    {
      label: '',
      items: [{ title: 'Inicio', icon: 'home-outline', url: '/home' }],
    },
    {
      label: 'Mi espacio',
      items: [
        { title: 'Mi Información', icon: 'person-outline', url: '/my-data' },
        { title: 'Mi Credencial', icon: 'card-outline', url: '/credential' },
        { title: 'Mi CV', icon: 'document-attach-outline', url: '/my-cv' },
      ],
    },
    {
      label: 'Contenido',
      items: [
        { title: 'Comunicados', icon: 'megaphone-outline', url: '/comunicados' },
        { title: 'Biblioteca', icon: 'library-outline', url: '/biblioteca' },
        { title: 'Documentos', icon: 'folder-outline', url: '/documents' },
      ],
    },
    {
      label: 'Beneficios',
      items: [
        { title: 'Beneficios', icon: 'gift-outline', url: '/benefits' },
        { title: 'Seguros', icon: 'shield-checkmark-outline', url: '/insurance' },
      ],
    },
    {
      label: 'RH',
      items: [
        { title: 'Vacaciones', icon: 'calendar-outline', url: '/vacaciones' },
        { title: 'Mesa de Ayuda', icon: 'help-buoy-outline', url: '/tickets' },
        { title: 'Encuestas', icon: 'bar-chart-outline', url: '/encuestas' },
        { title: 'Buzón Confidencial', icon: 'lock-closed-outline', url: '/buzon' },
      ],
    },
    {
      label: 'Más',
      items: [
        { title: 'Onboarding', icon: 'rocket-outline', url: '/onboarding' },
        { title: 'REPSE', icon: 'document-lock-outline', url: '/repse' },
        { title: 'Ayuda', icon: 'help-circle-outline', url: '/help' },
      ],
    },
  ];

  constructor(
    private authService: AuthService,
    private router: Router,
    private menuCtrl: MenuController,
    private pushService: PushNotificationService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe((user) => {
      this.userEmail = user?.email ?? '';
      if (user) {
        this.pushService.initialize();
      }
    });
  }

  navigateTo(url: string): void {
    this.menuCtrl.close();
    this.router.navigate([url]);
  }

  async logout(): Promise<void> {
    this.menuCtrl.close();
    await this.pushService.unregisterToken();
    this.authService.logout();
  }

  getUserInitial(): string {
    return this.userEmail ? this.userEmail.charAt(0).toUpperCase() : 'U';
  }
}
