import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Injectable()
export class EventsManagerProvider {

  constructor() { }

  private generalNotificationMessage = new Subject<MessageGeneral>();
  private isLoading = new Subject<boolean>();
  private isNewTokenEvent = new Subject<boolean>();
  private toastWithMessage = new Subject<string>();
  private isLogged = new Subject<boolean>();
  private isRequired = new Subject<boolean>();

  getGeneralNotificationMessage() {
    return this.generalNotificationMessage.asObservable();
  }

  setGeneralNotificationMessage(msg: MessageGeneral){
    this.generalNotificationMessage.next(msg);
  }

  getIsLoadingEvent(){
    return this.isLoading.asObservable();
  }

  setIsLoadingEvent(isLoading: boolean){
    this.isLoading.next(isLoading);
  }

  getIsLogged(){
    return this.isLogged.asObservable();
  }

  setIsLogged(isLogged: boolean){
    this.isLogged.next(isLogged);
  }

  getIsNewTokenEvent() {
    return this.isNewTokenEvent.asObservable();
  }

  setIsNewTokenEvent(isNewTokenEvent: boolean) {
    this.isNewTokenEvent.next(isNewTokenEvent);
  }

  getPresentToast() {
    return this.toastWithMessage.asObservable(); 
  }

  setPresentToast( toast:any ) {
    this.toastWithMessage.next(toast);
  }

  getIsRequired() {
    return this.isRequired.asObservable(); 
  }

  setIsRequired( required:any ) {
    this.isRequired.next(required);
  }


}
