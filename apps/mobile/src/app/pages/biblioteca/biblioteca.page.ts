import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

interface DocumentoTO {
  idDocumento: number;
  dsNombre: string;
  dsCategoria: string;
  dsUrl: string;
  dtFecha: string;
}

@Component({
  selector: 'app-biblioteca',
  templateUrl: './biblioteca.page.html',
  standalone: false,
})
export class BibliotecaPage implements OnInit {
  documentos: DocumentoTO[] = [];
  documentosFiltrados: DocumentoTO[] = [];
  categorias: string[] = [];
  categoriaSeleccionada = '';
  terminoBusqueda = '';
  loading = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadDocumentos();
  }

  loadDocumentos(event?: any): void {
    this.loading = true;
    this.http.get<DocumentoTO[]>(`${environment.gatewayUrl}/api/document/library/list`).subscribe({
      next: (data) => {
        this.procesarDatos(data);
        this.loading = false;
        if (event) event.target.complete();
      },
      error: () => {
        // Fallback al endpoint de application
        this.http.get<any[]>(`${environment.gatewayUrl}/api/application/content/getAll`).subscribe({
          next: (data) => {
            const mapped: DocumentoTO[] = (data || []).map((item: any, idx: number) => ({
              idDocumento: item.id ?? idx,
              dsNombre: item.dsNombre ?? item.title ?? item.name ?? 'Documento',
              dsCategoria: item.dsCategoria ?? item.category ?? 'General',
              dsUrl: item.dsUrl ?? item.url ?? '',
              dtFecha: item.dtFecha ?? item.date ?? '',
            }));
            this.procesarDatos(mapped);
            this.loading = false;
            if (event) event.target.complete();
          },
          error: () => {
            this.loading = false;
            this.documentos = [];
            this.documentosFiltrados = [];
            if (event) event.target.complete();
          },
        });
      },
    });
  }

  private procesarDatos(data: DocumentoTO[]): void {
    this.documentos = data || [];
    const cats = [...new Set(this.documentos.map((d) => d.dsCategoria).filter(Boolean))];
    this.categorias = cats;
    this.aplicarFiltros();
  }

  buscar(event: any): void {
    this.terminoBusqueda = event.detail.value ?? '';
    this.aplicarFiltros();
  }

  filtrarCategoria(cat: string): void {
    this.categoriaSeleccionada = this.categoriaSeleccionada === cat ? '' : cat;
    this.aplicarFiltros();
  }

  private aplicarFiltros(): void {
    let resultado = this.documentos;

    if (this.categoriaSeleccionada) {
      resultado = resultado.filter((d) => d.dsCategoria === this.categoriaSeleccionada);
    }

    if (this.terminoBusqueda.trim()) {
      const term = this.terminoBusqueda.toLowerCase();
      resultado = resultado.filter(
        (d) =>
          d.dsNombre.toLowerCase().includes(term) ||
          (d.dsCategoria ?? '').toLowerCase().includes(term)
      );
    }

    this.documentosFiltrados = resultado;
  }

  descargar(url: string): void {
    if (url) {
      window.open(url, '_blank');
    }
  }
}
