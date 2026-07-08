import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PATH_ATTENDANCE } from '../../environments/environments';

@Injectable()
export class AttendanceProvider {
  constructor(public http: HttpClient) {}

  checkIn(employeeId: number, latitude: number, longitude: number) {
    const URL = `${PATH_ATTENDANCE.DOMAIN}/${PATH_ATTENDANCE.CHECK_IN}`;
    return this.http
      .post(URL, { employeeId, latitude, longitude })
      .timeout(15000)
      .catch(this.handleError);
  }

  checkOut(employeeId: number, latitude: number, longitude: number) {
    const URL = `${PATH_ATTENDANCE.DOMAIN}/${PATH_ATTENDANCE.CHECK_OUT}`;
    return this.http
      .post(URL, { employeeId, latitude, longitude })
      .timeout(15000)
      .catch(this.handleError);
  }

  getToday(employeeId: number) {
    const URL = `${PATH_ATTENDANCE.DOMAIN}/${PATH_ATTENDANCE.TODAY}/${employeeId}`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  handleError(error: any) {
    return Observable.throw(error);
  }
}
