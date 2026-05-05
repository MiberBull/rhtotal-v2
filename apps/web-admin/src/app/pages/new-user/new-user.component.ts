import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UserApiService, CryptoService } from '@dch/shared';
import { passwordValidator } from '@dch/shared';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-new-user',
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
          <mat-icon class="header-icon">person_add</mat-icon>
          <h1>Crear cuenta</h1>
          <p class="subtitle">Configura tu contrasena para activar tu cuenta.</p>
        </div>

        @if (success()) {
          <div class="success-message">
            <mat-icon>check_circle</mat-icon>
            <p>Tu cuenta ha sido creada exitosamente. Ya puedes iniciar sesion.</p>
          </div>
          <div class="auth-footer">
            <a routerLink="/login" class="link">Ir al inicio de sesion</a>
          </div>
        } @else {
          <form [formGroup]="form" (ngSubmit)="onSubmit()" class="auth-form">
            <mat-form-field appearance="outline">
              <mat-label>Correo electronico</mat-label>
              <input matInput type="email" formControlName="email" readonly />
              <mat-icon matPrefix>mail</mat-icon>
            </mat-form-field>

            <mat-form-field appearance="outline">
              <mat-label>Contrasena</mat-label>
              <input matInput [type]="hidePassword() ? 'password' : 'text'" formControlName="password" />
              <mat-icon matPrefix>lock</mat-icon>
              <button mat-icon-button matSuffix type="button" (click)="togglePasswordVisibility()">
                <mat-icon>{{ hidePassword() ? 'visibility_off' : 'visibility' }}</mat-icon>
              </button>
              @if (form.controls.password.hasError('passwordStrength') && form.controls.password.touched) {
                <mat-error>Debe incluir mayuscula, minuscula y numero</mat-error>
              }
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
                Crear cuenta
              }
            </button>
          </form>

          <div class="auth-footer">
            <a routerLink="/login" class="link">Ya tengo cuenta</a>
          </div>
        }
      </div>
    </div>
  `,
  styleUrl: './new-user.component.scss',
})
export class NewUserComponent {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private cryptoService = inject(CryptoService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  hidePassword = signal(true);
  loading = signal(false);
  success = signal(false);
  errorMessage = signal('');

  togglePasswordVisibility(): void {
    this.hidePassword.update((v) => !v);
  }

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8), passwordValidator()]],
    confirmPassword: ['', [Validators.required]],
  });

  constructor() {
    this.userApi.configure({ gatewayUrl: environment.gatewayUrl });
    this.cryptoService.configure(environment.aesSecret);

    const email = this.route.snapshot.queryParamMap.get('email') ?? '';
    if (email) {
      this.form.controls.email.setValue(email);
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { email, password, confirmPassword } = this.form.getRawValue();

    if (password !== confirmPassword) {
      this.errorMessage.set('Las contrasenas no coinciden.');
      return;
    }

    this.loading.set(true);
    this.errorMessage.set('');

    const encryptedPassword = this.cryptoService.encrypt(password);

    this.userApi
      .createUser({
        user: email,
        password: encryptedPassword,
        passwordConfirmed: encryptedPassword,
        code: '',
      })
      .subscribe({
        next: () => {
          this.success.set(true);
          this.loading.set(false);
        },
        error: () => {
          this.errorMessage.set('No se pudo crear la cuenta. El correo puede ya estar en uso.');
          this.loading.set(false);
        },
      });
  }
}
