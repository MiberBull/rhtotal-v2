import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PATH_REPSE } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RepseComplianceService {
  constructor(private http: HttpClient) {}

  getDashboard(period: string) {
    const params = new HttpParams().set('period', period);
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.COMPLIANCE_DASHBOARD}`;
    return this.http.get(URL, { params });
  }

  getBySemaforo(semaforo: string) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.COMPLIANCE_SEMAFORO}/${semaforo}`;
    return this.http.get(URL);
  }

  recalculate(idRepseClient: number, period: string) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.COMPLIANCE_RECALCULATE}/${idRepseClient}/${period}`;
    return this.http.put(URL, null);
  }

  getExpiring(daysAhead: number = 90) {
    const params = new HttpParams().set('daysAhead', String(daysAhead));
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.COMPLIANCE_EXPIRING}`;
    return this.http.get(URL, { params });
  }

  exportExcel(period: string) {
    const params = new HttpParams().set('period', period);
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.COMPLIANCE_EXPORT}`;
    return this.http.get(URL, { params, responseType: 'blob' });
  }

  exportPdf(period: string) {
    const params = new HttpParams().set('period', period);
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.COMPLIANCE_REPORT}`;
    return this.http.get(URL, { params, responseType: 'blob' });
  }
}
