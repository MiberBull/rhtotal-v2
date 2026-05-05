import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

interface PaymentItem {
  id: number;
  period: string;
  netAmount: number;
  grossAmount: number;
  date: string;
  pdfUrl?: string;
}

@Component({
  selector: 'app-payments',
  templateUrl: './payments.page.html',
  styleUrls: ['./payments.page.scss'],
  standalone: false,
})
export class PaymentsPage implements OnInit {
  payments: PaymentItem[] = [];
  selectedMonth: number;
  selectedYear: number;

  months = [
    { value: 1, label: 'Enero' },
    { value: 2, label: 'Febrero' },
    { value: 3, label: 'Marzo' },
    { value: 4, label: 'Abril' },
    { value: 5, label: 'Mayo' },
    { value: 6, label: 'Junio' },
    { value: 7, label: 'Julio' },
    { value: 8, label: 'Agosto' },
    { value: 9, label: 'Septiembre' },
    { value: 10, label: 'Octubre' },
    { value: 11, label: 'Noviembre' },
    { value: 12, label: 'Diciembre' },
  ];

  years: number[] = [];

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {
    const now = new Date();
    this.selectedMonth = now.getMonth() + 1;
    this.selectedYear = now.getFullYear();

    for (let y = now.getFullYear(); y >= now.getFullYear() - 5; y--) {
      this.years.push(y);
    }
  }

  ngOnInit(): void {
    this.loadPayments();
  }

  onFilterChange(): void {
    this.loadPayments();
  }

  async loadPayments(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando pagos...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/document/getPayments/${userId}?month=${this.selectedMonth}&year=${this.selectedYear}`;

    this.http.get<PaymentItem[]>(url).subscribe({
      next: (data) => {
        this.payments = data || [];
        loading.dismiss();
      },
      error: () => {
        this.payments = [];
        loading.dismiss();
        this.showToast('Error al cargar pagos', 'danger');
      },
    });
  }

  openPayment(payment: PaymentItem): void {
    if (payment.pdfUrl) {
      window.open(payment.pdfUrl, '_blank');
    }
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
