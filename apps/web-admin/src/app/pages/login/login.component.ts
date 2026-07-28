import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AuthService } from '../../core/auth/auth.service';
import { LoginFlag } from '@dch/shared';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  hidePassword = signal(true);
  loading = signal(false);
  errorMessage = signal('');

  loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
  });

  togglePasswordVisibility(): void {
    this.hidePassword.update((v) => !v);
  }

  /** Deduce el tenant desde el dominio del email para la autenticación */
  private resolveTenant(email: string): string {
    const domain = email.split('@')[1]?.toLowerCase() ?? '';
    // Usuarios corporativos DCH (dch.mx, dchkw.mx)
    if (domain === 'dch.mx' || domain.endsWith('.dchkw.mx')) return 'demo-corp';
    // Usuarios de RS — buscar coincidencia en id del tenant (excluye 'ALL')
    for (const t of this.authService.availableTenants) {
      if (t.id !== 'ALL' && domain.includes(t.id)) return t.id;
    }
    return 'aeisa';
  }

  async onSubmit(): Promise<void> {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    const { email, password } = this.loginForm.getRawValue();

    try {
      const response = await this.authService.login(email, password, this.resolveTenant(email));

      switch (response.flag) {
        case LoginFlag.OK:
          this.router.navigate(['/dashboard']);
          break;
        case LoginFlag.INVALID_USER:
          this.errorMessage.set('El usuario ingresado no es valido.');
          break;
        case LoginFlag.WRONG_PASSWORD:
          this.errorMessage.set('La contrasena es incorrecta.');
          break;
        case LoginFlag.BLOCKED:
          this.errorMessage.set(
            'Tu cuenta ha sido bloqueada temporalmente. Intenta en 10 minutos.'
          );
          break;
        case LoginFlag.NEW_USER:
          this.router.navigate(['/new-user'], { queryParams: { email } });
          break;
      }
    } catch {
      this.errorMessage.set('Error de conexion. Intenta de nuevo.');
    } finally {
      this.loading.set(false);
    }
  }
}
