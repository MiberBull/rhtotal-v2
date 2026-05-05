import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UserApiService, EmployeeTO } from '@dch/shared';
import { environment } from '../../../../environments/environment';

import { PersonalInfoComponent } from './tabs/personal-info/personal-info.component';
import { AddressComponent } from './tabs/address/address.component';
import { SocialNetworksComponent } from './tabs/social-networks/social-networks.component';
import { ContractingComponent } from './tabs/contracting/contracting.component';
import { HistoryComponent } from './tabs/history/history.component';
import { CompensationComponent } from './tabs/compensation/compensation.component';
import { AssignmentComponent } from './tabs/assignment/assignment.component';

@Component({
  selector: 'app-user-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatTabsModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    PersonalInfoComponent,
    AddressComponent,
    SocialNetworksComponent,
    ContractingComponent,
    HistoryComponent,
    CompensationComponent,
    AssignmentComponent,
  ],
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.scss',
})
export class UserDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  loading = signal(true);
  employee = signal<EmployeeTO | null>(null);
  idUser = signal(0);

  constructor() {
    this.userApi.configure({ gatewayUrl: environment.gatewayUrl });
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.router.navigate(['/users']);
      return;
    }
    this.idUser.set(id);
    this.loadEmployee(id);
  }

  private loadEmployee(id: number): void {
    this.loading.set(true);
    this.userApi.getEmployeeById(id).subscribe({
      next: (data) => {
        this.employee.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.snackBar.open('Error al cargar datos del empleado', 'Cerrar', { duration: 3000 });
      },
    });
  }

  getEmployeeName(): string {
    const emp = this.employee();
    if (!emp) return '';
    return `${emp.name ?? ''} ${emp.lastName ?? ''} ${emp.lastMName ?? ''}`.trim();
  }

  goBack(): void {
    this.router.navigate(['/users']);
  }
}
