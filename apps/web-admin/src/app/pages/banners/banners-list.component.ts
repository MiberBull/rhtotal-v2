import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-banners-list',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule,
  ],
  templateUrl: './banners-list.component.html',
  styleUrl: './banners-list.component.scss',
})
export class BannersListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  banners = signal<any[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    this.loadBanners();
  }

  navigateToNew(): void {
    this.router.navigate(['/banners/new']);
  }

  navigateToEdit(banner: any): void {
    this.router.navigate(['/banners', banner.idBanner]);
  }

  confirmDelete(banner: any, event: Event): void {
    event.stopPropagation();
    if (confirm(`¿Estas seguro de eliminar el banner "${banner.name}"?`)) {
      this.deleteBanner(banner);
    }
  }

  getStatusLabel(status: string): string {
    return status === 'A' ? 'Activo' : 'Inactivo';
  }

  getBannerImageUrl(banner: any): string | null {
    if (banner.image) {
      // If it's a full URL, return as-is; otherwise prepend gateway
      if (banner.image.startsWith('http')) {
        return banner.image;
      }
      return `${environment.gatewayUrl}/${banner.image}`;
    }
    return null;
  }

  private loadBanners(): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/banner/showbanners`)
      .subscribe({
        next: (response) => {
          const data = Array.isArray(response) ? response : response?.content ?? [];
          this.banners.set(data);
          this.loading.set(false);
        },
        error: () => {
          this.banners.set([]);
          this.loading.set(false);
        },
      });
  }

  private deleteBanner(banner: any): void {
    this.http
      .post(`${environment.gatewayUrl}/api/application/banner/saveOrUpdateBanner`, {
        ...banner,
        status: 'I',
      })
      .subscribe({
        next: () => {
          this.banners.update((list) => list.filter((b) => b.idBanner !== banner.idBanner));
          this.snackBar.open('Banner eliminado', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
        error: () => {
          this.snackBar.open('Error al eliminar el banner', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
      });
  }
}
