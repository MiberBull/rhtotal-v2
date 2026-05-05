import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface Insurance {
  idInsurance: number;
  name: string;
  type?: string;
  provider?: string;
  description?: string;
}

@Component({
  selector: 'app-insurance-list',
  templateUrl: './insurance-list.page.html',
  styleUrls: ['./insurance-list.page.scss'],
  standalone: false,
})
export class InsuranceListPage implements OnInit {
  insurances: Insurance[] = [];

  constructor(
    private http: HttpClient,
    private router: Router,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadInsurances();
  }

  getTypeBadgeColor(type?: string): string {
    switch (type?.toLowerCase()) {
      case 'auto':
        return '#1565c0';
      case 'vida':
        return '#2e7d32';
      case 'gastos':
      case 'gastos medicos':
        return '#6a1b9a';
      default:
        return '#424242';
    }
  }

  navigateToDetail(insurance: Insurance): void {
    this.router.navigate(['/insurance/detail', insurance.idInsurance]);
  }

  private async loadInsurances(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando seguros...' });
    await loading.present();

    const user = this.authService.currentUser;
    const url = `${environment.gatewayUrl}/api/application/insurance/getInsuranseUser/${user?.id}`;

    this.http.get<Insurance[]>(url).subscribe({
      next: (data) => {
        this.insurances = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar seguros',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
