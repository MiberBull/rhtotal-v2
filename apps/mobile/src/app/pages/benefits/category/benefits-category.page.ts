import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

interface Discount {
  idDiscount: number;
  name: string;
  image?: string;
  discount?: number;
  provider?: string;
  description?: string;
}

@Component({
  selector: 'app-benefits-category',
  templateUrl: './benefits-category.page.html',
  styleUrls: ['./benefits-category.page.scss'],
  standalone: false,
})
export class BenefitsCategoryPage implements OnInit {
  discounts: Discount[] = [];
  categoryId!: string;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.categoryId = this.route.snapshot.paramMap.get('categoryId') || '';
    this.loadDiscounts();
  }

  navigateToDetail(discount: Discount): void {
    this.router.navigate(['/benefits/detail', discount.idDiscount]);
  }

  private async loadDiscounts(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando descuentos...' });
    await loading.present();

    const user = this.authService.currentUser;
    const url = `${environment.gatewayUrl}/api/application/discount/getImageDiscountByUser/${this.categoryId}/${user?.id}`;

    this.http.get<Discount[]>(url).subscribe({
      next: (data) => {
        this.discounts = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar descuentos',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
