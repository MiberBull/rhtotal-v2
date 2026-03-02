import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { parameters, PATH_SECURITY, PATH_USER } from './../../../environments/environment';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class ResetService {

  constructor(private http: HttpClient) {}

  requestResetCode(email: string) : Observable<any> {
    return this.http.post(
   `${PATH_USER.DOMAIN}${PATH_USER.RESET_REQUEST_ENDPOINT}`, 
      {
        user: email
      }
    ).pipe(catchError(this.handleError))
  }

  sendResetPassword(token: string , email: string, password: string, confirmedPassword: string,type:number): Observable<any> {

    let URL = ``;
    if( type == 2 ) {
      URL = `${PATH_USER.DOMAIN}/${PATH_USER.RESET_CONFIRMATION_ENDPOINT}`;
    } else {
      URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.RESET_CONFIRMATION_ENDPOINT}`
    }


    return this.http.post( URL,
      {
        token: token,
        user: email,
        newPassword: password,
        newPasswordConfirmed: confirmedPassword
      }
    ).pipe(catchError(this.handleError))
  }

  /**
     * Obtener Error
     * @param error 
     */
  handleError(error: any) {
    return Observable.throw(error || 'Server error');
  }
}