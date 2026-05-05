import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { LoadingController, ToastController } from '@ionic/angular';
import { environment } from '../../../../environments/environment';

interface Eventuality {
  idEventuality: number;
  name: string;
  description?: string;
}

interface Coverage {
  idCoverage: number;
  name: string;
  description?: string;
  amount?: string;
}

interface InsuranceDetail {
  idInsurance: number;
  name: string;
  type?: string;
  provider?: string;
  description?: string;
  phone?: string;
  email?: string;
  address?: string;
  website?: string;
  eventualities?: Eventuality[];
  coverages?: Coverage[];
}

@Component({
  selector: 'app-insurance-detail',
  templateUrl: './insurance-detail.page.html',
  styleUrls: ['./insurance-detail.page.scss'],
  standalone: false,
})
export class InsuranceDetailPage implements OnInit {
  insurance: InsuranceDetail | null = null;
  selectedSegment = 'general';

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id') || '';
    this.loadInsurance(id);
  }

  segmentChanged(event: any): void {
    this.selectedSegment = event.detail.value;
  }

  requestQuote(): void {
    this.router.navigate(['/insurance/quote']);
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

  private async loadInsurance(id: string): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando seguro...' });
    await loading.present();

    const url = `${environment.gatewayUrl}/api/application/insurance/getInsurance/${id}`;

    this.http.get<InsuranceDetail>(url).subscribe({
      next: (data) => {
        this.insurance = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar detalle del seguro',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
