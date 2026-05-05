import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_PATHS, APPLICATION_ENDPOINTS, ApiConfig } from './api-config';
import {
  BannerTO,
  NotificationTO,
  DiscountTO,
  InsuranceTO,
  InsuranceEventualityTO,
  InsurancePlanCoverageTO,
  ClientTO,
  CompanyInformationTO,
} from '../models/application.model';

@Injectable({ providedIn: 'root' })
export class ApplicationApiService {
  private http = inject(HttpClient);
  private baseUrl = '';

  configure(config: ApiConfig): void {
    this.baseUrl = `${config.gatewayUrl}${API_PATHS.APPLICATION}`;
  }

  // Banners
  getPagedBanners(page: number, size: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${APPLICATION_ENDPOINTS.TABLE_BANNER}`, {
      params: { page: page.toString(), size: size.toString() },
    });
  }

  getBanner(id: number): Observable<BannerTO> {
    return this.http.get<BannerTO>(`${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_BANNER}`, {
      params: { id: id.toString() },
    });
  }

  saveOrUpdateBanner(banner: BannerTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${APPLICATION_ENDPOINTS.SAVE_BANNER}`, banner);
  }

  showBanners(): Observable<BannerTO[]> {
    return this.http.get<BannerTO[]>(`${this.baseUrl}/${APPLICATION_ENDPOINTS.SHOW_BANNERS}`);
  }

  // Notifications
  getPagedNotifications(page: number, size: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${APPLICATION_ENDPOINTS.TABLE_NOTIFICATION}`, {
      params: { page: page.toString(), size: size.toString() },
    });
  }

  getNotification(id: number): Observable<NotificationTO> {
    return this.http.get<NotificationTO>(
      `${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_NOTIFICATION}`,
      { params: { id: id.toString() } }
    );
  }

  saveOrUpdateNotification(notification: NotificationTO): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${APPLICATION_ENDPOINTS.SAVE_NOTIFICATION}`,
      notification
    );
  }

  setUserToken(data: { idUser: number; token: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/${APPLICATION_ENDPOINTS.SET_USER_TOKEN}`, data);
  }

  inactiveUserToken(data: { idUser: number }): Observable<any> {
    return this.http.post(`${this.baseUrl}/${APPLICATION_ENDPOINTS.INACTIVE_USER_TOKEN}`, data);
  }

  getNotificationsByUser(idUser: number): Observable<NotificationTO[]> {
    return this.http.get<NotificationTO[]>(
      `${this.baseUrl}/${APPLICATION_ENDPOINTS.NOTIFICATION_USER}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  // Notification assignments
  getAssignmentTree(): Observable<any> {
    return this.http.get(`${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_ASSIGNMENT_TREE}`);
  }

  saveAssignment(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${APPLICATION_ENDPOINTS.SAVE_ASSIGNMENT}`, data);
  }

  // Discounts
  getPagedDiscounts(page: number, size: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${APPLICATION_ENDPOINTS.TABLE_DISCOUNT}`, {
      params: { page: page.toString(), size: size.toString() },
    });
  }

  saveOrUpdateDiscount(discount: DiscountTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${APPLICATION_ENDPOINTS.SAVE_DISCOUNT}`, discount);
  }

  getCategoryByUser(idUser: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_CATEGORY_BY_USER}`, {
      params: { idUser: idUser.toString() },
    });
  }

  getDiscountImages(idUser: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_DISCOUNT_IMAGES}`, {
      params: { idUser: idUser.toString() },
    });
  }

  // Insurance
  getAllInsurance(): Observable<InsuranceTO[]> {
    return this.http.get<InsuranceTO[]>(`${this.baseUrl}/${APPLICATION_ENDPOINTS.TABLE_INSURANCE}`);
  }

  getOneInsurance(id: number): Observable<InsuranceTO> {
    return this.http.get<InsuranceTO>(`${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_ONE_INSURANCE}`, {
      params: { id: id.toString() },
    });
  }

  saveUpdateInsurance(insurance: InsuranceTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${APPLICATION_ENDPOINTS.SAVE_INSURANCE}`, insurance);
  }

  getAllEventualities(idInsurance: number): Observable<InsuranceEventualityTO[]> {
    return this.http.get<InsuranceEventualityTO[]>(
      `${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_ALL_EVENTUALY}`,
      { params: { idInsurance: idInsurance.toString() } }
    );
  }

  getAllCoverage(idInsurance: number): Observable<InsurancePlanCoverageTO[]> {
    return this.http.get<InsurancePlanCoverageTO[]>(
      `${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_ALL_COVERAGE}`,
      { params: { idInsurance: idInsurance.toString() } }
    );
  }

  getInsuranceByUser(idUser: number): Observable<InsuranceTO[]> {
    return this.http.get<InsuranceTO[]>(
      `${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_INSURANCE_USER}`,
      { params: { idUser: idUser.toString() } }
    );
  }

  // Clients
  getAllClients(): Observable<ClientTO[]> {
    return this.http.get<ClientTO[]>(`${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_ALL_CLIENTS}`);
  }

  saveOrUpdateClient(client: ClientTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/${APPLICATION_ENDPOINTS.SAVE_CLIENT}`, client);
  }

  getClient(id: number): Observable<ClientTO> {
    return this.http.get<ClientTO>(`${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_CLIENT}`, {
      params: { id: id.toString() },
    });
  }

  // Company information
  getCompanyInformation(): Observable<CompanyInformationTO> {
    return this.http.get<CompanyInformationTO>(
      `${this.baseUrl}/${APPLICATION_ENDPOINTS.GET_COMPANY_INFO}`
    );
  }
}
