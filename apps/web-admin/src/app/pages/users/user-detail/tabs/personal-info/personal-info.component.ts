import { Component, inject, input, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {
  UserApiService,
  EmployeeComplementaryTO,
  rfcValidator,
  curpValidator,
  nssValidator,
} from '@dch/shared';

@Component({
  selector: 'app-personal-info',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './personal-info.component.html',
  styleUrl: './personal-info.component.scss',
})
export class PersonalInfoComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  idUser = input.required<number>();
  employeeData = input<any>();

  saving = signal(false);

  form = this.fb.nonNullable.group({
    name: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    middleName: [''],
    email: ['', [Validators.required, Validators.email]],
    rfc: ['', [rfcValidator()]],
    curp: ['', [curpValidator()]],
    nss: ['', [nssValidator()]],
    birthDate: [''],
    gender: [''],
    phone: [''],
    photo: [''],
  });

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.userApi.getComplementary(this.idUser()).subscribe({
      next: (data) => {
        if (data) {
          this.form.patchValue({
            name: this.employeeData()?.name ?? '',
            lastName: this.employeeData()?.lastName ?? '',
            middleName: this.employeeData()?.lastMName ?? '',
            email: data.email ?? '',
            rfc: data.rfc ?? '',
            curp: data.curp ?? '',
            nss: data.nss ?? '',
            birthDate: data.birthDate ? new Date(data.birthDate).toISOString().split('T')[0] : '',
            gender: this.employeeData()?.gender ?? '',
            phone: data.phone ?? '',
            photo: data.photography ?? '',
          });
        }
      },
      error: () => {
        this.snackBar.open('Error al cargar datos personales', 'Cerrar', { duration: 3000 });
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

    const payload: Partial<EmployeeComplementaryTO> = {
      employee: { id: this.employeeData()?.id } as any,
      rfc: values.rfc,
      curp: values.curp,
      nss: values.nss,
      email: values.email,
      phone: values.phone,
      birthDate: values.birthDate ? new Date(values.birthDate) : undefined as any,
      photography: values.photo,
      active: true,
      lastUserModifier: 'admin',
      lastModification: new Date(),
    };

    this.userApi.saveComplementary(payload as EmployeeComplementaryTO).subscribe({
      next: () => {
        this.saving.set(false);
        this.snackBar.open('Datos personales guardados correctamente', 'Cerrar', { duration: 3000 });
      },
      error: () => {
        this.saving.set(false);
        this.snackBar.open('Error al guardar datos personales', 'Cerrar', { duration: 3000 });
      },
    });
  }
}
