import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PATH_DOCUMENT } from '../../../environments/environment';
import { CfdiTO } from '../../models/document.model';

@Injectable({
  providedIn: 'root',
})
export class CfdiService {
  constructor(private http: HttpClient) {}

  importCfdi(cfdi: CfdiTO) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.CFDI_IMPORT}`;
    return this.http.post(URL, cfdi);
  }

  getByEmployee(employeeId: number) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.CFDI_BY_EMPLOYEE}/${employeeId}`;
    return this.http.get(URL);
  }

  getByEmployeeAndPeriod(employeeId: number, period: string) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.CFDI_BY_EMPLOYEE}/${employeeId}/period/${period}`;
    return this.http.get(URL);
  }

  getByPeriod(period: string) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.CFDI_BY_PERIOD}/${period}`;
    return this.http.get(URL);
  }
}
