import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Platform } from '@ionic/angular';
import { PushNotifications, Token, PushNotificationSchema } from '@capacitor/push-notifications';
import { environment } from '../../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class PushNotificationService {
  private registered = false;

  constructor(
    private http: HttpClient,
    private platform: Platform,
    private authService: AuthService
  ) {}

  async initialize(): Promise<void> {
    if (!this.platform.is('capacitor')) {
      console.log('Push notifications only work on native devices');
      return;
    }

    const permission = await PushNotifications.requestPermissions();
    if (permission.receive !== 'granted') {
      console.warn('Push notification permission denied');
      return;
    }

    await PushNotifications.register();
    this.addListeners();
  }

  private addListeners(): void {
    PushNotifications.addListener('registration', (token: Token) => {
      console.log('FCM Token:', token.value);
      this.registerTokenOnServer(token.value);
    });

    PushNotifications.addListener('registrationError', (error) => {
      console.error('Push registration error:', error);
    });

    PushNotifications.addListener(
      'pushNotificationReceived',
      (notification: PushNotificationSchema) => {
        console.log('Push received:', notification);
      }
    );

    PushNotifications.addListener('pushNotificationActionPerformed', (action) => {
      console.log('Push action:', action);
    });
  }

  private registerTokenOnServer(token: string): void {
    const user = this.authService.currentUser;
    if (!user) return;

    const url = `${environment.gatewayUrl}/api/application/notification/setUserToken`;
    this.http
      .post(url, { idUser: user.id, token, platform: this.getPlatform() })
      .subscribe({
        next: () => {
          this.registered = true;
          console.log('Token registered on server');
        },
        error: (err) => console.error('Failed to register token:', err),
      });
  }

  async unregisterToken(): Promise<void> {
    const user = this.authService.currentUser;
    if (!user || !this.registered) return;

    const url = `${environment.gatewayUrl}/api/application/notification/inactiveUserToken`;
    this.http.post(url, { idUser: user.id }).subscribe({
      next: () => (this.registered = false),
      error: (err) => console.error('Failed to unregister token:', err),
    });
  }

  private getPlatform(): string {
    if (this.platform.is('ios')) return 'ios';
    if (this.platform.is('android')) return 'android';
    return 'web';
  }
}
