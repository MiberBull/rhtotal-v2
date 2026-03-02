import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_RHTOTAL } from '../../environments/environments';
import { EmailServices } from '../../providers/email-services/email-services'; // change to new



@Injectable()
export class ServiceValidator {

  private apiurl = `${API_RHTOTAL.DOMAIN}:${API_RHTOTAL.PORT}`; // Base URL to consume Api
  public isActiveService: Boolean;
  public statusDeactivated: string;
  public titleDeactivated: string;
  public messageDeactivated: string;
  public test: any;

  constructor(private http: HttpClient,
    private validatorTemp: EmailServices) {

  }

  /**
   * @function getProperty
   * @param {any} sendto
   * @return {array} http.response
   **/
   getProperty(property: any) {    
     let sendParameters = API_RHTOTAL.GET_PROPERTY;
     sendParameters = sendParameters.replace(':property', property); //aplying apikey

     let url = `${this.apiurl}${sendParameters}`;
     let headersJSON = {
       'Content-Type': 'application/json',
       'apikey': API_RHTOTAL.KEY
     };

     let httpHeaders = new HttpHeaders(headersJSON);


     
     console.log('URL:  ', url);
     console.log('HEADERSJSON:  ', JSON.stringify(httpHeaders));
     console.log('HEADERS:  ', httpHeaders);


     return this.http.get(url,  
     {
       headers: httpHeaders
     }
     ).timeout(5000).catch(this.handleError); // return the response or error
   }

   private handleError(error: any) {
     return Observable.throw(error);
   }

   /**
   * @function checkServiceStatus check if service is available to set service
   **/
   checkServiceStatus(){
     this.validatorTemp.getEmailsToSend('loansActive').subscribe( (res:any) => 
     { 
       this.isActiveService = res[0][0] === '1' ? true : false;
       this.statusDeactivated = res[0][0];

       console.log('loansActive: ', JSON.stringify(res));
     },
     error => {
       console.log('Error Property: ', JSON.stringify(error));
     },
     ()=>{
       console.log('SERVICE? ', this.isActiveService);
       switch (this.statusDeactivated) {
         case "1":
         console.log('God is in heaven all is right with the world!');
         break;
         case "201":
         this.titleDeactivated = 'SERVICIO FUERA DE HORARIO';
         this.messageDeactivated = '<b>Horario de atención:</b> <br>Lunes a Viernes de 9:00hrs a 16:00hrs'
         break;
         case "301":
         this.titleDeactivated = 'SERVICIO EN MANTENIMIENTO';
         this.messageDeactivated = 'Nos encontramos realizando mejoras <br>al sistema,<br>vuelva a intentar en un momento.<br>Gracias por su comprensión.'
         break;
         default:
         this.titleDeactivated = 'SERVICIO NO DISPONIBLE';
         this.messageDeactivated = 'Estamos trabajando para brindarte <br> un mejor servicio.<br>Vuelva a intentar en unos minutos.'
         break;
       }

     }
     ); // get ivalidator

   }

 }