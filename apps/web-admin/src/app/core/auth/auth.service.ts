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
  loginTenant: string;
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
    'buzon',
    'buzon.write',
    'comunicados',
    'comunicados.write',
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
    'buzon',
    'buzon.write',
    'comunicados',
    'comunicados.write',
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
  '/colaboradores': 'users',
  '/tenants': 'tenants',
  '/buzon': 'buzon',
  '/comunicados': 'comunicados',
};

@Injectable({ providedIn: 'root' })
export class AuthService {
  private router = inject(Router);
  private securityApi = inject(SecurityApiService);
  private cryptoService = inject(CryptoService);

  /** Tenants corporativos DCH — sus usuarios pueden cambiar entre RS */
  private readonly CORPORATE_TENANTS = ['dchkw', 'demo-corp'];

  /** Razones Sociales disponibles del grupo DCH */
  readonly availableTenants = [
    { id: 'ALL', label: 'Todas las RS', description: 'Vista consolidada DCH' },
    { id: 'aeisa', label: 'AEISA', description: 'Administración y Consultoría' },
    { id: 'rga', label: 'RGA', description: 'Operaciones de Almacén' },
    { id: 'staffing', label: 'Staffing', description: 'Mantenimiento Industrial' },
  ];

  // Signals
  private _user = signal<UserTO | null>(null);
  private _menu = signal<MenuItem[]>([]);
  private _token = signal<string>('');
  private _tenantId = signal<string>('');
  /** Tenant con el que autenticó el usuario — no cambia durante la sesión */
  private _loginTenant = signal<string>('');

  readonly isAuthenticated = computed(() => !!this._user());
  readonly currentUser = computed(() => this._user());
  readonly menu = computed(() => this._menu());
  readonly token = computed(() => this._token());
  readonly tenantId = computed(() => this._tenantId());

  /** True si el usuario pertenece al corporativo DCH (no a una RS específica) */
  readonly isDchCorporate = computed(() => this.CORPORATE_TENANTS.includes(this._loginTenant()));

  /**
   * Puede cambiar de RS sin re-login:
   * - DCH_SUPER_ADMIN siempre
   * - DCH_RRHH solo si es usuario corporativo DCH
   */
  readonly canSwitchTenant = computed(() => {
    const role = this.userRole();
    if (role === 'DCH_SUPER_ADMIN') return true;
    if (role === 'DCH_RRHH' && this.isDchCorporate()) return true;
    return false;
  });

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
    this._loginTenant.set(tenant);

    const encryptedPassword = this.cryptoService.encrypt(password);
    const request = { user: { email, password: encryptedPassword } };

    return new Promise((resolve, reject) => {
      this.securityApi.loginWeb(request).subscribe({
        next: (response) => {
          if (response.flag === LoginFlag.OK) {
            this._user.set(response.user);
            this._menu.set(response.menu ?? []);
            this._token.set(response.token ?? '');
            const confirmedTenant = response.tenantId ?? tenant;
            this._tenantId.set(confirmedTenant);
            this._loginTenant.set(confirmedTenant);
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

  /** Cambia la Razón Social activa sin re-login (solo para DCH_ADMIN / DCH_SUPER_ADMIN) */
  setTenant(tenantId: string): void {
    this._tenantId.set(tenantId);
    this.saveToStorage();
  }

  logout(): void {
    this._user.set(null);
    this._menu.set([]);
    this._token.set('');
    this._tenantId.set('');
    this._loginTenant.set('');
    localStorage.removeItem(STORAGE_KEY);
    this.router.navigate(['/login']);
  }

  private saveToStorage(): void {
    const data: StoredAuth = {
      user: this._user()!,
      menu: this._menu(),
      token: this._token(),
      tenantId: this._tenantId(),
      loginTenant: this._loginTenant(),
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
      this._loginTenant.set(data.loginTenant ?? data.tenantId ?? '');
    } catch {
      localStorage.removeItem(STORAGE_KEY);
    }
  }
}
