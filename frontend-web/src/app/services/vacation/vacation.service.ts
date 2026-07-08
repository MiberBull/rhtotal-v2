import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PATH_HR } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class VacationService {
  constructor(private http: HttpClient) {}

  getAll() {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.VACATION_PENDING}`;
    return this.http.get(URL);
  }

  getBalance(employeeId: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.VACATION_BALANCE}/${employeeId}`;
    return this.http.get(URL);
  }

  approve(id: number, approvedBy: string) {
    const params = new HttpParams().set('approvedBy', approvedBy);
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.VACATION_APPROVE}/${id}/approve`;
    return this.http.put(URL, null, { params });
  }

  reject(id: number, approvedBy: string, reason: string) {
    const params = new HttpParams().set('approvedBy', approvedBy).set('reason', reason);
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.VACATION_REJECT}/${id}/reject`;
    return this.http.put(URL, null, { params });
  }
}
