import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatListModule } from '@angular/material/list';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-comunicado-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatRadioModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatListModule,
    MatCheckboxModule,
  ],
  templateUrl: './comunicado-form.component.html',
  styleUrl: './comunicado-form.component.scss',
})
export class ComunicadoFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private snackBar = inject(MatSnackBar);

  loading = signal(false);
  saving = signal(false);
  isEdit = signal(false);

  allEmployees = signal<any[]>([]);
  assignedIds = signal<Set<number>>(new Set());

  form = this.fb.nonNullable.group({
    titulo: ['', [Validators.required, Validators.maxLength(120)]],
    contenido: ['', [Validators.required]],
    fechaPublicacion: [null as Date | null],
    imagen: [''],
    destinatarios: ['todos'],
  });

  private comunicadoId: number | null = null;

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && idParam !== 'new') {
      this.isEdit.set(true);
      this.comunicadoId = Number(idParam);
      this.loadComunicado(this.comunicadoId);
    }
    this.loadEmployees();
  }

  get modoSeleccion(): boolean {
    return this.form.controls.destinatarios.value === 'seleccion';
  }

  toggleEmployee(id: number): void {
    this.assignedIds.update((ids) => {
      const s = new Set(ids);
      if (s.has(id)) {
        s.delete(id);
      } else {
        s.add(id);
      }
      return s;
    });
  }

  isAssigned(id: number): boolean {
    return this.assignedIds().has(id);
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.saving.set(true);
    const v = this.form.getRawValue();
    const payload: any = {
      titulo: v.titulo,
      contenido: v.contenido,
      fechaPublicacion: v.fechaPublicacion?.toISOString() ?? null,
      imagen: v.imagen || null,
      destinatarios: v.destinatarios === 'todos' ? [] : Array.from(this.assignedIds()),
    };
    if (this.isEdit()) payload.idComunicado = this.comunicadoId;

    this.http.post(`${environment.gatewayUrl}/api/application/comunicado/save`, payload).subscribe({
      next: () => {
        this.saving.set(false);
        this.snackBar.open(
          this.isEdit() ? 'Comunicado actualizado' : 'Comunicado creado',
          'Cerrar',
          { duration: 3000, horizontalPosition: 'end', verticalPosition: 'top' }
        );
        this.router.navigate(['/comunicados']);
      },
      error: () => {
        this.saving.set(false);
        this.snackBar.open('Error al guardar', 'Cerrar', {
          duration: 4000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
      },
    });
  }

  onCancel(): void {
    this.router.navigate(['/comunicados']);
  }

  private loadComunicado(id: number): void {
    this.loading.set(true);
    this.http.get<any>(`${environment.gatewayUrl}/api/application/comunicado/${id}`).subscribe({
      next: (data) => {
        this.form.patchValue({
          titulo: data.titulo ?? '',
          contenido: data.contenido ?? '',
          imagen: data.imagen ?? '',
          destinatarios: data.destinatarios?.length > 0 ? 'seleccion' : 'todos',
        });
        if (data.destinatarios?.length) {
          this.assignedIds.set(new Set(data.destinatarios.map((d: any) => d.idEmployee ?? d)));
        }
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.router.navigate(['/comunicados']);
      },
    });
  }

  private loadEmployees(): void {
    this.http.get<any>(`${environment.gatewayUrl}/api/user/employee/getAllEmployees`).subscribe({
      next: (res) => this.allEmployees.set(Array.isArray(res) ? res : (res?.content ?? [])),
      error: () => this.allEmployees.set([]),
    });
  }
}
