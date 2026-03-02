import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PATH_NOTIFICATION } from '../../environments/environments';
import { Observable, Subject } from 'rxjs';
import { PushObject, PushOptions, Push } from '@ionic-native/push';

/*
  Generated class for the NotificationsProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class NotificationsProvider {

  notificationToken: string;
  pushObject: PushObject
  numberNotifications = new Subject<number>();

  constructor(public http: HttpClient, private push: Push) {
    console.log('Hello NotificationsProvider Provider');
  }

  setNumberNotifications(num: number) {
    this.numberNotifications.next(num);
  }

  getNumberNotifications() {
    return this.numberNotifications.asObservable();
  }

  configToken() {
    const options: PushOptions = {
      android: {
        senderID: '300801911333'
      },
      ios: {
          alert: 'true',
          badge: true,
          sound: 'false'
      }
    };
    
    this.pushObject = this.push.init(options);
  }

  onNotification() {
    return this.pushObject.on('notification');
  }

  onRegistration() {
    return this.pushObject.on('registration');
  }

  onError() {
    return this.pushObject.on('error');
  }

  setNotificationToken(token) {
    this.notificationToken = token;
  }

  cancelNotificationRegister() {
    this.pushObject.unregister();
  }

  /**
   * Registra el token para notificaciones del un usuario
   * @param error 
   */
  setUserToken(userId) {
    let URL = `${PATH_NOTIFICATION.DOMAIN}/${PATH_NOTIFICATION.SET_USER_TOKEN}`;
    return this.http.post( URL, {
      idUser: userId,
      token: this.notificationToken
    } ).catch(this.handleError);
  }

  /**
   * Inactiva el registro el token para notificaciones del un usuario
   * @param error 
   */
  invalidUserToken(userId) {
    let URL = `${PATH_NOTIFICATION.DOMAIN}/${PATH_NOTIFICATION.INACTIVE_USER_TOKEN}`;
    return this.http.post( URL, {
      idUser: userId,
      token: this.notificationToken
    } ).catch(this.handleError);
  }

  /**
   * Se obtienen las ultimas notificaciones
   * @param error 
   */
  getNotifications(idUser:number) {
    let URL = `${PATH_NOTIFICATION.DOMAIN}/${PATH_NOTIFICATION.LAST_NOTIFICATIONS}`;
    let params = new HttpParams()
    .set('idUser',idUser.toString());
    return this.http.get(URL,{params}).catch(this.handleError);
  }

  /**
   * Cachar los errores
   * @param error 
   */
  handleError(error: any) {
    console.log(error);
    return Observable.throw(error);
  }
}
