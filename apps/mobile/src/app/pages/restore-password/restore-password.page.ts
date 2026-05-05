import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-restore-password',
  templateUrl: './restore-password.page.html',
  styleUrls: ['./restore-password.page.scss'],
  standalone: false,
})
export class RestorePasswordPage {
  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
  });

  sent = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  async onSubmit(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const loading = await this.loadingCtrl.create({ message: 'Enviando...' });
    await loading.present();

    const email = this.form.getRawValue().email;
    const url = `${environment.gatewayUrl}/api/security/role/reset/request`;

    this.http.post(url, { email, app: 'mobile' }).subscribe({
      next: async () => {
        await loading.dismiss();
        this.sent = true;
      },
      error: async () => {
        await loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'No se pudo enviar el correo. Verifica tu email.',
          duration: 3000,
          position: 'bottom',
          color: 'danger',
        });
        await toast.present();
      },
    });
  }
}
