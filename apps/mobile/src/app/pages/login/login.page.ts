import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: false,
})
export class LoginPage {
  loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
  });

  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  async onLogin(): Promise<void> {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const loading = await this.loadingCtrl.create({ message: 'Iniciando sesion...' });
    await loading.present();

    const { email, password } = this.loginForm.getRawValue();

    try {
      const response = await this.authService.login(email, password);

      switch (response.flag) {
        case 0:
          this.router.navigate(['/home'], { replaceUrl: true });
          break;
        case 1:
          this.showToast('El usuario ingresado no es valido.');
          break;
        case 2:
          this.showToast('La contrasena es incorrecta.');
          break;
        case 3:
          this.showToast('Tu cuenta ha sido bloqueada temporalmente. Intenta en 10 minutos.');
          break;
        case 4:
          this.router.navigate(['/create-account'], { queryParams: { email } });
          break;
      }
    } catch {
      this.showToast('Error de conexion. Verifica tu red e intenta de nuevo.');
    } finally {
      await loading.dismiss();
    }
  }

  private async showToast(message: string): Promise<void> {
    const toast = await this.toastCtrl.create({
      message,
      duration: 3000,
      position: 'bottom',
      color: 'danger',
    });
    await toast.present();
  }
}
