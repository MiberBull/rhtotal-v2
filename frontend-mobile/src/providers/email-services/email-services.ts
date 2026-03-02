import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_RHTOTAL } from '../../environments/environments';
import { EmailGeneric } from '../../models/emailgenric.model';



@Injectable()
export class EmailServices {

  private apiurl = `${API_RHTOTAL.DOMAIN}:${API_RHTOTAL.PORT}`; // Base URL to consume Api

  constructor(public http: HttpClient) {

  }

  /**
   * @function getEmailsToSent
   * @param {any} sendto
   * @return {array} http.response
   **/
  getEmailsToSend(sendto: any) {
    let sendParameters = API_RHTOTAL.GET_EMAIL_SEND;
    sendParameters = sendParameters.replace(':apikey', API_RHTOTAL.KEY); //aplying apikey
    sendParameters = sendParameters.replace(':sendto', sendto); // sendto is the name of mail use of senders

    return this.http.get(`${this.apiurl}${sendParameters}`).timeout(5000).catch(this.handleError); // return the response or error
  }

  /**
   * @function sendEmail
   * @param {EmailGeneric} emailbody
   **/
  sendEmail(emalbody: EmailGeneric){
    let sendMail = API_RHTOTAL.SEND_EMAIL;
    sendMail = sendMail.replace(':apikey', API_RHTOTAL.KEY); // applying apikey

    // method post to send email with parameter emalbody
    this.http.post(`${this.apiurl}${sendMail}`, emalbody).subscribe(data =>{
      console.log('susses ');
    },error =>{
      console.log('error');
    });  
  }

  handleError(error: any) {
    return Observable.throw(error);
  }

}
