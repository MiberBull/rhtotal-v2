import { Component, inject, input, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormArray, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { UserApiService, EmployeeHistoryTO } from '@dch/shared';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatDividerModule,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss',
})
export class HistoryComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  idUser = input.required<number>();

  saving = signal(false);

  form = this.fb.group({
    entries: this.fb.array<FormGroup>([]),
  });

  get entries(): FormArray {
    return this.form.get('entries') as FormArray;
  }

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.userApi.getHistory(this.idUser()).subscribe({
      next: (data) => {
        if (data && data.length) {
          data.forEach((item) => this.addEntry(item));
        } else {
          this.addEntry();
        }
      },
      error: () => {
        this.addEntry();
        this.snackBar.open('Error al cargar historial laboral', 'Cerrar', { duration: 3000 });
      },
    });
  }

  addEntry(item?: EmployeeHistoryTO): void {
    const group = this.fb.nonNullable.group({
      idEmployeeHis: [item?.idEmployeeHis ?? 0],
      companyName: [item?.dsCompany ?? '', [Validators.required]],
      position: [item?.dsEmployeePosition ?? ''],
      startDate: [item?.entryDate ? new Date(item.entryDate).toISOString().split('T')[0] : ''],
      endDate: [item?.endDate ? new Date(item.endDate).toISOString().split('T')[0] : ''],
      description: [item?.dsIndustry ?? ''],
    });
    this.entries.push(group);
  }

  removeEntry(index: number): void {
    this.entries.removeAt(index);
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    const entriesData = this.entries.getRawValue();
    let completed = 0;
    let hasError = false;

    entriesData.forEach((entry: any) => {
      const payload: Partial<EmployeeHistoryTO> = {
        idEmployeeHis: entry.idEmployeeHis || undefined as any,
        idUser: this.idUser(),
        dsCompany: entry.companyName,
        dsEmployeePosition: entry.position,
        entryDate: entry.startDate ? new Date(entry.startDate) : undefined as any,
        endDate: entry.endDate ? new Date(entry.endDate) : undefined as any,
        dsIndustry: entry.description,
        active: true,
        lastUserModifier: 'admin',
        lastModification: new Date(),
      };

      this.userApi.saveHistory(payload as EmployeeHistoryTO).subscribe({
        next: () => {
          completed++;
          if (completed === entriesData.length) {
            this.saving.set(false);
            if (!hasError) {
              this.snackBar.open('Historial laboral guardado correctamente', 'Cerrar', { duration: 3000 });
            }
          }
        },
        error: () => {
          hasError = true;
          completed++;
          if (completed === entriesData.length) {
            this.saving.set(false);
            this.snackBar.open('Error al guardar historial laboral', 'Cerrar', { duration: 3000 });
          }
        },
      });
    });
  }
}
