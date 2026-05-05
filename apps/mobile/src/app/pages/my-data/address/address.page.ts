import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-address',
  templateUrl: './address.page.html',
  styleUrls: ['./address.page.scss'],
  standalone: false,
})
export class AddressPage implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      street: [''],
      exteriorNumber: [''],
      interiorNumber: [''],
      colony: [''],
      postalCode: [''],
      city: [''],
      state: [''],
      country: [''],
    });

    this.loadData();
  }

  async loadData(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/getEmployeeAddress/${userId}`;

    this.http.get<any>(url).subscribe({
      next: (data) => {
        this.form.patchValue({
          street: data.street || '',
          exteriorNumber: data.exteriorNumber || '',
          interiorNumber: data.interiorNumber || '',
          colony: data.colony || '',
          postalCode: data.postalCode || '',
          city: data.city || '',
          state: data.state || '',
          country: data.country || '',
        });
        loading.dismiss();
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al cargar domicilio', 'danger');
      },
    });
  }

  async save(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Guardando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/saveOrUpdateEmployeeAddress`;
    const payload = { ...this.form.value, idUser: userId };

    this.http.post<any>(url, payload).subscribe({
      next: () => {
        loading.dismiss();
        this.showToast('Domicilio guardado correctamente', 'success');
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al guardar domicilio', 'danger');
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
