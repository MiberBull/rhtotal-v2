import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-banner-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './banner-form.component.html',
  styleUrl: './banner-form.component.scss',
})
export class BannerFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private snackBar = inject(MatSnackBar);

  loading = signal(false);
  saving = signal(false);
  isEdit = signal(false);
  imagePreview = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    idBanner: [0],
    name: ['', [Validators.required]],
    url: [''],
    status: ['A'],
    order: [0],
  });

  private bannerId: number | null = null;
  private selectedFile: File | null = null;

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam && idParam !== 'new') {
      this.isEdit.set(true);
      this.bannerId = Number(idParam);
      this.loadBanner(this.bannerId);
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];

      // Create preview
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview.set(reader.result as string);
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    const formValue = this.form.getRawValue();

    const formData = new FormData();
    if (this.isEdit() && this.bannerId) {
      formData.append('idBanner', this.bannerId.toString());
    }
    formData.append('name', formValue.name);
    formData.append('url', formValue.url);
    formData.append('status', formValue.status);
    formData.append('order', formValue.order.toString());

    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }

    this.http
      .post(`${environment.gatewayUrl}/api/application/banner/saveOrUpdateBanner`, formData)
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.snackBar.open(
            this.isEdit() ? 'Banner actualizado correctamente' : 'Banner creado correctamente',
            'Cerrar',
            { duration: 3000, horizontalPosition: 'end', verticalPosition: 'top' }
          );
          this.router.navigate(['/banners']);
        },
        error: () => {
          this.saving.set(false);
          this.snackBar.open('Error al guardar el banner', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
      });
  }

  onCancel(): void {
    this.router.navigate(['/banners']);
  }

  private loadBanner(id: number): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/banner/getBanner/${id}`)
      .subscribe({
        next: (banner) => {
          this.form.patchValue({
            idBanner: banner.idBanner ?? id,
            name: banner.name ?? '',
            url: banner.url ?? '',
            status: banner.status ?? 'A',
            order: banner.order ?? 0,
          });

          // Set existing image preview
          if (banner.image) {
            const imageUrl = banner.image.startsWith('http')
              ? banner.image
              : `${environment.gatewayUrl}/${banner.image}`;
            this.imagePreview.set(imageUrl);
          }

          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.snackBar.open('Error al cargar el banner', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.router.navigate(['/banners']);
        },
      });
  }
}
