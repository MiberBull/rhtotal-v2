import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TenantInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const tenantId = this.resolveTenantId();
    const cloned = req.clone({ setHeaders: { 'X-Tenant-ID': tenantId } });
    return next.handle(cloned);
  }

  private resolveTenantId(): string {
    const stored = localStorage.getItem('tenantId');
    if (stored) return stored;

    const parts = window.location.hostname.split('.');
    if (parts.length >= 3) return parts[0];

    return 'dchkw';
  }
}
