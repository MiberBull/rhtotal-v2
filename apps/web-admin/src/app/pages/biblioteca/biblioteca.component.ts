import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatChipsModule } from '@angular/material/chips';
import { MatBadgeModule } from '@angular/material/badge';

import { AuthService } from '../../core/auth/auth.service';
import { environment } from '../../../environments/environment';

interface Category {
  idCategory: number;
  name: string;
  description: string;
  icon: string;
  active: boolean;
}

interface Document {
  idDocument: number;
  tenantId: string;
  idCategory: number;
  categoryName: string;
  categoryIcon: string;
  title: string;
  description: string;
  fileName: string;
  mimeType: string;
  version: string;
  visibility: string;
  requiresAck: boolean;
  active: boolean;
  publicationDate: string;
  expiryDate: string;
  publishedBy: string;
  creationDate: string;
}

interface DocumentDetail extends Document {
  fileContent: string;
}

@Component({
  selector: 'app-biblioteca',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatTabsModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatSelectModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatBadgeModule,
  ],
  templateUrl: './biblioteca.component.html',
  styleUrl: './biblioteca.component.scss',
})
export class BibliotecaComponent implements OnInit {
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);
  private authService = inject(AuthService);

  // State signals
  categories = signal<Category[]>([]);
  documents = signal<Document[]>([]);
  loading = signal(false);
  uploadLoading = signal(false);

  // Filter
  selectedCategory = signal<number | null>(null);
  searchText = signal('');

  // New document form
  showUploadForm = signal(false);
  newDoc = signal<Partial<Document> & { fileContent?: string; fileBase64?: string }>({
    visibility: 'GENERAL',
    requiresAck: false,
    version: '1.0',
  });

  // Acks dialog
  selectedDocForAcks = signal<Document | null>(null);
  acks = signal<any[]>([]);
  acksLoading = signal(false);

  displayedColumns = [
    'icon',
    'title',
    'category',
    'version',
    'visibility',
    'ack',
    'date',
    'actions',
  ];

  ngOnInit(): void {
    this.loadAll();
  }

  get tenantId(): string {
    return this.authService.currentUser()?.tenantId ?? 'demo';
  }

  loadAll(): void {
    this.loading.set(true);
    const params = new HttpParams().set('tenantId', this.tenantId);

    this.http
      .get<Category[]>(`${environment.gatewayUrl}/api/application/library/categories`, { params })
      .subscribe({
        next: (cats) => this.categories.set(cats),
        error: () => {},
      });

    this.http
      .get<Document[]>(`${environment.gatewayUrl}/api/application/library/documents`, { params })
      .subscribe({
        next: (docs) => {
          this.documents.set(docs);
          this.loading.set(false);
        },
        error: () => this.loading.set(false),
      });
  }

  get filteredDocuments(): Document[] {
    let docs = this.documents();
    const cat = this.selectedCategory();
    const q = this.searchText().toLowerCase();
    if (cat) docs = docs.filter((d) => d.idCategory === cat);
    if (q)
      docs = docs.filter(
        (d) => d.title.toLowerCase().includes(q) || (d.description ?? '').toLowerCase().includes(q)
      );
    return docs;
  }

  openUploadForm(): void {
    this.newDoc.set({ visibility: 'GENERAL', requiresAck: false, version: '1.0' });
    this.showUploadForm.set(true);
  }

  cancelUpload(): void {
    this.showUploadForm.set(false);
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;
    const file = input.files[0];
    const current = this.newDoc();
    this.newDoc.set({ ...current, fileName: file.name, mimeType: file.type });

    const reader = new FileReader();
    reader.onload = () => {
      const base64 = (reader.result as string).split(',')[1];
      this.newDoc.set({ ...this.newDoc(), fileContent: base64 });
    };
    reader.readAsDataURL(file);
  }

  saveDocument(): void {
    const doc = this.newDoc();
    if (!doc.title || !doc.idCategory || !doc.fileContent) {
      this.snackBar.open('Título, categoría y archivo son requeridos', 'Cerrar', {
        duration: 3000,
      });
      return;
    }

    this.uploadLoading.set(true);
    const payload = {
      tenantId: this.tenantId,
      idCategory: doc.idCategory,
      title: doc.title,
      description: doc.description,
      fileName: doc.fileName,
      mimeType: doc.mimeType,
      fileContent: doc.fileContent,
      version: doc.version ?? '1.0',
      visibility: doc.visibility ?? 'GENERAL',
      requiresAck: doc.requiresAck ?? false,
      publishedBy: this.authService.currentUser()?.email ?? 'admin',
    };

    this.http
      .post<Document>(`${environment.gatewayUrl}/api/application/library/document`, payload)
      .subscribe({
        next: () => {
          this.snackBar.open('Documento subido correctamente', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.showUploadForm.set(false);
          this.uploadLoading.set(false);
          this.loadAll();
        },
        error: () => {
          this.snackBar.open('Error al subir el documento', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.uploadLoading.set(false);
        },
      });
  }

  downloadDocument(doc: Document): void {
    this.http
      .get<DocumentDetail>(
        `${environment.gatewayUrl}/api/application/library/document/${doc.idDocument}`
      )
      .subscribe({
        next: (detail) => {
          if (!detail.fileContent) return;
          const byteArray = Uint8Array.from(atob(detail.fileContent), (c) => c.charCodeAt(0));
          const blob = new Blob([byteArray], {
            type: detail.mimeType || 'application/octet-stream',
          });
          const url = URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = detail.fileName || doc.title;
          a.click();
          URL.revokeObjectURL(url);
        },
        error: () =>
          this.snackBar.open('Error al descargar el documento', 'Cerrar', { duration: 3000 }),
      });
  }

  deleteDocument(doc: Document): void {
    if (!confirm(`¿Eliminar "${doc.title}"?`)) return;
    this.http
      .delete(`${environment.gatewayUrl}/api/application/library/document/${doc.idDocument}`)
      .subscribe({
        next: () => {
          this.snackBar.open('Documento eliminado', 'Cerrar', { duration: 3000 });
          this.loadAll();
        },
        error: () => this.snackBar.open('Error al eliminar', 'Cerrar', { duration: 3000 }),
      });
  }

  viewAcks(doc: Document): void {
    this.selectedDocForAcks.set(doc);
    this.acksLoading.set(true);
    this.http
      .get<
        any[]
      >(`${environment.gatewayUrl}/api/application/library/document/${doc.idDocument}/acks`)
      .subscribe({
        next: (list) => {
          this.acks.set(list);
          this.acksLoading.set(false);
        },
        error: () => this.acksLoading.set(false),
      });
  }

  closeAcks(): void {
    this.selectedDocForAcks.set(null);
    this.acks.set([]);
  }

  getVisibilityLabel(v: string): string {
    const map: Record<string, string> = {
      GENERAL: 'General',
      BY_CLIENT: 'Por Cliente',
      STAFF_ONLY: 'Solo Personal',
    };
    return map[v] ?? v;
  }

  getVisibilityColor(v: string): string {
    const map: Record<string, string> = {
      GENERAL: 'primary',
      BY_CLIENT: 'accent',
      STAFF_ONLY: 'warn',
    };
    return map[v] ?? 'primary';
  }

  updateDocField(field: string, value: any): void {
    this.newDoc.set({ ...this.newDoc(), [field]: value });
  }
}
