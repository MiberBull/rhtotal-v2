import { Injectable } from '@angular/core';

@Injectable()
export class TenantProvider {
  getTenantId(): string {
    return localStorage.getItem('tenantId') || 'demo-corp';
  }

  persistTenantId(tenantId: string): void {
    localStorage.setItem('tenantId', tenantId || 'demo-corp');
  }

  getTenantHeaders(): { [key: string]: string } {
    return { 'X-Tenant-ID': this.getTenantId() };
  }
}
