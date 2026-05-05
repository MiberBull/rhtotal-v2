import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SecurityApiService } from '@dch/shared';
import { passwordValidator } from '@dch/shared';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-reset-confirmation',
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
  template: `
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <mat-icon class="header-icon">vpn_key</mat-icon>
          <h1>Nueva contrasena</h1>
          <p class="subtitle">Ingresa tu nueva contrasena. Debe tener al menos 8 caracteres, una mayuscula y un numero.</p>
        </div>

        @if (success()) {
          <div class="success-message">
            <mat-icon>check_circle</mat-icon>
            <p>Tu contrasena ha sido actualizada exitosamente.</p>
          </div>
          <div class="auth-footer">
            <a routerLink="/login" class="link">Ir al inicio de sesion</a>
          </div>
        } @else {
          <form [formGroup]="form" (ngSubmit)="onSubmit()" class="auth-form">
            <mat-form-field appearance="outline">
              <mat-label>Nueva contrasena</mat-label>
              <input matInput [type]="hidePassword() ? 'password' : 'text'" formControlName="password" />
              <mat-icon matPrefix>lock</mat-icon>
              <button mat-icon-button matSuffix type="button" (click)="togglePasswordVisibility()">
                <mat-icon>{{ hidePassword() ? 'visibility_off' : 'visibility' }}</mat-icon>
              </button>
            </mat-form-field>

            <mat-form-field appearance="outline">
              <mat-label>Confirmar contrasena</mat-label>
              <input matInput [type]="hidePassword() ? 'password' : 'text'" formControlName="confirmPassword" />
              <mat-icon matPrefix>lock</mat-icon>
            </mat-form-field>

            @if (errorMessage()) {
              <div class="error-banner">
                <mat-icon>error_outline</mat-icon>
                <span>{{ errorMessage() }}</span>
              </div>
            }

            <button mat-flat-button color="primary" type="submit" class="submit-btn" [disabled]="loading()">
              @if (loading()) {
                <mat-spinner diameter="20"></mat-spinner>
              } @else {
                Actualizar contrasena
              }
            </button>
          </form>
        }
      </div>
    </div>
  `,
  styleUrl: './reset-confirmation.component.scss',
})
export class ResetConfirmationComponent {
  private fb = inject(FormBuilder);
  private securityApi = inject(SecurityApiService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  hidePassword = signal(true);
  loading = signal(false);
  success = signal(false);
  errorMessage = signal('');

  togglePasswordVisibility(): void {
    this.hidePassword.update((v) => !v);
  }

  form = this.fb.nonNullable.group({
    password: ['', [Validators.required, Validators.minLength(8), passwordValidator()]],
    confirmPassword: ['', [Validators.required]],
  });

  private token = '';

  constructor() {
    this.securityApi.configure({ gatewayUrl: environment.gatewayUrl });
    this.token = this.route.snapshot.queryParamMap.get('token') ?? '';
    if (!this.token) {
      this.router.navigate(['/login']);
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { password, confirmPassword } = this.form.getRawValue();

    if (password !== confirmPassword) {
      this.errorMessage.set('Las contrasenas no coinciden.');
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    this.securityApi
      .resetPasswordConfirmation({ token: this.token, password, passwordConfirmed: confirmPassword })
      .subscribe({
        next: () => {
          this.success.set(true);
          this.loading.set(false);
        },
        error: () => {
          this.errorMessage.set('No se pudo actualizar la contrasena. El enlace puede haber expirado.');
          this.loading.set(false);
        },
      });
  }
}
