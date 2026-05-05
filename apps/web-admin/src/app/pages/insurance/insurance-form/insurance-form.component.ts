import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  FormArray,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';

import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-insurance-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    MatCardModule,
  ],
  templateUrl: './insurance-form.component.html',
  styleUrl: './insurance-form.component.scss',
})
export class InsuranceFormComponent implements OnInit {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);

  loading = signal(false);
  saving = signal(false);
  isEdit = signal(false);
  insuranceName = signal('Nuevo Seguro');

  form!: FormGroup;
  private insuranceId: number | null = null;

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      type: ['AUTO', Validators.required],
      provider: [''],
      phone: [''],
      email: ['', Validators.email],
      website: [''],
      status: ['A'],
      eventualities: this.fb.array([]),
      coverages: this.fb.array([]),
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'new') {
      this.isEdit.set(true);
      this.insuranceId = Number(id);
      this.loadInsurance(this.insuranceId);
    }
  }

  get eventualities(): FormArray {
    return this.form.get('eventualities') as FormArray;
  }

  get coverages(): FormArray {
    return this.form.get('coverages') as FormArray;
  }

  addEventuality(): void {
    this.eventualities.push(
      this.fb.group({
        name: ['', Validators.required],
        description: [''],
        instructions: [''],
      })
    );
  }

  removeEventuality(index: number): void {
    this.eventualities.removeAt(index);
  }

  addCoverage(): void {
    this.coverages.push(
      this.fb.group({
        name: ['', Validators.required],
        coverageAmount: [''],
        deductible: [''],
        coinsurance: [''],
        notes: [''],
      })
    );
  }

  removeCoverage(index: number): void {
    this.coverages.removeAt(index);
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.snackBar.open('Por favor completa los campos requeridos.', 'Cerrar', {
        duration: 4000,
      });
      return;
    }

    this.saving.set(true);
    const formValue = this.form.getRawValue();

    const payload: any = {
      name: formValue.name,
      description: formValue.description,
      type: formValue.type,
      provider: formValue.provider,
      phone: formValue.phone,
      email: formValue.email,
      website: formValue.website,
      status: formValue.status,
      eventualities: formValue.eventualities,
      coverages: formValue.coverages,
    };

    if (this.isEdit() && this.insuranceId) {
      payload.idInsurance = this.insuranceId;
    }

    this.http
      .post(`${environment.gatewayUrl}/api/application/insurance/saveUpdateInsurance`, payload)
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.snackBar.open(
            this.isEdit() ? 'Seguro actualizado correctamente.' : 'Seguro creado correctamente.',
            'Cerrar',
            { duration: 3000 }
          );
          this.router.navigate(['/insurance']);
        },
        error: () => {
          this.saving.set(false);
          this.snackBar.open('Error al guardar el seguro. Intenta de nuevo.', 'Cerrar', {
            duration: 4000,
          });
        },
      });
  }

  onCancel(): void {
    this.router.navigate(['/insurance']);
  }

  private loadInsurance(id: number): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/insurance/getInsurance/${id}`)
      .subscribe({
        next: (insurance) => {
          this.insuranceName.set(insurance.name ?? 'Seguro');
          this.form.patchValue({
            name: insurance.name ?? '',
            description: insurance.description ?? '',
            type: insurance.type ?? 'AUTO',
            provider: insurance.provider ?? '',
            phone: insurance.phone ?? '',
            email: insurance.email ?? '',
            website: insurance.website ?? '',
            status: insurance.status ?? 'A',
          });

          // Load eventualities
          if (insurance.eventualities?.length) {
            insurance.eventualities.forEach((ev: any) => {
              this.eventualities.push(
                this.fb.group({
                  name: [ev.name ?? '', Validators.required],
                  description: [ev.description ?? ''],
                  instructions: [ev.instructions ?? ''],
                })
              );
            });
          }

          // Load coverages
          if (insurance.coverages?.length) {
            insurance.coverages.forEach((cov: any) => {
              this.coverages.push(
                this.fb.group({
                  name: [cov.name ?? '', Validators.required],
                  coverageAmount: [cov.coverageAmount ?? ''],
                  deductible: [cov.deductible ?? ''],
                  coinsurance: [cov.coinsurance ?? ''],
                  notes: [cov.notes ?? ''],
                })
              );
            });
          }

          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.snackBar.open('Error al cargar el seguro.', 'Cerrar', { duration: 4000 });
          this.router.navigate(['/insurance']);
        },
      });
  }
}
