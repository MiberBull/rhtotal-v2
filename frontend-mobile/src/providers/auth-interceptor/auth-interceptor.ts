import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { TenantProvider } from '../tenant/tenant';
import { KEYS_STORAGE } from '../../environments/environments';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private tenant: TenantProvider) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const tenantId = this.tenant.getTenantId();
    const headers: { [key: string]: string } = {};

    if (tenantId && tenantId !== 'ALL') {
      headers['X-Tenant-ID'] = tenantId;
    }

    const userRaw = localStorage.getItem(KEYS_STORAGE.USER);
    if (userRaw) {
      try {
        const user = JSON.parse(userRaw);
        const token = user.token || user.dsToken || '';
        if (token) {
          headers['Authorization'] = `Bearer ${token}`;
        }
      } catch (_e) {
        // localStorage corrupted — ignore
      }
    }

    if (Object.keys(headers).length === 0) {
      return next.handle(req);
    }

    const authReq = req.clone({ setHeaders: headers });
    return next.handle(authReq);
  }
}
