import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { RhProvider } from '../../providers/rh/rh';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
//import { DialogsProvider } from '../../providers/dialogs/dialogs';
import { ActualPositionPage } from '../actual-position/actual-position';
import { PaymentDetailsPage } from '../payment-details/payment-details';
import { Conversions } from '../../util/conversions';
//import { DatesApplication } from '../../util/dates';
import { MSG_DIALOG } from '../../environments/environments';
import { CfdiPage } from '../cfdi/cfdi';
import { ConsumeApiProvider } from '../../providers/consume-api/consume-api';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Component({
  selector: 'page-rh',
  templateUrl: 'rh.html',
})
export class RhPage {

  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams,
    private rh_provider: RhProvider,
    //private dialog_provider: DialogsProvider,
    private events_provider: EventsManagerProvider,
    private api_consume: ConsumeApiProvider) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad RhPage');
  }

  goActualPosition() {
    this.events_provider.setIsLoadingEvent(true);
    this.rh_provider
        .getInfoActualPosition()
        .subscribe( (data:any) => {
          data.dateOfAdmission = data.dateOfAdmission.replace(/%2F/g,'/');
          data.paymentPeriod = Conversions.UpperFirstLetter(data.paymentPeriod);
          data.position = Conversions.UpperFirstLetter(data.position);
          this.navCtrl.push( ActualPositionPage,{data});
        },error => {
          console.log( JSON.stringify(error) );
          this.events_provider.setIsLoadingEvent(false);
          this.showAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
        }, () => this.events_provider.setIsLoadingEvent(false));
  }

  goPaymentDetails() {

    this.events_provider.setIsLoadingEvent(true);
    this.api_consume
          .getDateTimeService()
          .subscribe( (resp:any) => {
            this.events_provider.setIsLoadingEvent(false);
            this.navCtrl.push( PaymentDetailsPage,resp );
          }, error => {
            console.log( JSON.stringify(error) );
            this.events_provider.setIsLoadingEvent(false);
            this.showAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
          });

  }

  goCfdi() {

    this.events_provider.setIsLoadingEvent(true);
    this.api_consume
          .getDateTimeService()
          .subscribe( (resp:any) => {
            this.events_provider.setIsLoadingEvent(false);
            this.navCtrl.push( CfdiPage,resp );
          }, error => {
            console.log( JSON.stringify(error) );
            this.events_provider.setIsLoadingEvent(false);
            this.showAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
          });

    
  }

  showAlert(title:string,msg:string) {
    let objMessage:MessageGeneral = { msg,title };
    this.events_provider.setGeneralNotificationMessage(objMessage);
  }

  backRHGeneral(){
    this.navCtrl.pop();
  }
}
