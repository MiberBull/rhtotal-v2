import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PATH_SECURITY } from '../../../environments/environment';

export interface Tenant {
  id: string;
  name: string;
  domain: string;
  active: boolean;
  creationDate?: string;
}

@Injectable({ providedIn: 'root' })
export class TenantService {
  private BASE = PATH_SECURITY.DOMAIN;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Tenant[]> {
    return this.http.get<Tenant[]>(`${this.BASE}/tenant/all`);
  }

  create(tenant: Partial<Tenant>): Observable<Tenant> {
    return this.http.post<Tenant>(`${this.BASE}/tenant/create`, tenant);
  }

  toggle(id: string): Observable<Tenant> {
    return this.http.put<Tenant>(`${this.BASE}/tenant/${id}/toggle`, {});
  }
}
