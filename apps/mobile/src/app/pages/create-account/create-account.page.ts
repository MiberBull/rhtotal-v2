import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.page.html',
  styleUrls: ['./create-account.page.scss'],
  standalone: false,
})
export class CreateAccountPage implements OnInit {
  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    confirmPassword: ['', [Validators.required]],
  });

  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    const email = this.route.snapshot.queryParamMap.get('email') ?? '';
    if (email) {
      this.form.controls.email.setValue(email);
    }
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  async onSubmit(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { email, password, confirmPassword } = this.form.getRawValue();

    if (password !== confirmPassword) {
      this.showToast('Las contrasenas no coinciden.');
      return;
    }

    const loading = await this.loadingCtrl.create({ message: 'Creando cuenta...' });
    await loading.present();

    const encryptedPassword = this.authService.encrypt(password);
    const url = `${environment.gatewayUrl}/api/user/user/create`;

    this.http.post(url, {
      user: email,
      password: encryptedPassword,
      passwordConfirmed: encryptedPassword,
      code: '',
    }).subscribe({
      next: async () => {
        await loading.dismiss();
        this.showToast('Cuenta creada exitosamente. Inicia sesion.', 'success');
        this.router.navigate(['/login'], { replaceUrl: true });
      },
      error: async () => {
        await loading.dismiss();
        this.showToast('No se pudo crear la cuenta. El correo puede ya estar en uso.');
      },
    });
  }

  private async showToast(message: string, color = 'danger'): Promise<void> {
    const toast = await this.toastCtrl.create({ message, duration: 3000, position: 'bottom', color });
    await toast.present();
  }
}
