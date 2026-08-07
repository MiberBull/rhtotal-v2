import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthGuard, GuestGuard } from './core/guards/auth.guard';

const routes: Routes = [
  // Public routes
  {
    path: 'onboarding',
    loadChildren: () =>
      import('./pages/onboarding/onboarding.module').then((m) => m.OnboardingPageModule),
    canActivate: [GuestGuard],
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then((m) => m.LoginPageModule),
    canActivate: [GuestGuard],
  },
  {
    path: 'create-account',
    loadChildren: () =>
      import('./pages/create-account/create-account.module').then((m) => m.CreateAccountPageModule),
    canActivate: [GuestGuard],
  },
  {
    path: 'restore-password',
    loadChildren: () =>
      import('./pages/restore-password/restore-password.module').then(
        (m) => m.RestorePasswordPageModule
      ),
    canActivate: [GuestGuard],
  },

  // Protected routes
  {
    path: 'home',
    loadChildren: () => import('./pages/home/home.module').then((m) => m.HomePageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'my-data',
    loadChildren: () => import('./pages/my-data/my-data.module').then((m) => m.MyDataPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'my-data/personal-info',
    loadChildren: () =>
      import('./pages/my-data/personal-info/personal-info.module').then(
        (m) => m.PersonalInfoPageModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'my-data/address',
    loadChildren: () =>
      import('./pages/my-data/address/address.module').then((m) => m.AddressPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'my-data/social-networks',
    loadChildren: () =>
      import('./pages/my-data/social-networks/social-networks.module').then(
        (m) => m.SocialNetworksPageModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'credential',
    loadChildren: () =>
      import('./pages/credential/credential.module').then((m) => m.CredentialPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'my-cv',
    loadChildren: () => import('./pages/my-cv/my-cv.module').then((m) => m.MyCvPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'benefits',
    loadChildren: () =>
      import('./pages/benefits/benefits.module').then((m) => m.BenefitsPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'benefits/detail/:id',
    loadChildren: () =>
      import('./pages/benefits/detail/discount-detail.module').then(
        (m) => m.DiscountDetailPageModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'benefits/:categoryId',
    loadChildren: () =>
      import('./pages/benefits/category/benefits-category.module').then(
        (m) => m.BenefitsCategoryPageModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'insurance',
    loadChildren: () =>
      import('./pages/insurance/insurance-list.module').then((m) => m.InsuranceListPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'insurance/detail/:id',
    loadChildren: () =>
      import('./pages/insurance/detail/insurance-detail.module').then(
        (m) => m.InsuranceDetailPageModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'insurance/quote',
    loadChildren: () =>
      import('./pages/insurance/quote/quote-safe.module').then((m) => m.QuoteSafePageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'notifications',
    loadChildren: () =>
      import('./pages/notifications/notifications.module').then((m) => m.NotificationsPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'help',
    loadChildren: () => import('./pages/help/help.module').then((m) => m.HelpPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'about',
    loadChildren: () => import('./pages/about/about.module').then((m) => m.AboutPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'documents',
    loadChildren: () =>
      import('./pages/documents/documents.module').then((m) => m.DocumentsPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'comunicados',
    loadChildren: () =>
      import('./pages/comunicados/comunicados.module').then((m) => m.ComunicadosPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'biblioteca',
    loadChildren: () =>
      import('./pages/biblioteca/biblioteca.module').then((m) => m.BibliotecaPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'vacaciones',
    loadChildren: () =>
      import('./pages/vacaciones/vacaciones.module').then((m) => m.VacacionesPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'tickets',
    loadChildren: () => import('./pages/tickets/tickets.module').then((m) => m.TicketsPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'encuestas',
    loadChildren: () =>
      import('./pages/encuestas/encuestas.module').then((m) => m.EncuestasPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'repse',
    loadChildren: () => import('./pages/repse/repse.module').then((m) => m.RepsePageModule),
    canActivate: [AuthGuard],
  },

  // Default
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
