import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { LoadingController, ToastController } from '@ionic/angular';
import { environment } from '../../../../environments/environment';

interface DiscountDetail {
  idDiscount: number;
  name: string;
  description?: string;
  image?: string;
  discount?: number;
  provider?: string;
  phone?: string;
  email?: string;
  address?: string;
  website?: string;
}

@Component({
  selector: 'app-discount-detail',
  templateUrl: './discount-detail.page.html',
  styleUrls: ['./discount-detail.page.scss'],
  standalone: false,
})
export class DiscountDetailPage implements OnInit {
  discount: DiscountDetail | null = null;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id') || '';
    this.loadDiscount(id);
  }

  callPhone(): void {
    if (this.discount?.phone) {
      window.open(`tel:${this.discount.phone}`, '_system');
    }
  }

  sendEmail(): void {
    if (this.discount?.email) {
      window.open(`mailto:${this.discount.email}`, '_system');
    }
  }

  openWebsite(): void {
    if (this.discount?.website) {
      window.open(this.discount.website, '_system');
    }
  }

  private async loadDiscount(id: string): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando detalle...' });
    await loading.present();

    const url = `${environment.gatewayUrl}/api/application/discount/getDiscount/${id}`;

    this.http.get<DiscountDetail>(url).subscribe({
      next: (data) => {
        this.discount = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar el descuento',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
