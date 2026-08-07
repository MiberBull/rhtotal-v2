import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from '../core/auth/auth.service';
import { RoleBadgeComponent } from '../shared/components/role-badge/role-badge.component';

interface NavItem {
  icon: string;
  label: string;
  route: string;
  permission: string;
}

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    RouterOutlet,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatListModule,
    MatMenuModule,
    MatTooltipModule,
    MatDividerModule,
    RoleBadgeComponent,
  ],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss',
})
export class LayoutComponent {
  authService = inject(AuthService);

  collapsed = signal(false);
  currentUser = this.authService.currentUser;
  userRole = this.authService.userRole;

  /** Label corta del tenant activo para mostrar en el toolbar */
  currentTenantLabel = computed(() => {
    const t = this.authService.availableTenants.find((x) => x.id === this.authService.tenantId());
    return t?.label ?? (this.authService.tenantId() || 'DCH');
  });

  /** Solo SUPER_ADMIN y RRHH corporativo DCH pueden cambiar de RS */
  canSwitchTenant = this.authService.canSwitchTenant;

  navItems: NavItem[] = [
    { icon: 'dashboard', label: 'Dashboard', route: '/dashboard', permission: 'dashboard' },
    { icon: 'people', label: 'Usuarios', route: '/users', permission: 'users' },
    { icon: 'badge', label: 'Colaboradores', route: '/colaboradores', permission: 'users' },
    { icon: 'admin_panel_settings', label: 'Roles', route: '/roles', permission: 'roles' },
    { icon: 'business', label: 'Clientes', route: '/clients', permission: 'clients' },
    // HR
    { icon: 'beach_access', label: 'Vacaciones', route: '/vacaciones', permission: 'vacaciones' },
    { icon: 'event_busy', label: 'Incidencias', route: '/incidencias', permission: 'incidencias' },
    { icon: 'support_agent', label: 'Mesa de Ayuda', route: '/tickets', permission: 'tickets' },
    { icon: 'poll', label: 'Encuestas', route: '/encuestas', permission: 'encuestas' },
    { icon: 'rule', label: 'Aprobaciones', route: '/approvals', permission: 'approvals' },
    { icon: 'lock', label: 'Buzón Confidencial', route: '/buzon', permission: 'buzon' },
    // REPSE
    { icon: 'verified', label: 'REPSE', route: '/repse', permission: 'repse' },
    // Attendance
    { icon: 'fingerprint', label: 'Asistencia', route: '/asistencia', permission: 'asistencia' },
    // Onboarding
    { icon: 'person_add', label: 'Onboarding', route: '/onboarding', permission: 'onboarding' },
    // Content
    { icon: 'campaign', label: 'Comunicados', route: '/comunicados', permission: 'comunicados' },
    {
      icon: 'notifications',
      label: 'Notificaciones',
      route: '/notifications',
      permission: 'notifications',
    },
    { icon: 'local_offer', label: 'Beneficios', route: '/discounts', permission: 'discounts' },
    { icon: 'health_and_safety', label: 'Seguros', route: '/insurance', permission: 'insurance' },
    { icon: 'image', label: 'Banners', route: '/banners', permission: 'banners' },
    { icon: 'description', label: 'Documentos', route: '/documents', permission: 'documents' },
    { icon: 'menu_book', label: 'Biblioteca', route: '/biblioteca', permission: 'biblioteca' },
    // Admin
    { icon: 'domain', label: 'Tenants', route: '/tenants', permission: 'tenants' },
  ];

  /** Computed signal that filters nav items based on user permissions */
  visibleNavItems = computed(() =>
    this.navItems.filter((item) => this.authService.hasPermission(item.permission))
  );

  toggleSidebar(): void {
    this.collapsed.update((v) => !v);
  }

  switchTenant(tenantId: string): void {
    this.authService.setTenant(tenantId);
  }

  logout(): void {
    this.authService.logout();
  }

  getUserInitials(): string {
    const user = this.currentUser();
    if (!user?.email) return 'U';
    return user.email.charAt(0).toUpperCase();
  }
}
