import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

interface ContratingData {
  position: string;
  area: string;
  department: string;
  startDate: string;
  contractType: string;
  directManager: string;
}

@Component({
  selector: 'app-position',
  templateUrl: './position.page.html',
  styleUrls: ['./position.page.scss'],
  standalone: false,
})
export class PositionPage implements OnInit {
  data: ContratingData | null = null;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  async loadData(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/getContrating/${userId}`;

    this.http.get<ContratingData>(url).subscribe({
      next: (response) => {
        this.data = response;
        loading.dismiss();
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al cargar datos de posicion', 'danger');
      },
    });
  }

  private async showToast(message: string, color: string): Promise<void> {
    const toast = await this.toastCtrl.create({
      message,
      color,
      duration: 2500,
      position: 'bottom',
    });
    await toast.present();
  }
}
