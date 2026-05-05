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
import { UserApiService, EmployeeDataAssignmentTO } from '@dch/shared';

@Component({
  selector: 'app-assignment',
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
  templateUrl: './assignment.component.html',
  styleUrl: './assignment.component.scss',
})
export class AssignmentComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  idUser = input.required<number>();

  saving = signal(false);
  clients = signal<any[]>([]);
  projects = signal<any[]>([]);

  form = this.fb.nonNullable.group({
    clientId: [0],
    projectName: [''],
    directManager: [''],
    startDate: [''],
    location: [''],
    emailDirectBoss: [''],
    telephoneDirectBoss: [''],
  });

  ngOnInit(): void {
    this.loadCatalogues();
    this.loadData();
  }

  private loadCatalogues(): void {
    this.userApi.getClients().subscribe({
      next: (data) => this.clients.set(data ?? []),
    });
  }

  onClientChange(clientId: number): void {
    if (clientId) {
      this.userApi.getProjectsByClient(clientId).subscribe({
        next: (data) => this.projects.set(data ?? []),
      });
    }
  }

  private loadData(): void {
    this.userApi.getAsignation(this.idUser()).subscribe({
      next: (data) => {
        if (data) {
          this.form.patchValue({
            clientId: data.idClient ?? 0,
            projectName: data.project ?? '',
            directManager: data.manager ?? '',
            startDate: data.startAssigment
              ? new Date(data.startAssigment).toISOString().split('T')[0]
              : '',
            location: data.city ? `${data.city}, ${data.state}` : '',
            emailDirectBoss: data.emailDirectBoss ?? '',
            telephoneDirectBoss: data.telephoneDirectBoss ?? '',
          });

          if (data.idClient) {
            this.onClientChange(data.idClient);
          }
        }
      },
      error: () => {
        this.snackBar.open('Error al cargar datos de asignacion', 'Cerrar', { duration: 3000 });
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

    const locationParts = values.location.split(',').map((s: string) => s.trim());

    const payload: Partial<EmployeeDataAssignmentTO> = {
      idUser: this.idUser(),
      idClient: values.clientId,
      project: values.projectName,
      manager: values.directManager,
      startAssigment: values.startDate ? new Date(values.startDate) : undefined as any,
      city: locationParts[0] ?? '',
      state: locationParts[1] ?? '',
      emailDirectBoss: values.emailDirectBoss,
      telephoneDirectBoss: values.telephoneDirectBoss,
      active: true,
      lastUserModifier: 'admin',
      lastModification: new Date(),
    };

    this.userApi.saveAsignation(payload as EmployeeDataAssignmentTO).subscribe({
      next: () => {
        this.saving.set(false);
        this.snackBar.open('Datos de asignacion guardados correctamente', 'Cerrar', { duration: 3000 });
      },
      error: () => {
        this.saving.set(false);
        this.snackBar.open('Error al guardar datos de asignacion', 'Cerrar', { duration: 3000 });
      },
    });
  }
}
