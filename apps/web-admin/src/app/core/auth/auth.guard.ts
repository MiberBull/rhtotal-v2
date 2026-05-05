import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return true;
  }

  return router.createUrlTree(['/login']);
};

export const guestGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isAuthenticated()) {
    return true;
  }

  return router.createUrlTree(['/dashboard']);
};

/**
 * Factory function that creates a route guard checking for a specific permission.
 * If the user is not authenticated, redirects to /login.
 * If the user lacks the required permission, redirects to /dashboard.
 */
export function roleGuard(requiredPermission: string): CanActivateFn {
  return () => {
    const auth = inject(AuthService);
    if (!auth.isAuthenticated()) return inject(Router).createUrlTree(['/login']);
    if (!auth.hasPermission(requiredPermission)) return inject(Router).createUrlTree(['/dashboard']);
    return true;
  };
}
