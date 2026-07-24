import { Injectable, signal, computed, inject } from '@angular/core';
import { Router } from '@angular/router';
import { LoginResponseTO, LoginFlag, UserTO, MenuItem, DchRole } from '@dch/shared';
import { SecurityApiService, CryptoService } from '@dch/shared';
import { environment } from '../../../environments/environment';

const STORAGE_KEY = 'dch_auth';

interface StoredAuth {
  user: UserTO;
  menu: MenuItem[];
  token: string;
  tenantId: string;
}

const ROLE_PERMISSIONS: Record<DchRole, string[]> = {
  DCH_SUPER_ADMIN: ['*'],
  DCH_ADMIN: [
    'dashboard',
    'users',
    'users.write',
    'roles',
    'clients',
    'notifications',
    'banners',
    'insurance',
    'discounts',
    'documents',
    'documents.upload',
    'approvals',
    'approvals.action',
    // HR
    'vacaciones',
    'vacaciones.write',
    'incidencias',
    'tickets',
    'tickets.write',
    'encuestas',
    'encuestas.write',
    'repse',
    'repse.write',
    'asistencia',
    'onboarding',
    'onboarding.write',
    'biblioteca',
    'biblioteca.write',
    // Admin
    'tenants',
    'tenants.write',
  ],
  DCH_RRHH: [
    'dashboard',
    'users',
    'users.write',
    'clients',
    'notifications',
    'documents',
    'documents.upload',
    'approvals',
    'approvals.action',
    // HR
    'vacaciones',
    'vacaciones.write',
    'incidencias',
    'tickets',
    'tickets.write',
    'encuestas',
    'repse',
    'asistencia',
    'onboarding',
    'biblioteca',
    'biblioteca.write',
  ],
  DCH_VIEWER: ['dashboard', 'users', 'clients', 'documents'],
};

const ROUTE_PERMISSION_MAP: Record<string, string> = {
  '/dashboard': 'dashboard',
  '/users': 'users',
  '/roles': 'roles',
  '/clients': 'clients',
  '/notifications': 'notifications',
  '/discounts': 'discounts',
  '/insurance': 'insurance',
  '/banners': 'banners',
  '/documents': 'documents',
  '/approvals': 'approvals',
  '/vacaciones': 'vacaciones',
  '/incidencias': 'incidencias',
  '/tickets': 'tickets',
  '/encuestas': 'encuestas',
  '/repse': 'repse',
  '/asistencia': 'asistencia',
  '/onboarding': 'onboarding',
  '/biblioteca': 'biblioteca',
  '/tenants': 'tenants',
};

@Injectable({ providedIn: 'root' })
export class AuthService {
  private router = inject(Router);
  private securityApi = inject(SecurityApiService);
  private cryptoService = inject(CryptoService);

  // Signals
  private _user = signal<UserTO | null>(null);
  private _menu = signal<MenuItem[]>([]);
  private _token = signal<string>('');
  private _tenantId = signal<string>('');

  readonly isAuthenticated = computed(() => !!this._user());
  readonly currentUser = computed(() => this._user());
  readonly menu = computed(() => this._menu());
  readonly token = computed(() => this._token());
  readonly tenantId = computed(() => this._tenantId());

  /** Derived role signal — defaults to DCH_VIEWER if no role found */
  readonly userRole = computed<DchRole>(() => {
    const user = this._user();
    if (!user) return 'DCH_VIEWER';
    return user.nameRole ?? user.nameRol ?? user.role ?? 'DCH_VIEWER';
  });

  constructor() {
    this.cryptoService.configure(environment.aesSecret);
    this.securityApi.configure({ gatewayUrl: environment.gatewayUrl });
    this.loadFromStorage();
  }

  /**
   * Check if the current user has a specific permission.
   * SUPER_ADMIN with '*' has access to everything.
   */
  hasPermission(permission: string): boolean {
    const role = this.userRole();
    const permissions = ROLE_PERMISSIONS[role] ?? [];
    if (permissions.includes('*')) return true;
    return permissions.includes(permission);
  }

  /**
   * Check if the current user can access a given route path.
   */
  canAccess(route: string): boolean {
    const normalizedRoute = route.startsWith('/') ? route : `/${route}`;
    const permission = ROUTE_PERMISSION_MAP[normalizedRoute];
    if (!permission) return true; // unknown routes are accessible by default
    return this.hasPermission(permission);
  }

  async login(email: string, password: string, tenant: string): Promise<LoginResponseTO> {
    // Establecer tenant ANTES del request — el interceptor lo inyectará en X-Tenant-ID
    this._tenantId.set(tenant);

    const encryptedPassword = this.cryptoService.encrypt(password);
    const request = { user: { email, password: encryptedPassword } };

    return new Promise((resolve, reject) => {
      this.securityApi.loginWeb(request).subscribe({
        next: (response) => {
          if (response.flag === LoginFlag.OK) {
            this._user.set(response.user);
            this._menu.set(response.menu ?? []);
            this._token.set(response.token ?? '');
            // Confirmar tenant con lo que devuelve el servidor
            this._tenantId.set(response.tenantId ?? tenant);
            this.saveToStorage();
          }
          resolve(response);
        },
        error: (err) => {
          this._tenantId.set('');
          reject(err);
        },
      });
    });
  }

  logout(): void {
    this._user.set(null);
    this._menu.set([]);
    this._token.set('');
    this._tenantId.set('');
    localStorage.removeItem(STORAGE_KEY);
    this.router.navigate(['/login']);
  }

  private saveToStorage(): void {
    const data: StoredAuth = {
      user: this._user()!,
      menu: this._menu(),
      token: this._token(),
      tenantId: this._tenantId(),
    };
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
  }

  private loadFromStorage(): void {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return;
    try {
      const data: StoredAuth = JSON.parse(raw);
      this._user.set(data.user);
      this._menu.set(data.menu);
      this._token.set(data.token);
      this._tenantId.set(data.tenantId ?? '');
    } catch {
      localStorage.removeItem(STORAGE_KEY);
    }
  }
}
