import { Routes } from '@angular/router';
import { authGuard, guestGuard } from './core/auth/auth.guard';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
  // Public routes
  {
    path: 'login',
    canActivate: [guestGuard],
    loadComponent: () =>
      import('./pages/login/login.component').then((m) => m.LoginComponent),
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
          import(
            './pages/notifications/notification-assignment/notification-assignment.component'
          ).then((m) => m.NotificationAssignmentComponent),
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
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },

  // Fallback
  { path: '**', redirectTo: 'login' },
];
