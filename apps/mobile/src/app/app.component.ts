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

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  standalone: false,
})
export class AppComponent implements OnInit {
  menuItems: MenuItem[] = [
    { title: 'Inicio', icon: 'home-outline', url: '/home' },
    { title: 'Mi Perfil', icon: 'person-outline', url: '/my-data' },
    { title: 'Mi Credencial', icon: 'card-outline', url: '/credential' },
    { title: 'Mi CV', icon: 'document-attach-outline', url: '/my-cv' },
    { title: 'Beneficios', icon: 'gift-outline', url: '/benefits' },
    { title: 'RH', icon: 'briefcase-outline', url: '/rh' },
    { title: 'Seguros', icon: 'shield-checkmark-outline', url: '/insurance' },
    { title: 'Ayuda', icon: 'help-circle-outline', url: '/help' },
    { title: 'Quienes somos', icon: 'information-circle-outline', url: '/about' },
  ];

  userEmail = '';

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
