import { Component, inject, input, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UserApiService, EmployeeDomicileTO } from '@dch/shared';

@Component({
  selector: 'app-address',
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
  ],
  templateUrl: './address.component.html',
  styleUrl: './address.component.scss',
})
export class AddressComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  idUser = input.required<number>();
  employeeData = input<any>();

  saving = signal(false);
  states = signal<any[]>([]);
  cities = signal<any[]>([]);

  form = this.fb.nonNullable.group({
    street: ['', [Validators.required]],
    exteriorNumber: ['', [Validators.required]],
    interiorNumber: [''],
    colony: ['', [Validators.required]],
    postalCode: ['', [Validators.required, Validators.pattern(/^\d{5}$/)]],
    city: ['', [Validators.required]],
    state: ['', [Validators.required]],
    country: ['Mexico'],
  });

  ngOnInit(): void {
    this.loadCatalogues();
    this.loadData();
  }

  private loadCatalogues(): void {
    this.userApi.getStates().subscribe({
      next: (data) => this.states.set(data ?? []),
    });
    this.userApi.getCities().subscribe({
      next: (data) => this.cities.set(data ?? []),
    });
  }

  private loadData(): void {
    this.userApi.getAddress(this.idUser()).subscribe({
      next: (data) => {
        if (data) {
          this.form.patchValue({
            street: data.street ?? '',
            exteriorNumber: data.outDoorNumber ?? '',
            interiorNumber: data.interiorNumber ?? '',
            colony: data.colony ?? '',
            postalCode: data.postalCode ?? '',
            city: data.city ?? '',
            state: data.state ?? '',
          });
        }
      },
      error: () => {
        this.snackBar.open('Error al cargar domicilio', 'Cerrar', { duration: 3000 });
      },
    });
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    const values = this.form.getRawValue();

    const payload: Partial<EmployeeDomicileTO> = {
      employee: { id: this.employeeData()?.id } as any,
      street: values.street,
      outDoorNumber: values.exteriorNumber,
      interiorNumber: values.interiorNumber,
      colony: values.colony,
      postalCode: values.postalCode,
      city: values.city,
      state: values.state,
      active: true,
      lastUserModifier: 'admin',
      lastModification: new Date(),
    };

    this.userApi.saveAddress(payload as EmployeeDomicileTO).subscribe({
      next: () => {
        this.saving.set(false);
        this.snackBar.open('Domicilio guardado correctamente', 'Cerrar', { duration: 3000 });
      },
      error: () => {
        this.saving.set(false);
        this.snackBar.open('Error al guardar domicilio', 'Cerrar', { duration: 3000 });
      },
    });
  }
}
