import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface BenefitCategory {
  idCategory: number;
  name: string;
  icon?: string;
  image?: string;
  discountCount?: number;
}

@Component({
  selector: 'app-benefits',
  templateUrl: './benefits.page.html',
  styleUrls: ['./benefits.page.scss'],
  standalone: false,
})
export class BenefitsPage implements OnInit {
  categories: BenefitCategory[] = [];

  private categoryColors = ['#e91e63', '#1565c0', '#2e7d32', '#6a1b9a', '#e65100', '#00838f'];
  private categoryIcons = [
    'restaurant-outline',
    'fitness-outline',
    'cart-outline',
    'school-outline',
    'car-outline',
    'medical-outline',
  ];

  constructor(
    private http: HttpClient,
    private router: Router,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  getCategoryColor(index: number): string {
    return this.categoryColors[index % this.categoryColors.length];
  }

  getCategoryIcon(category: BenefitCategory, index: number): string {
    return category.icon || this.categoryIcons[index % this.categoryIcons.length];
  }

  navigateToCategory(category: BenefitCategory): void {
    this.router.navigate(['/benefits', category.idCategory]);
  }

  private async loadCategories(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando beneficios...' });
    await loading.present();

    const user = this.authService.currentUser;
    const url = `${environment.gatewayUrl}/api/application/discount/getCategoryIdUser/${user?.id}`;

    this.http.get<BenefitCategory[]>(url).subscribe({
      next: (data) => {
        this.categories = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar beneficios',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
