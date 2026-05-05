import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatListModule } from '@angular/material/list';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-notification-assignment',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCheckboxModule,
    MatListModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './notification-assignment.component.html',
  styleUrl: './notification-assignment.component.scss',
})
export class NotificationAssignmentComponent implements OnInit {
  private http = inject(HttpClient);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private snackBar = inject(MatSnackBar);

  loading = signal(true);
  saving = signal(false);
  searchTerm = signal('');

  allEmployees = signal<any[]>([]);
  assignedEmployeeIds = signal<Set<number>>(new Set());
  selectedAvailableIds = signal<Set<number>>(new Set());
  selectedAssignedIds = signal<Set<number>>(new Set());

  private notificationId: number = 0;

  availableEmployees = computed(() => {
    const assigned = this.assignedEmployeeIds();
    const search = this.searchTerm().toLowerCase();
    return this.allEmployees().filter((emp) => {
      const isNotAssigned = !assigned.has(emp.idEmployee);
      const matchesSearch =
        !search ||
        (emp.name?.toLowerCase().includes(search)) ||
        (emp.lastName?.toLowerCase().includes(search)) ||
        (emp.email?.toLowerCase().includes(search));
      return isNotAssigned && matchesSearch;
    });
  });

  assignedEmployees = computed(() => {
    const assigned = this.assignedEmployeeIds();
    return this.allEmployees().filter((emp) => assigned.has(emp.idEmployee));
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.notificationId = Number(idParam);
      this.loadData();
    }
  }

  onSearchChange(value: string): void {
    this.searchTerm.set(value);
  }

  toggleAvailableSelection(employeeId: number): void {
    this.selectedAvailableIds.update((ids) => {
      const newSet = new Set(ids);
      if (newSet.has(employeeId)) {
        newSet.delete(employeeId);
      } else {
        newSet.add(employeeId);
      }
      return newSet;
    });
  }

  toggleAssignedSelection(employeeId: number): void {
    this.selectedAssignedIds.update((ids) => {
      const newSet = new Set(ids);
      if (newSet.has(employeeId)) {
        newSet.delete(employeeId);
      } else {
        newSet.add(employeeId);
      }
      return newSet;
    });
  }

  isAvailableSelected(employeeId: number): boolean {
    return this.selectedAvailableIds().has(employeeId);
  }

  isAssignedSelected(employeeId: number): boolean {
    return this.selectedAssignedIds().has(employeeId);
  }

  addSelected(): void {
    const selected = this.selectedAvailableIds();
    if (selected.size === 0) return;

    this.assignedEmployeeIds.update((ids) => {
      const newSet = new Set(ids);
      selected.forEach((id) => newSet.add(id));
      return newSet;
    });
    this.selectedAvailableIds.set(new Set());
  }

  removeSelected(): void {
    const selected = this.selectedAssignedIds();
    if (selected.size === 0) return;

    this.assignedEmployeeIds.update((ids) => {
      const newSet = new Set(ids);
      selected.forEach((id) => newSet.delete(id));
      return newSet;
    });
    this.selectedAssignedIds.set(new Set());
  }

  onSave(): void {
    this.saving.set(true);
    const employees = Array.from(this.assignedEmployeeIds());

    this.http
      .post(`${environment.gatewayUrl}/api/application/notificationassignment/saveAssignmentBenefitsNotifications`, {
        idNotification: this.notificationId,
        employees,
      })
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.snackBar.open('Asignacion guardada correctamente', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
          this.router.navigate(['/notifications']);
        },
        error: () => {
          this.saving.set(false);
          this.snackBar.open('Error al guardar la asignacion', 'Cerrar', {
            duration: 4000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
          });
        },
      });
  }

  onCancel(): void {
    this.router.navigate(['/notifications']);
  }

  getEmployeeName(emp: any): string {
    return `${emp.name || ''} ${emp.lastName || ''}`.trim() || 'Sin nombre';
  }

  private loadData(): void {
    this.loading.set(true);

    // Load all employees
    this.http
      .get<any>(`${environment.gatewayUrl}/api/user/employee/getAllEmployees`)
      .subscribe({
        next: (response) => {
          const employees = Array.isArray(response) ? response : response?.content ?? [];
          this.allEmployees.set(employees);
          this.loadAssignments();
        },
        error: () => {
          this.allEmployees.set([]);
          this.loading.set(false);
        },
      });
  }

  private loadAssignments(): void {
    this.http
      .get<any>(
        `${environment.gatewayUrl}/api/application/notificationassignment/getAssignmentsByNotification/${this.notificationId}`
      )
      .subscribe({
        next: (response) => {
          const assignments = Array.isArray(response) ? response : response?.content ?? [];
          const ids = new Set<number>(
            assignments.map((a: any) => a.idEmployee || a.employee?.idEmployee).filter(Boolean)
          );
          this.assignedEmployeeIds.set(ids);
          this.loading.set(false);
        },
        error: () => {
          this.assignedEmployeeIds.set(new Set());
          this.loading.set(false);
        },
      });
  }
}
