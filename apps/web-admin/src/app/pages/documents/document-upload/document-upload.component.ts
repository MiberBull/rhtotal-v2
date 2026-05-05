import { Component, OnInit, inject, signal, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpEventType } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatChipsModule } from '@angular/material/chips';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { environment } from '../../../../environments/environment';

interface FileEntry {
  file: File;
  name: string;
  size: string;
  status: 'pending' | 'uploading' | 'done' | 'error';
  progress: number;
}

@Component({
  selector: 'app-document-upload',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatIconModule,
    MatButtonModule,
    MatProgressBarModule,
    MatSnackBarModule,
    MatChipsModule,
  ],
  templateUrl: './document-upload.component.html',
  styleUrl: './document-upload.component.scss',
})
export class DocumentUploadComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private destroyRef = inject(DestroyRef);

  // State
  files = signal<FileEntry[]>([]);
  uploading = signal(false);
  selectedType = signal<string>('CFDI');
  employeeSearchCtrl = new FormControl('');
  employeeSuggestions = signal<any[]>([]);
  selectedEmployee = signal<any | null>(null);
  isDragOver = signal(false);

  documentTypes = [
    { value: 'CFDI', label: 'CFDI' },
    { value: 'CONSTANCIA', label: 'Constancia' },
    { value: 'OTRO', label: 'Otro' },
  ];

  private employeeSearchSubject = new Subject<string>();

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
  }

  onEmployeeSearch(value: string): void {
    this.employeeSearchSubject.next(value);
  }

  onEmployeeSelected(employee: any): void {
    this.selectedEmployee.set(employee);
  }

  displayEmployee(employee: any): string {
    return employee ? `${employee.name} ${employee.lastName}` : '';
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(true);
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(false);
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(false);

    const droppedFiles = event.dataTransfer?.files;
    if (droppedFiles) {
      this.addFiles(droppedFiles);
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.addFiles(input.files);
      input.value = '';
    }
  }

  removeFile(index: number): void {
    this.files.update((list) => list.filter((_, i) => i !== index));
  }

  uploadAll(): void {
    if (this.files().length === 0) return;

    this.uploading.set(true);
    const filesToUpload = this.files().filter((f) => f.status === 'pending');
    let completed = 0;

    filesToUpload.forEach((entry, idx) => {
      const actualIndex = this.files().indexOf(entry);
      this.updateFileStatus(actualIndex, 'uploading', 0);

      const formData = new FormData();
      formData.append('file', entry.file);
      formData.append('type', this.selectedType());
      if (this.selectedEmployee()) {
        formData.append('employeeId', this.selectedEmployee().id.toString());
      }

      this.http
        .post(`${environment.gatewayUrl}/api/user/document/upload`, formData, {
          reportProgress: true,
          observe: 'events',
        })
        .subscribe({
          next: (event) => {
            if (event.type === HttpEventType.UploadProgress && event.total) {
              const progress = Math.round((100 * event.loaded) / event.total);
              this.updateFileStatus(actualIndex, 'uploading', progress);
            } else if (event.type === HttpEventType.Response) {
              this.updateFileStatus(actualIndex, 'done', 100);
              completed++;
              this.checkCompletion(completed, filesToUpload.length);
            }
          },
          error: () => {
            this.updateFileStatus(actualIndex, 'error', 0);
            completed++;
            this.checkCompletion(completed, filesToUpload.length);
          },
        });
    });
  }

  goBack(): void {
    this.router.navigate(['/documents']);
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 B';
    const k = 1024;
    const sizes = ['B', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(1)) + ' ' + sizes[i];
  }

  private addFiles(fileList: FileList): void {
    const validExtensions = ['.pdf', '.xml'];
    const newFiles: FileEntry[] = [];

    for (let i = 0; i < fileList.length; i++) {
      const file = fileList[i];
      const ext = '.' + file.name.split('.').pop()?.toLowerCase();
      if (validExtensions.includes(ext)) {
        newFiles.push({
          file,
          name: file.name,
          size: this.formatFileSize(file.size),
          status: 'pending',
          progress: 0,
        });
      }
    }

    if (newFiles.length === 0) {
      this.snackBar.open('Solo se permiten archivos .pdf y .xml', 'Cerrar', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
      });
      return;
    }

    this.files.update((list) => [...list, ...newFiles]);
  }

  private updateFileStatus(index: number, status: FileEntry['status'], progress: number): void {
    this.files.update((list) =>
      list.map((f, i) => (i === index ? { ...f, status, progress } : f))
    );
  }

  private checkCompletion(completed: number, total: number): void {
    if (completed === total) {
      this.uploading.set(false);
      const successful = this.files().filter((f) => f.status === 'done').length;
      const failed = this.files().filter((f) => f.status === 'error').length;

      if (failed === 0) {
        this.snackBar.open(`${successful} archivo(s) subidos exitosamente`, 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
      } else {
        this.snackBar.open(`${successful} exitosos, ${failed} con error`, 'Cerrar', {
          duration: 4000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
      }
    }
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
