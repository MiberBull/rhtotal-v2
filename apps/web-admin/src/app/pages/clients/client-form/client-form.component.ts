import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { ClientTO } from '@dch/shared';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './client-form.component.html',
  styleUrl: './client-form.component.scss',
})
export class ClientFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private snackBar = inject(MatSnackBar);

  loading = signal(false);
  saving = signal(false);
  isEdit = signal(false);

  form = this.fb.nonNullable.group({
    idClient: [0],
    name: ['', [Validators.required]],
    rfc: [''],
    address: [''],
    colony: [''],
    postalCode: [''],
    city: [''],
    state: [''],
    phone: [''],
    email: ['', [Validators.email]],
    contactName: [''],
    status: ['A'],
  });

  private clientId: number | null = null;

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam && idParam !== 'new') {
      this.isEdit.set(true);
      this.clientId = Number(idParam);
      this.loadClient(this.clientId);
    }
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    const formValue = this.form.getRawValue();

    const payload: any = {
      idClient: this.isEdit() ? this.clientId : undefined,
      name: formValue.name,
      rfc: formValue.rfc,
      address: formValue.address,
      phone: formValue.phone,
      email: formValue.email,
      contact: formValue.contactName,
      status: formValue.status,
    };

    this.http
      .post(`${environment.gatewayUrl}/api/application/client/saveOrUpdateClient`, payload)
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.snackBar.open(
            this.isEdit() ? 'Cliente actualizado correctamente' : 'Cliente creado correctamente',
            'Cerrar',
            { duration: 3000, horizontalPosition: 'end', verticalPosition: 'top' }
          );
          this.router.navigate(['/clients']);
        },
        error: () => {
          this.saving.set(false);
          this.snackBar.open('Error al guardar el cliente', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
      });
  }

  onCancel(): void {
    this.router.navigate(['/clients']);
  }

  private loadClient(id: number): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/client/getClient/${id}`)
      .subscribe({
        next: (client) => {
          this.form.patchValue({
            idClient: client.idClient ?? id,
            name: client.name ?? '',
            rfc: client.rfc ?? '',
            address: client.address ?? '',
            colony: client.colony ?? '',
            postalCode: client.postalCode ?? '',
            city: client.city ?? '',
            state: client.state ?? '',
            phone: client.phone ?? '',
            email: client.email ?? '',
            contactName: client.contact ?? '',
            status: client.status ?? 'A',
          });
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.snackBar.open('Error al cargar el cliente', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.router.navigate(['/clients']);
        },
      });
  }
}
