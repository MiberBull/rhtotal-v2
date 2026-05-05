import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_PATHS, USER_ENDPOINTS, ApiConfig } from './api-config';
import {
  EmployeeTO,
  EmployeeComplementaryTO,
  EmployeeDomicileTO,
  EmployeeCompensationTO,
  EmployeeContratingTO,
  EmployeeHistoryTO,
  EmployeeDataAssignmentTO,
  EmployeeSocialNetworkTO,
} from '../models/employee.model';
import { NewAccountTO } from '../models/user.model';
import { CountRowResponse } from '../models/common.model';

@Injectable({ providedIn: 'root' })
export class UserApiService {
  private http = inject(HttpClient);
  private baseUrl = '';

  configure(config: ApiConfig): void {
    this.baseUrl = `${config.gatewayUrl}${API_PATHS.USER}`;
  }

  // Account creation
  createUser(account: NewAccountTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.CREATE}`, account);
  }

  confirmUser(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.CONFIRMATION}`, data);
  }

  // Employee listing
  getAllEmployees(page: number, size: number, search?: string): Observable<any> {
    const params: Record<string, string> = {
      page: page.toString(),
      size: size.toString(),
    };
    if (search) params['search'] = search;
    return this.http.get(`${this.baseUrl}/${USER_ENDPOINTS.GET_ALL_EMPLOYEES}`, { params });
  }

  getCountRow(): Observable<CountRowResponse> {
    return this.http.get<CountRowResponse>(`${this.baseUrl}/${USER_ENDPOINTS.GET_COUNT_ROW}`);
  }

  // Employee data by ID
  getEmployeeById(idUser: number): Observable<EmployeeTO> {
    return this.http.get<EmployeeTO>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_EMPLOYEE_BY_ID}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getComplementary(idUser: number): Observable<EmployeeComplementaryTO> {
    return this.http.get<EmployeeComplementaryTO>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_COMPLEMENTARY}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getAddress(idUser: number): Observable<EmployeeDomicileTO> {
    return this.http.get<EmployeeDomicileTO>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_ADDRESS}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getContrating(idUser: number): Observable<EmployeeContratingTO> {
    return this.http.get<EmployeeContratingTO>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_CONTRATING}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getHistory(idUser: number): Observable<EmployeeHistoryTO[]> {
    return this.http.get<EmployeeHistoryTO[]>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_HISTORY}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getCompensation(idUser: number): Observable<EmployeeCompensationTO[]> {
    return this.http.get<EmployeeCompensationTO[]>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_COMPENSATION}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getAsignation(idUser: number): Observable<EmployeeDataAssignmentTO> {
    return this.http.get<EmployeeDataAssignmentTO>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_ASIGNATION}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getSocialNetworks(idUser: number): Observable<EmployeeSocialNetworkTO[]> {
    return this.http.get<EmployeeSocialNetworkTO[]>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_SOCIAL_NETWORK}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  getTabs(idUser: number): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_TABS}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  // Save/Update operations
  saveComplementary(data: EmployeeComplementaryTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.SAVE_COMPLEMENTARY}`, data);
  }

  saveAddress(data: EmployeeDomicileTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.SAVE_ADDRESS}`, data);
  }

  saveContrating(data: EmployeeContratingTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.SAVE_CONTRATING}`, data);
  }

  saveHistory(data: EmployeeHistoryTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.SAVE_HISTORY}`, data);
  }

  saveCompensation(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.SAVE_COMPENSATION}`, data);
  }

  saveAsignation(data: EmployeeDataAssignmentTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.SAVE_ASIGNATION}`, data);
  }

  saveSocialNetwork(data: EmployeeSocialNetworkTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${USER_ENDPOINTS.SAVE_SOCIAL_NETWORK}`, data);
  }

  // Lookups
  getCivilStatus(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${USER_ENDPOINTS.GET_CIVIL_STATUS}`);
  }

  getCities(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${USER_ENDPOINTS.GET_CITY}`);
  }

  getStates(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${USER_ENDPOINTS.GET_STATE}`);
  }

  getClients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${USER_ENDPOINTS.GET_CLIENT}`);
  }

  getProjectsByClient(idClient: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.baseUrl}/${USER_ENDPOINTS.GET_PROJECT_BY_CLIENT}`,
      { params: { idClient: idClient.toString() } }
    );
  }
}
