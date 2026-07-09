import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-tenant-form-dialog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  template: `
    <h2 mat-dialog-title>Nuevo Tenant</h2>
    <mat-dialog-content>
      <form [formGroup]="form" style="display:flex;flex-direction:column;gap:8px;padding-top:8px">
        <mat-form-field appearance="outline">
          <mat-label>Nombre</mat-label>
          <input matInput formControlName="dsName" placeholder="Ej: ACME Corp" />
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Dominio</mat-label>
          <input matInput formControlName="dsDomain" placeholder="ej: acme.dchkw.com.mx" />
          <mat-hint>Subdominio único que identifica al tenant</mat-hint>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Identificador interno</mat-label>
          <input matInput formControlName="dsSlug" placeholder="acme-corp" />
          <mat-hint>Solo letras minúsculas, números y guiones</mat-hint>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button
        mat-flat-button
        color="primary"
        [mat-dialog-close]="form.value"
        [disabled]="form.invalid"
      >
        Crear
      </button>
    </mat-dialog-actions>
  `,
})
export class TenantFormDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  form!: FormGroup;

  ngOnInit(): void {
    this.form = this.fb.group({
      dsName: ['', [Validators.required, Validators.minLength(2)]],
      dsDomain: ['', Validators.required],
      dsSlug: ['', [Validators.required, Validators.pattern(/^[a-z0-9-]+$/)]],
    });
  }
}

@Component({
  selector: 'app-tenants',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatDialogModule,
    MatSlideToggleModule,
  ],
  templateUrl: './tenants.component.html',
  styleUrl: './tenants.component.scss',
})
export class TenantsComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  tenants = signal<any[]>([]);
  loading = signal(true);

  displayedColumns = ['name', 'domain', 'slug', 'active', 'actions'];

  ngOnInit(): void {
    this.loadTenants();
  }

  openCreate(): void {
    const ref = this.dialog.open(TenantFormDialogComponent, { width: '480px' });
    ref.afterClosed().subscribe((data) => {
      if (!data) return;
      this.http.post(`${environment.gatewayUrl}/api/security/tenant/create`, data).subscribe({
        next: () => {
          this.snackBar.open('Tenant creado correctamente', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadTenants();
        },
        error: () =>
          this.snackBar.open('Error al crear el tenant', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
    });
  }

  toggleActive(tenant: any): void {
    this.http
      .put(`${environment.gatewayUrl}/api/security/tenant/${tenant.id}/toggle`, {})
      .subscribe({
        next: () => {
          const newState = !tenant.fgActive;
          this.tenants.update((list) =>
            list.map((t) => (t.id === tenant.id ? { ...t, fgActive: newState } : t))
          );
          this.snackBar.open(newState ? 'Tenant activado' : 'Tenant desactivado', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
        error: () =>
          this.snackBar.open('Error al actualizar el tenant', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
  }

  private loadTenants(): void {
    this.loading.set(true);
    this.http.get<any[]>(`${environment.gatewayUrl}/api/security/tenant/all`).subscribe({
      next: (res) => {
        const data = Array.isArray(res) ? res : ((res as any)?.content ?? []);
        this.tenants.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.tenants.set([]);
        this.loading.set(false);
      },
    });
  }
}
