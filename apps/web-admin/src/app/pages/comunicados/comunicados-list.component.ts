import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { environment } from '../../../environments/environment';

export interface Comunicado {
  idComunicado: number;
  titulo: string;
  contenido: string;
  fechaPublicacion: string | null;
  imagen: string | null;
  estado: 'BORRADOR' | 'PUBLICADO' | 'ARCHIVADO';
  totalDestinatarios: number;
  totalLeidos: number;
  fechaCreacion: string;
}

@Component({
  selector: 'app-comunicados-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatSnackBarModule,
  ],
  templateUrl: './comunicados-list.component.html',
  styleUrl: './comunicados-list.component.scss',
})
export class ComunicadosListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  comunicados = signal<Comunicado[]>([]);
  loading = signal(true);

  displayedColumns = ['titulo', 'estado', 'fechaPublicacion', 'lecturas', 'actions'];

  ngOnInit(): void {
    this.loadComunicados();
  }

  navigateToNew(): void {
    this.router.navigate(['/comunicados/new']);
  }

  navigateToEdit(c: Comunicado): void {
    this.router.navigate(['/comunicados', c.idComunicado]);
  }

  navigateToLecturas(c: Comunicado): void {
    this.router.navigate(['/comunicados', c.idComunicado, 'lecturas']);
  }

  publicar(c: Comunicado): void {
    this.http
      .post(`${environment.gatewayUrl}/api/application/comunicado/${c.idComunicado}/publicar`, {})
      .subscribe({
        next: () => {
          this.snackBar.open('Comunicado publicado', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.loadComunicados();
        },
        error: () =>
          this.snackBar.open('Error al publicar', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
  }

  eliminar(c: Comunicado): void {
    if (!confirm(`¿Eliminar el comunicado "${c.titulo}"?`)) return;
    this.http
      .delete(`${environment.gatewayUrl}/api/application/comunicado/${c.idComunicado}`)
      .subscribe({
        next: () => {
          this.comunicados.update((list) => list.filter((x) => x.idComunicado !== c.idComunicado));
          this.snackBar.open('Comunicado eliminado', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
        error: () =>
          this.snackBar.open('Error al eliminar', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          }),
      });
  }

  getEstadoColor(estado: string): string {
    switch (estado) {
      case 'PUBLICADO':
        return 'primary';
      case 'BORRADOR':
        return '';
      case 'ARCHIVADO':
        return 'warn';
      default:
        return '';
    }
  }

  private loadComunicados(): void {
    this.loading.set(true);
    this.http
      .get<Comunicado[]>(`${environment.gatewayUrl}/api/application/comunicado/list`)
      .subscribe({
        next: (data) => {
          this.comunicados.set(Array.isArray(data) ? data : []);
          this.loading.set(false);
        },
        error: () => {
          this.comunicados.set([]);
          this.loading.set(false);
        },
      });
  }
}
