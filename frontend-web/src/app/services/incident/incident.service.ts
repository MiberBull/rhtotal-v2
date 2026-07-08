import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PATH_HR } from '../../../environments/environment';
import { IncidentTO } from '../../models/hr.model';

@Injectable({
  providedIn: 'root',
})
export class IncidentService {
  constructor(private http: HttpClient) {}

  getByPeriod(from: string, to: string) {
    const params = new HttpParams().set('from', from).set('to', to);
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.INCIDENT_PERIOD}`;
    return this.http.get(URL, { params });
  }

  save(incident: IncidentTO) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.INCIDENT_SAVE}`;
    return this.http.post(URL, incident);
  }

  exportExcel(from: string, to: string) {
    const params = new HttpParams().set('from', from).set('to', to);
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.INCIDENT_EXCEL}`;
    return this.http.get(URL, { params, responseType: 'blob' });
  }
}
