import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { environment } from '../../../environments/environment';

interface CompanyInfo {
  idCompanyInformation?: number;
  name?: string;
  phone?: string;
  email?: string;
  address?: string;
  website?: string;
  mission?: string;
  vision?: string;
  values?: string;
  logo?: string;
}

@Component({
  selector: 'app-about',
  templateUrl: './about.page.html',
  styleUrls: ['./about.page.scss'],
  standalone: false,
})
export class AboutPage implements OnInit {
  companyInfo: CompanyInfo | null = null;

  constructor(
    private http: HttpClient,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.loadCompanyInfo();
  }

  openWebsite(): void {
    if (this.companyInfo?.website) {
      window.open(this.companyInfo.website, '_system');
    }
  }

  private async loadCompanyInfo(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando...' });
    await loading.present();

    const url = `${environment.gatewayUrl}/api/application/companyInformation/getCompanyInformation`;

    this.http.get<CompanyInfo>(url).subscribe({
      next: (data) => {
        this.companyInfo = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar informacion',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
