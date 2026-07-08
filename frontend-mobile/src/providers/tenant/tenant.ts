import { Injectable } from '@angular/core';

@Injectable()
export class TenantProvider {
  getTenantId(): string {
    return localStorage.getItem('tenantId') || 'dchkw';
  }

  getTenantHeaders(): { [key: string]: string } {
    return { 'X-Tenant-ID': this.getTenantId() };
  }
}
