import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { RoleTO } from '@dch/shared';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-role-form',
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
  templateUrl: './role-form.component.html',
  styleUrl: './role-form.component.scss',
})
export class RoleFormComponent implements OnInit {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);

  loading = signal(false);
  saving = signal(false);
  isEdit = signal(false);

  form!: FormGroup;
  private roleId: number | null = null;

  ngOnInit(): void {
    this.form = this.fb.group({
      nameRole: ['', Validators.required],
      description: [''],
      status: ['A'],
      permissions: ['[]'],
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'new') {
      this.isEdit.set(true);
      this.roleId = Number(id);
      this.loadRole(this.roleId);
    }
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    // Validate JSON permissions
    let permissions: any[];
    try {
      permissions = JSON.parse(this.form.value.permissions);
    } catch {
      this.snackBar.open('El campo de permisos debe ser JSON valido.', 'Cerrar', {
        duration: 4000,
      });
      return;
    }

    this.saving.set(true);

    const payload: any = {
      name: this.form.value.nameRole,
      description: this.form.value.description,
      status: this.form.value.status,
      permissions,
    };

    if (this.isEdit() && this.roleId) {
      payload.idRole = this.roleId;
    }

    this.http
      .post(`${environment.gatewayUrl}/api/security/role/saveOrUpdateRole`, payload)
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.snackBar.open(
            this.isEdit() ? 'Rol actualizado correctamente.' : 'Rol creado correctamente.',
            'Cerrar',
            { duration: 3000 }
          );
          this.router.navigate(['/roles']);
        },
        error: () => {
          this.saving.set(false);
          this.snackBar.open('Error al guardar el rol. Intenta de nuevo.', 'Cerrar', {
            duration: 4000,
          });
        },
      });
  }

  onCancel(): void {
    this.router.navigate(['/roles']);
  }

  private loadRole(id: number): void {
    this.loading.set(true);
    this.http
      .get<RoleTO>(`${environment.gatewayUrl}/api/security/role/getRole/${id}`)
      .subscribe({
        next: (role) => {
          this.form.patchValue({
            nameRole: role.name,
            description: role.description,
            status: role.status,
            permissions: JSON.stringify(role.permissions ?? [], null, 2),
          });
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.snackBar.open('Error al cargar el rol.', 'Cerrar', { duration: 4000 });
          this.router.navigate(['/roles']);
        },
      });
  }
}
