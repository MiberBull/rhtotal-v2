import { Component, input, output, viewChild } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSortModule, MatSort, Sort } from '@angular/material/sort';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

export interface TableColumn {
  key: string;
  label: string;
  type?: 'text' | 'number' | 'date' | 'currency' | 'boolean';
}

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [
    DatePipe,
    CurrencyPipe,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressBarModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.scss',
})
export class DataTableComponent {
  columns = input.required<TableColumn[]>();
  data = input.required<any[]>();
  totalCount = input<number>(0);
  loading = input<boolean>(false);

  pageChange = output<PageEvent>();
  sortChange = output<Sort>();

  readonly paginator = viewChild<MatPaginator>('paginator');
  readonly sort = viewChild<MatSort>('matSort');

  get displayedColumns(): string[] {
    return this.columns().map((col) => col.key);
  }

  onPageChange(event: PageEvent): void {
    this.pageChange.emit(event);
  }

  onSortChange(event: Sort): void {
    this.sortChange.emit(event);
  }

  exportToExcel(): void {
    const cols = this.columns();
    const rows = this.data();

    const header = cols.map((col) => col.label).join(',');
    const csvRows = rows.map((row) =>
      cols
        .map((col) => {
          const value = row[col.key];
          const escaped =
            value !== null && value !== undefined
              ? String(value).replace(/"/g, '""')
              : '';
          return `"${escaped}"`;
        })
        .join(',')
    );

    const csv = [header, ...csvRows].join('\n');
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `export-${Date.now()}.csv`;
    link.click();
    URL.revokeObjectURL(url);
  }
}
