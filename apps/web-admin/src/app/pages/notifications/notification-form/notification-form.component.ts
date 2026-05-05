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

import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-notification-form',
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
  templateUrl: './notification-form.component.html',
  styleUrl: './notification-form.component.scss',
})
export class NotificationFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private snackBar = inject(MatSnackBar);

  loading = signal(false);
  saving = signal(false);
  isEdit = signal(false);

  form = this.fb.nonNullable.group({
    idNotification: [0],
    title: ['', [Validators.required]],
    message: ['', [Validators.required, Validators.maxLength(500)]],
    type: ['PUSH', [Validators.required]],
    status: ['A'],
  });

  private notificationId: number | null = null;

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam && idParam !== 'new') {
      this.isEdit.set(true);
      this.notificationId = Number(idParam);
      this.loadNotification(this.notificationId);
    }
  }

  get messageLength(): number {
    return this.form.controls.message.value?.length || 0;
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    const formValue = this.form.getRawValue();

    const payload: any = {
      idNotification: this.isEdit() ? this.notificationId : undefined,
      title: formValue.title,
      message: formValue.message,
      type: formValue.type,
      status: formValue.status,
    };

    this.http
      .post(`${environment.gatewayUrl}/api/application/notification/saveOrUpdateNotification`, payload)
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.snackBar.open(
            this.isEdit() ? 'Notificacion actualizada correctamente' : 'Notificacion creada correctamente',
            'Cerrar',
            { duration: 3000, horizontalPosition: 'end', verticalPosition: 'top' }
          );
          this.router.navigate(['/notifications']);
        },
        error: () => {
          this.saving.set(false);
          this.snackBar.open('Error al guardar la notificacion', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
      });
  }

  onCancel(): void {
    this.router.navigate(['/notifications']);
  }

  private loadNotification(id: number): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/notification/getNotification/${id}`)
      .subscribe({
        next: (notification) => {
          this.form.patchValue({
            idNotification: notification.idNotification ?? id,
            title: notification.title ?? '',
            message: notification.message ?? '',
            type: notification.type ?? 'PUSH',
            status: notification.status ?? 'A',
          });
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.snackBar.open('Error al cargar la notificacion', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.router.navigate(['/notifications']);
        },
      });
  }
}
