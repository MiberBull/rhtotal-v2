import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-personal-info',
  templateUrl: './personal-info.page.html',
  styleUrls: ['./personal-info.page.scss'],
  standalone: false,
})
export class PersonalInfoPage implements OnInit {
  form!: FormGroup;

  genderOptions = [
    { value: 'M', label: 'Masculino' },
    { value: 'F', label: 'Femenino' },
    { value: 'O', label: 'Otro' },
  ];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [''],
      lastName: [''],
      middleName: [''],
      email: [{ value: '', disabled: true }],
      rfc: [''],
      curp: [''],
      nss: [''],
      phone: [''],
      birthDate: [''],
      gender: [''],
    });

    this.loadData();
  }

  async loadData(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/getEmployeeComplementary/${userId}`;

    this.http.get<any>(url).subscribe({
      next: (data) => {
        this.form.patchValue({
          name: data.name || '',
          lastName: data.lastName || '',
          middleName: data.middleName || '',
          email: data.email || this.authService.currentUser?.email || '',
          rfc: data.rfc || '',
          curp: data.curp || '',
          nss: data.nss || '',
          phone: data.phone || '',
          birthDate: data.birthDate || '',
          gender: data.gender || '',
        });
        loading.dismiss();
      },
      error: () => {
        this.form.patchValue({ email: this.authService.currentUser?.email || '' });
        loading.dismiss();
        this.showToast('Error al cargar datos', 'danger');
      },
    });
  }

  async save(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Guardando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/saveOrUpdateEmployeeComplementaryWeb`;
    const payload = { ...this.form.getRawValue(), idUser: userId };

    this.http.post<any>(url, payload).subscribe({
      next: () => {
        loading.dismiss();
        this.showToast('Datos guardados correctamente', 'success');
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al guardar datos', 'danger');
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
