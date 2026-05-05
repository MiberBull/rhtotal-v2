import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-discounts-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './discounts-list.component.html',
  styleUrl: './discounts-list.component.scss',
})
export class DiscountsListComponent implements OnInit {
  private router = inject(Router);
  private http = inject(HttpClient);

  discounts = signal<any[]>([]);
  loading = signal(true);
  categories = signal<any[]>([]);
  selectedCategory = signal<number | null>(null);

  filteredDiscounts = computed(() => {
    const category = this.selectedCategory();
    const all = this.discounts();
    if (!category) return all;
    return all.filter(
      (d) =>
        d.idCategory === category ||
        d.category?.idCategory === category
    );
  });

  ngOnInit(): void {
    this.loadDiscounts();
    this.loadCategories();
  }

  onCategoryChange(value: number | null): void {
    this.selectedCategory.set(value);
  }

  navigateToNew(): void {
    this.router.navigate(['/discounts/new']);
  }

  navigateToEdit(discount: any): void {
    this.router.navigate(['/discounts', discount.idDiscount ?? discount.id]);
  }

  getImageUrl(discount: any): string {
    if (discount.imageUrl) return discount.imageUrl;
    if (discount.images?.length) return discount.images[0].url ?? discount.images[0].imageUrl ?? '';
    return '';
  }

  getCategoryName(discount: any): string {
    return discount.category?.name ?? discount.categoryName ?? '—';
  }

  private loadDiscounts(): void {
    this.loading.set(true);
    this.http
      .get<any[]>(`${environment.gatewayUrl}/api/application/discount/getAllDiscounts`)
      .subscribe({
        next: (data) => {
          this.discounts.set(data ?? []);
          this.loading.set(false);
        },
        error: () => {
          this.discounts.set([]);
          this.loading.set(false);
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
