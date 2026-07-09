import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatExpansionModule } from '@angular/material/expansion';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-survey-form-dialog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
  ],
  template: `
    <h2 mat-dialog-title>{{ data.survey ? 'Editar Encuesta' : 'Nueva Encuesta' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="form" class="form-content">
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Título</mat-label>
          <input
            matInput
            formControlName="dsTitle"
            placeholder="Ej: Encuesta de clima organizacional"
          />
        </mat-form-field>
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Descripción</mat-label>
          <textarea matInput formControlName="dsDescription" rows="3"></textarea>
        </mat-form-field>
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Tipo</mat-label>
          <mat-select formControlName="dsType">
            <mat-option value="CLIMA">Clima Laboral</mat-option>
            <mat-option value="SATISFACCION">Satisfacción</mat-option>
            <mat-option value="DESEMPENO">Desempeño</mat-option>
            <mat-option value="OTRO">Otro</mat-option>
          </mat-select>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button
        mat-flat-button
        color="primary"
        [mat-dialog-close]="form.value"
        [disabled]="form.invalid"
      >
        {{ data.survey ? 'Guardar' : 'Crear' }}
      </button>
    </mat-dialog-actions>
  `,
  styles: [
    '.form-content { display: flex; flex-direction: column; gap: 8px; padding-top: 8px; }',
    '.full-width { width: 100%; }',
  ],
})
export class SurveyFormDialogComponent implements OnInit {
  data = inject(MAT_DIALOG_DATA);
  private fb = inject(FormBuilder);
  form!: FormGroup;

  ngOnInit(): void {
    this.form = this.fb.group({
      dsTitle: [this.data.survey?.dsTitle ?? '', Validators.required],
      dsDescription: [this.data.survey?.dsDescription ?? ''],
      dsType: [this.data.survey?.dsType ?? 'CLIMA', Validators.required],
    });
  }
}

@Component({
  selector: 'app-encuestas',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatDialogModule,
    MatSlideToggleModule,
    MatExpansionModule,
  ],
  templateUrl: './encuestas.component.html',
  styleUrl: './encuestas.component.scss',
})
export class EncuestasComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  surveys = signal<any[]>([]);
  loading = signal(true);
  expandedSurveyId = signal<number | null>(null);
  surveyResults = signal<Record<number, any>>({});

  displayedColumns = ['title', 'type', 'status', 'responses', 'createdAt', 'actions'];

  ngOnInit(): void {
    this.loadSurveys();
  }

  openCreate(): void {
    const ref = this.dialog.open(SurveyFormDialogComponent, {
      width: '500px',
      data: { survey: null },
    });
    ref.afterClosed().subscribe((data) => {
      if (!data) return;
      this.http.post(`${environment.gatewayUrl}/api/hr/survey`, data).subscribe({
        next: () => {
          this.snackBar.open('Encuesta creada', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadSurveys();
        },
        error: () =>
          this.snackBar.open('Error al crear encuesta', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
    });
  }

  publish(survey: any): void {
    this.http.put(`${environment.gatewayUrl}/api/hr/survey/${survey.id}/publish`, {}).subscribe({
      next: () => {
        this.snackBar.open('Encuesta publicada', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
        this.loadSurveys();
      },
      error: () =>
        this.snackBar.open('Error al publicar', 'Cerrar', {
          duration: 4000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        }),
    });
  }

  close(survey: any): void {
    this.http.put(`${environment.gatewayUrl}/api/hr/survey/${survey.id}/close`, {}).subscribe({
      next: () => {
        this.snackBar.open('Encuesta cerrada', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
        this.loadSurveys();
      },
      error: () =>
        this.snackBar.open('Error al cerrar', 'Cerrar', {
          duration: 4000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        }),
    });
  }

  viewResults(survey: any): void {
    const id = survey.id;
    if (this.expandedSurveyId() === id) {
      this.expandedSurveyId.set(null);
      return;
    }
    this.expandedSurveyId.set(id);
    if (!this.surveyResults()[id]) {
      this.http.get(`${environment.gatewayUrl}/api/hr/survey/${id}/results`).subscribe({
        next: (res) => {
          this.surveyResults.update((r) => ({ ...r, [id]: res }));
        },
        error: () => {},
      });
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'BORRADOR':
        return 'status-draft';
      case 'PUBLICADA':
        return 'status-published';
      case 'CERRADA':
        return 'status-closed';
      default:
        return '';
    }
  }

  getTypeLabel(type: string): string {
    switch (type) {
      case 'CLIMA':
        return 'Clima Laboral';
      case 'SATISFACCION':
        return 'Satisfacción';
      case 'DESEMPENO':
        return 'Desempeño';
      default:
        return type ?? '—';
    }
  }

  private loadSurveys(): void {
    this.loading.set(true);
    this.http.get<any[]>(`${environment.gatewayUrl}/api/hr/survey`).subscribe({
      next: (res) => {
        const data = Array.isArray(res) ? res : ((res as any)?.content ?? []);
        this.surveys.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.surveys.set([]);
        this.loading.set(false);
      },
    });
  }
}
