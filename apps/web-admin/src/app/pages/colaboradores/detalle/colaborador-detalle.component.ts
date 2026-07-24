import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import {
  MatDialog,
  MatDialogModule,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatTooltipModule } from '@angular/material/tooltip';

import { AuthService } from '../../../core/auth/auth.service';
import { environment } from '../../../../environments/environment';

// ─── Dialog de Baja ──────────────────────────────────────────────────────────

@Component({
  selector: 'app-baja-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
    MatIconModule,
  ],
  template: `
    <div class="baja-dialog">
      <h2 mat-dialog-title>
        <mat-icon color="warn">person_off</mat-icon>
        Registrar Baja
      </h2>
      <mat-dialog-content>
        <p class="baja-warning">
          Esta accion cambiara el estatus del colaborador a <strong>Baja</strong>. Esta operacion
          queda registrada en el expediente.
        </p>
        <form [formGroup]="form" class="baja-form">
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Motivo de baja</mat-label>
            <textarea
              matInput
              formControlName="reason"
              rows="3"
              placeholder="Describe el motivo..."
            ></textarea>
            @if (form.controls.reason.hasError('required') && form.controls.reason.touched) {
              <mat-error>El motivo es obligatorio</mat-error>
            }
          </mat-form-field>

          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Fecha de baja</mat-label>
            <input matInput type="date" formControlName="terminationDate" />
            @if (
              form.controls.terminationDate.hasError('required') &&
              form.controls.terminationDate.touched
            ) {
              <mat-error>La fecha es obligatoria</mat-error>
            }
          </mat-form-field>
        </form>
      </mat-dialog-content>
      <mat-dialog-actions align="end">
        <button mat-stroked-button mat-dialog-close>Cancelar</button>
        <button mat-flat-button color="warn" (click)="confirm()">Registrar Baja</button>
      </mat-dialog-actions>
    </div>
  `,
  styles: [
    `
      .baja-dialog {
        padding: 8px;
        min-width: 360px;
      }
      h2 {
        display: flex;
        align-items: center;
        gap: 8px;
        font-size: 1.25rem;
        font-weight: 600;
      }
      .baja-warning {
        font-size: 0.875rem;
        color: #616161;
        margin-bottom: 16px;
      }
      .baja-form {
        display: flex;
        flex-direction: column;
        gap: 8px;
      }
      .full-width {
        width: 100%;
      }
      mat-dialog-actions {
        padding: 16px 0 0;
        gap: 8px;
      }
    `,
  ],
})
export class BajaDialogComponent {
  private dialogRef = inject(MatDialogRef<BajaDialogComponent>);
  private fb = inject(FormBuilder);

  form = this.fb.nonNullable.group({
    reason: ['', Validators.required],
    terminationDate: [new Date().toISOString().split('T')[0], Validators.required],
  });

  confirm(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.dialogRef.close(this.form.getRawValue());
  }
}

// ─── Interfaces ───────────────────────────────────────────────────────────────

interface EmployeeProfileTO {
  employee: {
    idUser: number;
    dsName: string;
    dsSex: string;
    dtBirthdate: string;
    dsCurp: string;
    dsRfc: string;
    dsNss: string;
    dsPhone: string;
    dsEmail: string;
    dsTerminationReason: string;
    dtTerminationDate: string;
    dsDchExecutive: string;
    dsEmployeeNumber: string;
    fgActive: boolean;
  };
  complementary: {
    dsJobTitle: string;
    dsArea: string;
    dsWorkSchedule: string;
    dsContractType: string;
    dsEmployeeType: string;
    dsVacationBalance: number;
  };
  address: {
    dsStreet: string;
    dsNeighborhood: string;
    dsCity: string;
    dsState: string;
    dsZipCode: string;
  };
  contracting: {
    dtHireDate: string;
    dsWorkShift: string;
  };
  assignment: {
    dsClientName: string;
    dsProjectName: string;
    dsWorkCenter: string;
    dsRegion: string;
  };
  emergencyContact: {
    dsName: string;
    dsRelationship: string;
    dsPhone: string;
    dsPhoneAlt: string;
  };
  tenantName: string;
  vacationBalance: number;
}

// ─── Componente principal ─────────────────────────────────────────────────────

@Component({
  selector: 'app-colaborador-detalle',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    MatTabsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatDividerModule,
    MatTooltipModule,
  ],
  templateUrl: './colaborador-detalle.component.html',
  styleUrl: './colaborador-detalle.component.scss',
})
export class ColaboradorDetalleComponent implements OnInit {
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);
  private fb = inject(FormBuilder);

  loading = signal(true);
  savingContact = signal(false);
  profile = signal<EmployeeProfileTO | null>(null);
  idUser = signal(0);

  emergencyForm = this.fb.nonNullable.group({
    dsName: [''],
    dsRelationship: [''],
    dsPhone: [''],
    dsPhoneAlt: [''],
  });

  private get apiHeaders(): Record<string, string> {
    const h: Record<string, string> = {
      Authorization: `Bearer ${this.authService.token()}`,
    };
    const tid = this.authService.tenantId();
    if (tid && tid !== 'ALL') h['X-Tenant-ID'] = tid;
    return h;
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.router.navigate(['/colaboradores']);
      return;
    }
    this.idUser.set(id);
    this.loadProfile(id);
  }

  private loadProfile(id: number): void {
    this.loading.set(true);

    // Endpoint getProfile Sprint 15; si falla, caer en getTabUser
    const profileUrl = `${environment.gatewayUrl}/api/user/user/employee/${id}/profile`;
    const fallbackUrl = `${environment.gatewayUrl}/api/user/user/getTabUser?idUser=${id}`;

    this.http.get<any>(profileUrl, { headers: this.apiHeaders }).subscribe({
      next: (data) => {
        this.mapProfile(data);
        this.loading.set(false);
      },
      error: () => {
        // Fallback: construir perfil desde getEmployee
        this.http.get<any>(fallbackUrl, { headers: this.apiHeaders }).subscribe({
          next: (emp) => {
            this.mapFromEmployee(emp);
            this.loading.set(false);
          },
          error: () => {
            this.loading.set(false);
            this.snackBar.open('Error al cargar el perfil del colaborador', 'Cerrar', {
              duration: 4000,
            });
          },
        });
      },
    });
  }

  private mapProfile(data: any): void {
    // Mapeo cuando el backend retorna EmployeeProfileTO completo
    const profile: EmployeeProfileTO = {
      employee: {
        idUser: data.employee?.idUser ?? data.idUser ?? this.idUser(),
        dsName: data.employee?.dsName ?? data.dsName ?? '',
        dsSex: data.employee?.dsSex ?? data.dsSex ?? '',
        dtBirthdate: data.employee?.dtBirthdate ?? '',
        dsCurp: data.employee?.dsCurp ?? '',
        dsRfc: data.employee?.dsRfc ?? '',
        dsNss: data.employee?.dsNss ?? '',
        dsPhone: data.employee?.dsPhone ?? '',
        dsEmail: data.employee?.dsEmail ?? '',
        dsTerminationReason: data.employee?.dsTerminationReason ?? '',
        dtTerminationDate: data.employee?.dtTerminationDate ?? '',
        dsDchExecutive: data.employee?.dsDchExecutive ?? '',
        dsEmployeeNumber: data.employee?.dsEmployeeNumber ?? '',
        fgActive: data.employee?.fgActive ?? true,
      },
      complementary: {
        dsJobTitle: data.complementary?.dsJobTitle ?? '',
        dsArea: data.complementary?.dsArea ?? '',
        dsWorkSchedule: data.complementary?.dsWorkSchedule ?? '',
        dsContractType: data.complementary?.dsContractType ?? '',
        dsEmployeeType: data.complementary?.dsEmployeeType ?? '',
        dsVacationBalance: data.complementary?.dsVacationBalance ?? 0,
      },
      address: {
        dsStreet: data.address?.dsStreet ?? '',
        dsNeighborhood: data.address?.dsNeighborhood ?? '',
        dsCity: data.address?.dsCity ?? '',
        dsState: data.address?.dsState ?? '',
        dsZipCode: data.address?.dsZipCode ?? '',
      },
      contracting: {
        dtHireDate: data.contracting?.dtHireDate ?? '',
        dsWorkShift: data.contracting?.dsWorkShift ?? '',
      },
      assignment: {
        dsClientName: data.assignment?.dsClientName ?? '',
        dsProjectName: data.assignment?.dsProjectName ?? '',
        dsWorkCenter: data.assignment?.dsWorkCenter ?? '',
        dsRegion: data.assignment?.dsRegion ?? '',
      },
      emergencyContact: {
        dsName: data.emergencyContact?.dsName ?? '',
        dsRelationship: data.emergencyContact?.dsRelationship ?? '',
        dsPhone: data.emergencyContact?.dsPhone ?? '',
        dsPhoneAlt: data.emergencyContact?.dsPhoneAlt ?? '',
      },
      tenantName: data.tenantName ?? '',
      vacationBalance: data.vacationBalance ?? 0,
    };
    this.profile.set(profile);
    this.patchEmergencyForm(profile.emergencyContact);
  }

  private mapFromEmployee(emp: any): void {
    // Construir perfil parcial desde el endpoint getEmployee (legado)
    const profile: EmployeeProfileTO = {
      employee: {
        idUser: emp.idUser ?? emp.user?.id ?? emp.id,
        dsName: [emp.name ?? '', emp.lastName ?? '', emp.lastMName ?? ''].filter(Boolean).join(' '),
        dsSex: emp.gender ?? emp.dsSex ?? '',
        dtBirthdate: emp.complementary?.birthDate ?? emp.birthDate ?? '',
        dsCurp: emp.complementary?.curp ?? emp.curp ?? '',
        dsRfc: emp.complementary?.rfc ?? emp.rfc ?? '',
        dsNss: emp.complementary?.nss ?? emp.nss ?? '',
        dsPhone: emp.complementary?.phone ?? emp.phone ?? '',
        dsEmail: emp.complementary?.email ?? emp.user?.email ?? emp.email ?? '',
        dsTerminationReason: '',
        dtTerminationDate: '',
        dsDchExecutive: '',
        dsEmployeeNumber: emp.dsEmployeeNumber ?? emp.employeeNumber ?? '',
        fgActive: emp.fgActive ?? emp.active ?? emp.status ?? true,
      },
      complementary: {
        dsJobTitle: emp.complementary?.dsJobTitle ?? emp.contracting?.jobTitle ?? '',
        dsArea: emp.complementary?.dsArea ?? emp.contracting?.area ?? '',
        dsWorkSchedule: emp.complementary?.workSchedule ?? '',
        dsContractType: emp.complementary?.contractType ?? '',
        dsEmployeeType: emp.userType ?? '',
        dsVacationBalance: 0,
      },
      address: {
        dsStreet: emp.address?.street ?? '',
        dsNeighborhood: emp.address?.neighborhood ?? '',
        dsCity: emp.address?.city ?? '',
        dsState: emp.address?.state ?? '',
        dsZipCode: emp.address?.zipCode ?? '',
      },
      contracting: {
        dtHireDate: emp.contracting?.hireDate ?? '',
        dsWorkShift: emp.contracting?.workShift ?? '',
      },
      assignment: {
        dsClientName: emp.assignment?.clientName ?? '',
        dsProjectName: emp.assignment?.projectName ?? '',
        dsWorkCenter: emp.assignment?.workCenter ?? '',
        dsRegion: emp.assignment?.region ?? '',
      },
      emergencyContact: {
        dsName: '',
        dsRelationship: '',
        dsPhone: '',
        dsPhoneAlt: '',
      },
      tenantName: emp.tenantId ?? '',
      vacationBalance: 0,
    };
    this.profile.set(profile);
    this.patchEmergencyForm(profile.emergencyContact);
  }

  private patchEmergencyForm(contact: EmployeeProfileTO['emergencyContact']): void {
    this.emergencyForm.patchValue({
      dsName: contact.dsName ?? '',
      dsRelationship: contact.dsRelationship ?? '',
      dsPhone: contact.dsPhone ?? '',
      dsPhoneAlt: contact.dsPhoneAlt ?? '',
    });
  }

  saveEmergencyContact(): void {
    if (this.emergencyForm.invalid) {
      this.emergencyForm.markAllAsTouched();
      return;
    }
    this.savingContact.set(true);
    const url = `${environment.gatewayUrl}/api/user/user/employee/${this.idUser()}/emergency`;
    this.http
      .post<any>(url, this.emergencyForm.getRawValue(), { headers: this.apiHeaders })
      .subscribe({
        next: () => {
          this.savingContact.set(false);
          this.snackBar.open('Contacto de emergencia guardado correctamente', 'Cerrar', {
            duration: 3000,
          });
        },
        error: () => {
          this.savingContact.set(false);
          this.snackBar.open('Error al guardar contacto de emergencia', 'Cerrar', {
            duration: 4000,
          });
        },
      });
  }

  openBajaDialog(): void {
    const dialogRef = this.dialog.open(BajaDialogComponent, { width: '480px' });
    dialogRef.afterClosed().subscribe((result) => {
      if (!result) return;
      const url = `${environment.gatewayUrl}/api/user/user/employee/${this.idUser()}/termination`;
      const payload = {
        dsTerminationReason: result.reason,
        dtTerminationDate: result.terminationDate,
        fgActive: false,
      };
      this.http.put<any>(url, payload, { headers: this.apiHeaders }).subscribe({
        next: () => {
          this.snackBar.open('Baja registrada correctamente', 'Cerrar', { duration: 3000 });
          // Actualizar estado local sin recargar
          const p = this.profile();
          if (p) {
            this.profile.set({
              ...p,
              employee: {
                ...p.employee,
                fgActive: false,
                dsTerminationReason: result.reason,
                dtTerminationDate: result.terminationDate,
              },
            });
          }
        },
        error: () => {
          this.snackBar.open('Error al registrar la baja', 'Cerrar', { duration: 4000 });
        },
      });
    });
  }

  goBack(): void {
    this.router.navigate(['/colaboradores']);
  }

  getInitials(name: string): string {
    const parts = name.trim().split(' ');
    if (parts.length === 0) return 'C';
    if (parts.length === 1) return parts[0].charAt(0).toUpperCase();
    return (parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
  }

  getSexLabel(sex: string): string {
    if (sex === 'M') return 'Masculino';
    if (sex === 'F') return 'Femenino';
    return sex || '—';
  }

  formatDate(dateStr: string | null | undefined): string {
    if (!dateStr) return '—';
    try {
      return new Date(dateStr).toLocaleDateString('es-MX', {
        day: '2-digit',
        month: 'long',
        year: 'numeric',
      });
    } catch {
      return dateStr;
    }
  }

  canRegistrarBaja(): boolean {
    const role = this.authService.userRole();
    return role === 'DCH_SUPER_ADMIN' || role === 'DCH_ADMIN' || role === 'DCH_RRHH';
  }
}
