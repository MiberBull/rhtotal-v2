import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

export interface DocumentoCFDI {
  id: number;
  nombre: string;
  tipo: 'CFDI' | 'RECIBO' | 'CONTRATO' | 'OTRO';
  fechaEmision: string;
  periodo: string;
  urlDescarga: string;
  estado: 'DISPONIBLE' | 'PROCESANDO';
}

@Component({
  selector: 'app-documents',
  templateUrl: './documents.page.html',
  styleUrls: ['./documents.page.scss'],
  standalone: false,
})
export class DocumentsPage implements OnInit {
  documentos: DocumentoCFDI[] = [];
  documentosFiltrados: DocumentoCFDI[] = [];
  loading = true;
  filtroActivo: string = 'TODOS';
  filtros = ['TODOS', 'CFDI', 'RECIBO', 'CONTRATO', 'OTRO'];

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadDocumentos();
  }

  doRefresh(event: any): void {
    this.loadDocumentos(() => event.target.complete());
  }

  aplicarFiltro(filtro: string): void {
    this.filtroActivo = filtro;
    this.documentosFiltrados =
      filtro === 'TODOS' ? [...this.documentos] : this.documentos.filter((d) => d.tipo === filtro);
  }

  descargar(doc: DocumentoCFDI): void {
    window.open(doc.urlDescarga, '_blank');
  }

  getTipoIcon(tipo: string): string {
    switch (tipo) {
      case 'CFDI':
        return 'receipt-outline';
      case 'RECIBO':
        return 'cash-outline';
      case 'CONTRATO':
        return 'document-text-outline';
      default:
        return 'document-outline';
    }
  }

  private loadDocumentos(callback?: () => void): void {
    this.loading = true;
    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/document/cfdi/getByUser/${userId}`;

    this.http.get<DocumentoCFDI[]>(url).subscribe({
      next: (data) => {
        this.documentos = Array.isArray(data) ? data : [];
        this.aplicarFiltro(this.filtroActivo);
        this.loading = false;
        callback?.();
      },
      error: () => {
        this.documentos = [];
        this.documentosFiltrados = [];
        this.loading = false;
        callback?.();
      },
    });
  }
}
