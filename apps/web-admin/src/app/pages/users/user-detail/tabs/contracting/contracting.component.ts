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
import { UserApiService, EmployeeContratingTO } from '@dch/shared';

@Component({
  selector: 'app-contracting',
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
  templateUrl: './contracting.component.html',
  styleUrl: './contracting.component.scss',
})
export class ContractingComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  idUser = input.required<number>();

  saving = signal(false);

  contractTypes = [
    { value: 'INDEFINIDO', label: 'Indefinido' },
    { value: 'DETERMINADO', label: 'Tiempo determinado' },
    { value: 'OBRA', label: 'Por obra' },
    { value: 'CAPACITACION', label: 'Capacitacion' },
    { value: 'PERIODO_PRUEBA', label: 'Periodo de prueba' },
  ];

  form = this.fb.nonNullable.group({
    startDate: [''],
    endDate: [''],
    position: ['', [Validators.required]],
    area: [''],
    department: [''],
    contractType: [''],
    salary: [''],
  });

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.userApi.getContrating(this.idUser()).subscribe({
      next: (data) => {
        if (data) {
          this.form.patchValue({
            startDate: data.creationDate ? new Date(data.creationDate).toISOString().split('T')[0] : '',
            endDate: data.endOfContract ? new Date(data.endOfContract).toISOString().split('T')[0] : '',
            position: data.job ?? '',
            area: data.dsArea ?? '',
            department: data.skill ?? '',
            contractType: data.contract ?? '',
            salary: data.qtSalary ?? '',
          });
        }
      },
      error: () => {
        this.snackBar.open('Error al cargar datos de contratacion', 'Cerrar', { duration: 3000 });
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

    const payload: Partial<EmployeeContratingTO> = {
      idUser: this.idUser(),
      job: values.position,
      dsArea: values.area,
      skill: values.department,
      contract: values.contractType,
      qtSalary: values.salary,
      endOfContract: values.endDate ? new Date(values.endDate) : undefined as any,
      active: true,
      dsLastUserModifier: 'admin',
      lastModification: new Date(),
    };

    this.userApi.saveContrating(payload as EmployeeContratingTO).subscribe({
      next: () => {
        this.saving.set(false);
        this.snackBar.open('Datos de contratacion guardados correctamente', 'Cerrar', { duration: 3000 });
      },
      error: () => {
        this.saving.set(false);
        this.snackBar.open('Error al guardar datos de contratacion', 'Cerrar', { duration: 3000 });
      },
    });
  }
}
