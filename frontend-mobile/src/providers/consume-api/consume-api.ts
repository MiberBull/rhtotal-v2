import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PATH_SECURITY, PATH_APLICATION } from '../../environments/environments';
import { emailTO } from '../../models/email.model';


@Injectable()
export class ConsumeApiProvider {

  private baseUrl = 'https://elite-schedule-app-i2-e1e13.firebaseio.com';

  constructor(public http: HttpClient) {
    
  }

  

  getTournaments() {
    return this.http
              .get(`${this.baseUrl}/tournaments.json`)
              .catch(this.handleError);
  }

  sendEmail(emalbody:emailTO){
    this.http.post(`${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.PARAMETER_EMAIL}`, emalbody).subscribe(data =>{
    console.log('susses ');
    },error =>{
    console.log('error');
    });  
  }

  getDateTimeService() {
    let URL = `${PATH_APLICATION.DOMAIN}${PATH_APLICATION.DATETIME}`;
    return this.http.get(URL).timeout(5000).catch(this.handleError);
  }

  handleError(error: any) {
    return Observable.throw(error);
  }

  /**
  * @function sendCustomEmail Servicio para enviar correos sin utilizar un template predefinido en BD
  * @params emailbody: emailTO modelo de envio para correos
  **/
  sendCustomEmail(emalbody:emailTO){
    this.http.post(`${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.EMAIL_TRUE}`, emalbody).subscribe(data =>{
    console.log('susses ');
    },error =>{
    console.log('error')
    });
  }


}
