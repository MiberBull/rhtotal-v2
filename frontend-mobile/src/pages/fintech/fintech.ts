import { Component } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import { VeloCashPage } from '../velo-cash/velo-cash';
import { PaysheetProvider } from '../../providers/paysheet/paysheet';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
//import { DialogsProvider } from '../../providers/dialogs/dialogs';
import { MyAdvancePage } from '../my-advance/my-advance';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { NetworkService } from '../../providers/network-service/network.service'
import { ServiceValidator } from '../../providers/service-validator/service-validator'

@Component({
  selector: 'page-fintech',
  templateUrl: 'fintech.html',
})
export class FintechPage {

  public isConnected: Boolean;


  constructor(
    public navCtrl: NavController, 
    private eventsManager: EventsManagerProvider,
    public navParams: NavParams,
    //private _dialog:DialogsProvider,
    public _storageProvider: StorageProvider,
    private _paysheetProvider: PaysheetProvider,
    private events_provider: EventsManagerProvider,
    private alertCtrl: AlertController,
    private network: NetworkService,
    private serviceValidator: ServiceValidator) {  
    this.serviceValidator.getProperty('loansActive').subscribe( (res:any)  => {
      console.log('this ',  JSON.stringify(res));
      this.serviceValidator.test = res[0][0] === 1 ? true : false;
    },
    error => {
      console.log('Error Property: ', JSON.stringify(error));
    },
    ()=>{
      console.log('SERVICE? ', this.serviceValidator.test);
    });

    this.serviceValidator.checkServiceStatus();

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad FintechPage');
    this.serviceValidator.checkServiceStatus();
  }


  // Star service on enter page to constantly detect the status of network
  ionViewDidEnter(){
    this.network.startNetworkService();
    this.isConnected = this.network.getIsConnected();

    if(!this.serviceValidator.isActiveService){
      this.showALert(this.serviceValidator.titleDeactivated, this.serviceValidator.messageDeactivated);
    }
  }

  // Stop service when leave the page
  ionViewWillLeave(){
    this.network.stopNetworkService();

    if(!this.serviceValidator.isActiveService){
      this.showALert(this.serviceValidator.titleDeactivated, this.serviceValidator.messageDeactivated);
    }
  }



  goVelocash() {
    if(!this.network.getIsConnected()){
      this.showALert('RH Total', 'Revisa tu conexión y vuelve a intentar.');
      console.log(this.isConnected, 'Connected.');
    } else {
      this.eventsManager.setIsLoadingEvent(true);
      this._paysheetProvider.velocashService()
      .subscribe( (resp:any)  => {
        if( resp.access ){
          this.navCtrl.push(VeloCashPage,{data:resp} );
        }else{
          this.showALert(MSG_DIALOG.ERROR_TITLE,`${resp.message}.`);
        }
      },error => {
        let msg:MessageGeneral = {
          msg:MSG_DIALOG.ERROR_SERVICE,
          title:MSG_DIALOG.ERROR_TITLE
        };
        this.events_provider.setGeneralNotificationMessage(msg);
        this.eventsManager.setIsLoadingEvent(false)
      }, () => this.eventsManager.setIsLoadingEvent(false));
    }

  }

  getIdUser() {
    this._storageProvider.getItem( KEYS_STORAGE.USER );
  }

  goMyAdvance() {
    if(!this.network.getIsConnected()){
      this.showALert('RH Total', 'Revisa tu conexión y vuelve a intentar.');
      console.log(this.isConnected, 'Connected.');
    } else {
      this.eventsManager.setIsLoadingEvent(true);
      this._paysheetProvider.myAdvanceService()
      .subscribe( (resp:any)  => {
        if( resp.access ){
          this.navCtrl.push(MyAdvancePage,{data:resp} );
        }else{
          this.showALert(MSG_DIALOG.ERROR_TITLE,`${resp.message}.`);
        }
      },error => {
        let msg:MessageGeneral = {
          msg:MSG_DIALOG.ERROR_SERVICE,
          title:MSG_DIALOG.ERROR_TITLE
        };
        this.events_provider.setGeneralNotificationMessage(msg);
        this.eventsManager.setIsLoadingEvent(false)
      }, () => this.eventsManager.setIsLoadingEvent(false));
    }
  }

  showALert(title,subTitle) {
    let alert = this.alertCtrl.create({
      title,
      subTitle,
      enableBackdropDismiss : false, // cant close
      buttons:[
      {
        text: 'ACEPTAR',
        handler: () => {
          alert.dismiss();
          if(!this.serviceValidator.isActiveService){
            this.backFintechGeneral();
          }
        }
      }],
      cssClass:'alertonfirmCustomCss'
    });
    alert.present();
  }

  backFintechGeneral(){
    this.navCtrl.pop();
  }
}
