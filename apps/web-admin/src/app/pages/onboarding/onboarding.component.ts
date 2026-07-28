import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-advance-stage-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, MatSelectModule, FormsModule],
  template: `
    <h2 mat-dialog-title>Avanzar Etapa — {{ data.name }}</h2>
    <mat-dialog-content>
      <p>
        Etapa actual: <strong>{{ data.currentStage }}</strong>
      </p>
      <p>Selecciona la nueva etapa:</p>
      <mat-select [(ngModel)]="selectedStage" style="width:100%">
        @for (s of stages; track s) {
          <mat-option [value]="s">{{ s }}</mat-option>
        }
      </mat-select>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button
        mat-flat-button
        color="primary"
        [mat-dialog-close]="selectedStage"
        [disabled]="!selectedStage"
      >
        Avanzar
      </button>
    </mat-dialog-actions>
  `,
})
export class AdvanceStageDialogComponent {
  data = inject(MAT_DIALOG_DATA);
  selectedStage = '';
  readonly stages = ['POSTULADO', 'ENTREVISTA', 'SELECCIONADO', 'ONBOARDING', 'CONTRATADO'];
}

@Component({
  selector: 'app-onboarding',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatTabsModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatInputModule,
    MatFormFieldModule,
    MatChipsModule,
    MatDialogModule,
  ],
  templateUrl: './onboarding.component.html',
  styleUrl: './onboarding.component.scss',
})
export class OnboardingComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  readonly stages = [
    { label: 'Todos', value: '' },
    { label: 'Postulado', value: 'POSTULADO' },
    { label: 'Entrevista', value: 'ENTREVISTA' },
    { label: 'Seleccionado', value: 'SELECCIONADO' },
    { label: 'Onboarding', value: 'ONBOARDING' },
    { label: 'Contratado', value: 'CONTRATADO' },
  ];

  candidates = signal<any[]>([]);
  loading = signal(true);
  totalCount = signal(0);
  pageIndex = signal(0);
  pageSize = signal(10);
  activeStage = signal('');
  searchTerm = signal('');

  displayedColumns = ['name', 'email', 'position', 'stage', 'docsStatus', 'createdAt', 'actions'];

  private searchSubject = new Subject<string>();

  ngOnInit(): void {
    this.searchSubject.pipe(debounceTime(400), distinctUntilChanged()).subscribe((term) => {
      this.searchTerm.set(term);
      this.pageIndex.set(0);
      this.loadCandidates();
    });
    this.loadCandidates();
  }

  onStageChange(index: number): void {
    this.activeStage.set(this.stages[index].value);
    this.pageIndex.set(0);
    this.loadCandidates();
  }

  onSearchChange(value: string): void {
    this.searchSubject.next(value);
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadCandidates();
  }

  openAdvanceDialog(candidate: any): void {
    const ref = this.dialog.open(AdvanceStageDialogComponent, {
      width: '400px',
      data: { name: candidate.dsName, currentStage: candidate.dsStage },
    });
    ref.afterClosed().subscribe((newStage) => {
      if (!newStage) return;
      // Backend: PUT /pipeline/{candidateId}/advance (no /candidate/{id}/advance)
      this.http
        .put(`${environment.gatewayUrl}/api/onboarding/pipeline/${candidate.id}/advance`, {
          stage: newStage,
          notes: '',
          createdBy: 'sistema',
        })
        .subscribe({
          next: () => {
            this.snackBar.open(`Candidato avanzado a ${newStage}`, 'Cerrar', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
            });
            this.loadCandidates();
          },
          error: () =>
            this.snackBar.open('Error al avanzar etapa', 'Cerrar', {
              duration: 4000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
            }),
        });
    });
  }

  getStageClass(stage: string): string {
    switch (stage) {
      case 'POSTULADO':
        return 'stage-postulado';
      case 'ENTREVISTA':
        return 'stage-entrevista';
      case 'SELECCIONADO':
        return 'stage-seleccionado';
      case 'ONBOARDING':
        return 'stage-onboarding';
      case 'CONTRATADO':
        return 'stage-contratado';
      default:
        return '';
    }
  }

  getDocsStatusClass(status: string): string {
    if (status === 'COMPLETO') return 'docs-complete';
    if (status === 'INCOMPLETO') return 'docs-incomplete';
    return '';
  }

  private loadCandidates(): void {
    this.loading.set(true);
    const params: any = {
      page: this.pageIndex().toString(),
      size: this.pageSize().toString(),
    };
    if (this.searchTerm()) params['search'] = this.searchTerm();

    // Backend: por etapa → /candidate/stage/{stage}; sin etapa → /candidate/all
    const stage = this.activeStage();
    const url = stage
      ? `${environment.gatewayUrl}/api/onboarding/candidate/stage/${stage}`
      : `${environment.gatewayUrl}/api/onboarding/candidate/all`;

    this.http.get<any>(url, { params }).subscribe({
      next: (res) => {
        const data = Array.isArray(res) ? res : (res?.content ?? []);
        this.candidates.set(data);
        this.totalCount.set(res?.totalElements ?? data.length);
        this.loading.set(false);
      },
      error: () => {
        this.candidates.set([]);
        this.loading.set(false);
      },
    });
  }
}
