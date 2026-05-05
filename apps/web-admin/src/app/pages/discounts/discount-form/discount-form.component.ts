import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';

import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-discount-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    MatCardModule,
    MatDatepickerModule,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './discount-form.component.html',
  styleUrl: './discount-form.component.scss',
})
export class DiscountFormComponent implements OnInit {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);

  loading = signal(false);
  saving = signal(false);
  isEdit = signal(false);
  categories = signal<any[]>([]);
  subcategories = signal<any[]>([]);
  images = signal<any[]>([]);
  imagePreview = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    name: ['', Validators.required],
    description: [''],
    discountPercentage: [0],
    provider: [''],
    phone: [''],
    email: ['', Validators.email],
    website: [''],
    address: [''],
    idCategory: [null as number | null],
    idSubcategory: [null as number | null],
    validFrom: [null as Date | null],
    validUntil: [null as Date | null],
    status: ['A'],
  });

  private discountId: number | null = null;
  private selectedFile: File | null = null;

  ngOnInit(): void {
    this.loadCategories();

    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'new') {
      this.isEdit.set(true);
      this.discountId = Number(id);
      this.loadDiscount(this.discountId);
    }
  }

  onCategoryChange(categoryId: number): void {
    const category = this.categories().find(
      (c) => (c.idCategory ?? c.id) === categoryId
    );
    this.subcategories.set(category?.subcategories ?? []);
    this.form.patchValue({ idSubcategory: null });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.length) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview.set(reader.result as string);
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    if (event.dataTransfer?.files?.length) {
      this.selectedFile = event.dataTransfer.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview.set(reader.result as string);
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  removeImage(index: number): void {
    this.images.update((imgs) => imgs.filter((_, i) => i !== index));
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.snackBar.open('Por favor completa los campos requeridos.', 'Cerrar', {
        duration: 4000,
      });
      return;
    }

    this.saving.set(true);
    const formValue = this.form.getRawValue();

    const payload: any = {
      name: formValue.name,
      description: formValue.description,
      discountPercentage: formValue.discountPercentage,
      provider: formValue.provider,
      phone: formValue.phone,
      email: formValue.email,
      website: formValue.website,
      address: formValue.address,
      idCategory: formValue.idCategory,
      idSubcategory: formValue.idSubcategory,
      validFrom: formValue.validFrom,
      validUntil: formValue.validUntil,
      status: formValue.status,
      images: this.images(),
    };

    if (this.isEdit() && this.discountId) {
      payload.idDiscount = this.discountId;
    }

    this.http
      .post(`${environment.gatewayUrl}/api/application/discount/saveOrUpdateDiscount`, payload)
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.snackBar.open(
            this.isEdit()
              ? 'Beneficio actualizado correctamente.'
              : 'Beneficio creado correctamente.',
            'Cerrar',
            { duration: 3000 }
          );
          this.router.navigate(['/discounts']);
        },
        error: () => {
          this.saving.set(false);
          this.snackBar.open('Error al guardar el beneficio. Intenta de nuevo.', 'Cerrar', {
            duration: 4000,
          });
        },
      });
  }

  onCancel(): void {
    this.router.navigate(['/discounts']);
  }

  private loadDiscount(id: number): void {
    this.loading.set(true);
    this.http
      .get<any>(`${environment.gatewayUrl}/api/application/discount/getDiscount/${id}`)
      .subscribe({
        next: (discount) => {
          this.form.patchValue({
            name: discount.name ?? '',
            description: discount.description ?? '',
            discountPercentage: discount.discountPercentage ?? 0,
            provider: discount.provider ?? '',
            phone: discount.phone ?? '',
            email: discount.email ?? '',
            website: discount.website ?? '',
            address: discount.address ?? '',
            idCategory: discount.idCategory ?? discount.category?.idCategory ?? null,
            idSubcategory: discount.idSubcategory ?? discount.subcategory?.idSubcategory ?? null,
            validFrom: discount.validFrom ? new Date(discount.validFrom) : null,
            validUntil: discount.validUntil ? new Date(discount.validUntil) : null,
            status: discount.status ?? 'A',
          });

          if (discount.images?.length) {
            this.images.set(discount.images);
          }

          // Load subcategories for selected category
          if (discount.idCategory ?? discount.category?.idCategory) {
            this.onCategoryChange(discount.idCategory ?? discount.category?.idCategory);
          }

          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.snackBar.open('Error al cargar el beneficio.', 'Cerrar', { duration: 4000 });
          this.router.navigate(['/discounts']);
        },
      });
  }

  private loadCategories(): void {
    this.http
      .get<any[]>(`${environment.gatewayUrl}/api/application/discount/getAllCategories`)
      .subscribe({
        next: (data) => {
          this.categories.set(data ?? []);
        },
        error: () => {
          this.categories.set([]);
        },
      });
  }
}
