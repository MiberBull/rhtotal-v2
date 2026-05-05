import { Component, OnInit, inject, signal, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { provideNativeDateAdapter } from '@angular/material/core';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-documents-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatAutocompleteModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './documents-list.component.html',
  styleUrl: './documents-list.component.scss',
})
export class DocumentsListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private destroyRef = inject(DestroyRef);

  // State signals
  documents = signal<any[]>([]);
  loading = signal(true);
  totalCount = signal(0);
  pageIndex = signal(0);
  pageSize = signal(10);

  // Filter state
  employeeSearchCtrl = new FormControl('');
  employeeSuggestions = signal<any[]>([]);
  selectedEmployeeId = signal<number | null>(null);
  selectedType = signal<string>('');
  dateFrom = signal<Date | null>(null);
  dateTo = signal<Date | null>(null);

  // Table configuration
  displayedColumns = ['employeeName', 'documentType', 'fileName', 'uploadDate', 'actions'];

  // Search debounce for employee autocomplete
  private employeeSearchSubject = new Subject<string>();

  documentTypes = [
    { value: '', label: 'Todos' },
    { value: 'CFDI', label: 'CFDI' },
    { value: 'CONSTANCIA', label: 'Constancia' },
    { value: 'OTRO', label: 'Otro' },
  ];

  ngOnInit(): void {
    this.employeeSearchSubject
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe((term) => {
        if (term && term.length >= 2) {
          this.searchEmployees(term);
        } else {
          this.employeeSuggestions.set([]);
        }
      });

    this.loadDocuments();
  }

  onEmployeeSearch(value: string): void {
    this.employeeSearchSubject.next(value);
  }

  onEmployeeSelected(employee: any): void {
    this.selectedEmployeeId.set(employee.id);
    this.pageIndex.set(0);
    this.loadDocuments();
  }

  clearEmployeeFilter(): void {
    this.employeeSearchCtrl.setValue('');
    this.selectedEmployeeId.set(null);
    this.employeeSuggestions.set([]);
    this.pageIndex.set(0);
    this.loadDocuments();
  }

  onTypeChange(type: string): void {
    this.selectedType.set(type);
    this.pageIndex.set(0);
    this.loadDocuments();
  }

  onDateFromChange(date: Date | null): void {
    this.dateFrom.set(date);
    this.pageIndex.set(0);
    this.loadDocuments();
  }

  onDateToChange(date: Date | null): void {
    this.dateTo.set(date);
    this.pageIndex.set(0);
    this.loadDocuments();
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadDocuments();
  }

  navigateToUpload(): void {
    this.router.navigate(['/documents/upload']);
  }

  downloadDocument(doc: any): void {
    if (doc.fileUrl) {
      window.open(doc.fileUrl, '_blank');
    }
  }

  deleteDocument(doc: any): void {
    if (!confirm(`¿Estas seguro de eliminar "${doc.fileName}"?`)) return;

    this.http
      .delete(`${environment.gatewayUrl}/api/user/document/deleteDocument/${doc.id}`)
      .subscribe({
        next: () => {
          this.documents.update((list) => list.filter((d) => d.id !== doc.id));
          this.totalCount.update((c) => c - 1);
          this.snackBar.open('Documento eliminado', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
        error: () => {
          this.snackBar.open('Error al eliminar el documento', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
      });
  }

  displayEmployee(employee: any): string {
    return employee ? `${employee.name} ${employee.lastName}` : '';
  }

  getTypeLabel(type: string): string {
    switch (type) {
      case 'CFDI': return 'CFDI';
      case 'CONSTANCIA': return 'Constancia';
      case 'OTRO': return 'Otro';
      default: return type || '—';
    }
  }

  private loadDocuments(): void {
    this.loading.set(true);

    let params = new HttpParams()
      .set('page', this.pageIndex().toString())
      .set('size', this.pageSize().toString());

    if (this.selectedEmployeeId()) {
      params = params.set('employeeId', this.selectedEmployeeId()!.toString());
    }
    if (this.selectedType()) {
      params = params.set('type', this.selectedType());
    }
    if (this.dateFrom()) {
      params = params.set('dateFrom', this.dateFrom()!.toISOString().split('T')[0]);
    }
    if (this.dateTo()) {
      params = params.set('dateTo', this.dateTo()!.toISOString().split('T')[0]);
    }

    this.http
      .get<any>(`${environment.gatewayUrl}/api/user/document/getAllDocuments`, { params })
      .subscribe({
        next: (response) => {
          const data = Array.isArray(response) ? response : response?.content ?? [];
          this.documents.set(data);
          if (response?.totalElements != null) {
            this.totalCount.set(response.totalElements);
          } else {
            this.totalCount.set(data.length);
          }
          this.loading.set(false);
        },
        error: () => {
          this.documents.set([]);
          this.loading.set(false);
        },
      });
  }

  private searchEmployees(term: string): void {
    this.http
      .get<any>(`${environment.gatewayUrl}/api/user/employee/search`, {
        params: new HttpParams().set('query', term),
      })
      .subscribe({
        next: (response) => {
          const data = Array.isArray(response) ? response : response?.content ?? [];
          this.employeeSuggestions.set(data);
        },
        error: () => {
          this.employeeSuggestions.set([]);
        },
      });
  }
}
