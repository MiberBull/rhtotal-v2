import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_PATHS, SECURITY_ENDPOINTS, ApiConfig } from './api-config';
import {
  LoginRequestTO,
  LoginResponseTO,
  ResetPasswordRequestTO,
} from '../models/user.model';
import { RoleTO } from '../models/role.model';
import { PagedResponse } from '../models/common.model';

@Injectable({ providedIn: 'root' })
export class SecurityApiService {
  private http = inject(HttpClient);
  private baseUrl = '';

  configure(config: ApiConfig): void {
    this.baseUrl = `${config.gatewayUrl}${API_PATHS.SECURITY}`;
  }

  loginWeb(request: LoginRequestTO): Observable<LoginResponseTO> {
    return this.http.post<LoginResponseTO>(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.LOGIN_WEB}`,
      request
    );
  }

  loginMobile(request: LoginRequestTO): Observable<LoginResponseTO> {
    return this.http.post<LoginResponseTO>(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.LOGIN_MOBILE}`,
      request
    );
  }

  resetPasswordRequest(request: ResetPasswordRequestTO): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.RESET_REQUEST}`,
      request
    );
  }

  resetPasswordConfirmation(data: any): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.RESET_CONFIRMATION}`,
      data
    );
  }

  getAllRoles(page: number, size: number): Observable<PagedResponse<RoleTO>> {
    return this.http.get<PagedResponse<RoleTO>>(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.TABLE_ROLE}`,
      { params: { page: page.toString(), size: size.toString() } }
    );
  }

  getRole(id: number): Observable<RoleTO> {
    return this.http.get<RoleTO>(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.GET_ROLE}`,
      { params: { id: id.toString() } }
    );
  }

  saveOrUpdateRole(role: RoleTO): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.SAVE_ROLE}`,
      role
    );
  }

  getRolesCatalogue(): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.ROLES_CATALOGUE}`
    );
  }

  getRoleCount(): Observable<number> {
    return this.http.get<number>(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.COUNT_ROLE}`
    );
  }

  sendEmail(data: any): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${SECURITY_ENDPOINTS.SEND_EMAIL}`,
      data
    );
  }
}
