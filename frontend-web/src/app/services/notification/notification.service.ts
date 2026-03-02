import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { NotificationTO } from '../../models/notification.model';
import { PATH_APPLICATION } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notification:NotificationTO;
  private tabNotification:number;

  private tabExcelNotification:string;

  constructor( private http:HttpClient ) {
    this.tabNotification = 0;
  }

  /**
   * Crea una nueva notificacion
   * @param notification
   */
  newNotification( notification:NotificationTO,json? ) {
    console.log( 'jsonService',json );
    let obj = {
      notificationTO:notification,
      benefitsNotificationsTO:json
    }
    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.SAVE_NOTIFICATION}`;
   console.log('Datas',obj);
    return this.http.post( URL,obj );
  }

  /**
   * Retorna una notificacion buscada por id
   */
  queryNotificationById( notification:string ){
    console.log(`Error => ${notification}`);
    let params = new HttpParams()
                 .set('id', notification);
    let URL = `${PATH_APPLICATION.DOMAIN}/${ PATH_APPLICATION.GET_NOTIFCATION }`
    return this.http.get( URL,{params} );
  }

  getCountItems( tab = 0,filters = {} ){
    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.COUNT_NOTIFICATION}`;
    let params = new HttpParams()
                .set( 'nametab',TABS[tab] )
                .set('title', filters['titulo'] == null ? '' : filters['titulo'].toUpperCase())
                .set('autor', filters['autor'] == null ? '' : filters['autor'].toUpperCase())
                .set('startDate',filters['fechadeenvioinicio'] == null ? '' : filters['fechadeenvioinicio'])
                .set('enddate',filters['fechadeenviofin'] == null ? '' : filters['fechadeenviofin']);
    return this.http.get( URL,{params} );
  }

  /**
   * Coloca un objeto de tipo NotificationTo a
   * notification
   * @param notification
   */
  setNotifitacion( notification:NotificationTO ) {
    this.notification = notification;
  }

  /**
   * Regresa el objeto notification
   */
  getNotification() {
    return this.notification;
  }

  getTabNotification(){
    return this.tabNotification;
  }

  setTabNotification( tabNotification:number ){
    this.tabNotification = tabNotification;
  }

  getNameExcelNotification(){
    return this.tabExcelNotification;
  }

  setNameExcelNotification(tab:string){
    this.tabExcelNotification = tab;
  }

}

export const TABS = {
  0:'A,I',
  1:'E'
}
