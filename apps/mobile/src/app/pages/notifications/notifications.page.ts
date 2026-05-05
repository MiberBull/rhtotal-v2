import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AlertController, LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface Notification {
  idNotification: number;
  title: string;
  message: string;
  date?: string;
  read?: boolean;
}

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.page.html',
  styleUrls: ['./notifications.page.scss'],
  standalone: false,
})
export class NotificationsPage implements OnInit {
  notifications: Notification[] = [];

  constructor(
    private http: HttpClient,
    private alertCtrl: AlertController,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadNotifications();
  }

  async openNotification(notification: Notification): Promise<void> {
    // Mark as read
    if (!notification.read) {
      this.markAsRead(notification);
    }

    // Show full message
    const alert = await this.alertCtrl.create({
      header: notification.title,
      message: notification.message,
      buttons: ['Cerrar'],
    });
    await alert.present();
  }

  private async loadNotifications(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando notificaciones...' });
    await loading.present();

    const user = this.authService.currentUser;
    const url = `${environment.gatewayUrl}/api/application/notification/notificationUser/${user?.id}`;

    this.http.get<Notification[]>(url).subscribe({
      next: (data) => {
        this.notifications = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar notificaciones',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }

  private markAsRead(notification: Notification): void {
    const url = `${environment.gatewayUrl}/api/application/notification/readNotification/${notification.idNotification}`;
    this.http.post(url, {}).subscribe({
      next: () => {
        notification.read = true;
      },
      error: () => {
        // Silent fail - notification still shown
      },
    });
  }
}
