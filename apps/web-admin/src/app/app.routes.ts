import { Routes } from '@angular/router';
import { authGuard, guestGuard } from './core/auth/auth.guard';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
  // Public routes
  {
    path: 'login',
    canActivate: [guestGuard],
    loadComponent: () => import('./pages/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'reset-password',
    canActivate: [guestGuard],
    loadComponent: () =>
      import('./pages/reset-password/reset-password.component').then(
        (m) => m.ResetPasswordComponent
      ),
  },
  {
    path: 'reset-confirmation',
    canActivate: [guestGuard],
    loadComponent: () =>
      import('./pages/reset-confirmation/reset-confirmation.component').then(
        (m) => m.ResetConfirmationComponent
      ),
  },
  {
    path: 'new-user',
    canActivate: [guestGuard],
    loadComponent: () =>
      import('./pages/new-user/new-user.component').then((m) => m.NewUserComponent),
  },

  // Protected routes (inside layout shell)
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./pages/dashboard/dashboard.component').then((m) => m.DashboardComponent),
      },
      {
        path: 'users',
        loadComponent: () =>
          import('./pages/users/users-list.component').then((m) => m.UsersListComponent),
      },
      {
        path: 'users/:id',
        loadComponent: () =>
          import('./pages/users/user-detail/user-detail.component').then(
            (m) => m.UserDetailComponent
          ),
      },
      {
        path: 'roles',
        loadComponent: () =>
          import('./pages/roles/roles-list.component').then((m) => m.RolesListComponent),
      },
      {
        path: 'roles/:id',
        loadComponent: () =>
          import('./pages/roles/role-form/role-form.component').then((m) => m.RoleFormComponent),
      },
      {
        path: 'clients',
        loadComponent: () =>
          import('./pages/clients/clients-list.component').then((m) => m.ClientsListComponent),
      },
      {
        path: 'clients/:id',
        loadComponent: () =>
          import('./pages/clients/client-form/client-form.component').then(
            (m) => m.ClientFormComponent
          ),
      },
      {
        path: 'notifications',
        loadComponent: () =>
          import('./pages/notifications/notifications-list.component').then(
            (m) => m.NotificationsListComponent
          ),
      },
      {
        path: 'notifications/new',
        loadComponent: () =>
          import('./pages/notifications/notification-form/notification-form.component').then(
            (m) => m.NotificationFormComponent
          ),
      },
      {
        path: 'notifications/:id',
        loadComponent: () =>
          import('./pages/notifications/notification-form/notification-form.component').then(
            (m) => m.NotificationFormComponent
          ),
      },
      {
        path: 'notifications/:id/assign',
        loadComponent: () =>
          import('./pages/notifications/notification-assignment/notification-assignment.component').then(
            (m) => m.NotificationAssignmentComponent
          ),
      },
      {
        path: 'banners',
        loadComponent: () =>
          import('./pages/banners/banners-list.component').then((m) => m.BannersListComponent),
      },
      {
        path: 'banners/new',
        loadComponent: () =>
          import('./pages/banners/banner-form/banner-form.component').then(
            (m) => m.BannerFormComponent
          ),
      },
      {
        path: 'banners/:id',
        loadComponent: () =>
          import('./pages/banners/banner-form/banner-form.component').then(
            (m) => m.BannerFormComponent
          ),
      },
      {
        path: 'insurance',
        loadComponent: () =>
          import('./pages/insurance/insurance-list.component').then(
            (m) => m.InsuranceListComponent
          ),
      },
      {
        path: 'insurance/new',
        loadComponent: () =>
          import('./pages/insurance/insurance-form/insurance-form.component').then(
            (m) => m.InsuranceFormComponent
          ),
      },
      {
        path: 'insurance/:id',
        loadComponent: () =>
          import('./pages/insurance/insurance-form/insurance-form.component').then(
            (m) => m.InsuranceFormComponent
          ),
      },
      {
        path: 'discounts',
        loadComponent: () =>
          import('./pages/discounts/discounts-list.component').then(
            (m) => m.DiscountsListComponent
          ),
      },
      {
        path: 'discounts/new',
        loadComponent: () =>
          import('./pages/discounts/discount-form/discount-form.component').then(
            (m) => m.DiscountFormComponent
          ),
      },
      {
        path: 'discounts/:id',
        loadComponent: () =>
          import('./pages/discounts/discount-form/discount-form.component').then(
            (m) => m.DiscountFormComponent
          ),
      },
      // HR modules
      {
        path: 'vacaciones',
        loadComponent: () =>
          import('./pages/vacaciones/vacaciones.component').then((m) => m.VacacionesComponent),
      },
      {
        path: 'incidencias',
        loadComponent: () =>
          import('./pages/incidencias/incidencias.component').then((m) => m.IncidenciasComponent),
      },
      {
        path: 'tickets',
        loadComponent: () =>
          import('./pages/tickets/tickets.component').then((m) => m.TicketsComponent),
      },
      {
        path: 'encuestas',
        loadComponent: () =>
          import('./pages/encuestas/encuestas.component').then((m) => m.EncuestasComponent),
      },
      // REPSE
      {
        path: 'repse',
        loadComponent: () => import('./pages/repse/repse.component').then((m) => m.RepseComponent),
      },
      // Attendance
      {
        path: 'asistencia',
        loadComponent: () =>
          import('./pages/asistencia/asistencia.component').then((m) => m.AsistenciaComponent),
      },
      // Onboarding pipeline
      {
        path: 'onboarding',
        loadComponent: () =>
          import('./pages/onboarding/onboarding.component').then((m) => m.OnboardingComponent),
      },
      // Documentos / CFDIs
      {
        path: 'documents',
        loadComponent: () =>
          import('./pages/documents/documents-list.component').then(
            (m) => m.DocumentsListComponent
          ),
      },
      {
        path: 'documents/upload',
        loadComponent: () =>
          import('./pages/documents/document-upload/document-upload.component').then(
            (m) => m.DocumentUploadComponent
          ),
      },
      // Biblioteca de Recursos
      {
        path: 'biblioteca',
        loadComponent: () =>
          import('./pages/biblioteca/biblioteca.component').then((m) => m.BibliotecaComponent),
      },
      // Ficha del Colaborador
      {
        path: 'colaboradores',
        loadComponent: () =>
          import('./pages/colaboradores/colaboradores.component').then(
            (m) => m.ColaboradoresComponent
          ),
      },
      {
        path: 'colaboradores/:id',
        loadComponent: () =>
          import('./pages/colaboradores/detalle/colaborador-detalle.component').then(
            (m) => m.ColaboradorDetalleComponent
          ),
      },
      // Comunicados (M5)
      {
        path: 'comunicados',
        loadComponent: () =>
          import('./pages/comunicados/comunicados-list.component').then(
            (m) => m.ComunicadosListComponent
          ),
      },
      {
        path: 'comunicados/new',
        loadComponent: () =>
          import('./pages/comunicados/comunicado-form/comunicado-form.component').then(
            (m) => m.ComunicadoFormComponent
          ),
      },
      {
        path: 'comunicados/:id/lecturas',
        loadComponent: () =>
          import('./pages/comunicados/comunicado-lecturas/comunicado-lecturas.component').then(
            (m) => m.ComunicadoLecturasComponent
          ),
      },
      {
        path: 'comunicados/:id',
        loadComponent: () =>
          import('./pages/comunicados/comunicado-form/comunicado-form.component').then(
            (m) => m.ComunicadoFormComponent
          ),
      },
      // Multi-tenant admin
      {
        path: 'tenants',
        loadComponent: () =>
          import('./pages/tenants/tenants.component').then((m) => m.TenantsComponent),
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },

  // Fallback
  { path: '**', redirectTo: 'login' },
];
