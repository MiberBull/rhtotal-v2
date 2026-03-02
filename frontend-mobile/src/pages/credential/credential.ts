import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { StorageProvider } from '../../providers/storage/storage';
import { UsersProvider } from '../../providers/users/users';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { Conversions } from '../../util/conversions';
import { MessageGeneral } from '../../iterface/create-account.interface';

/**
 * Generated class for the CredentialPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-credential',
  templateUrl: 'credential.html',
})
export class CredentialPage {

  user:any;
  name:string = '';
  numberEmployee:number;
  since:string = '';
  timeLoading:any;
  infoUser:any;
  image:string;
  viewCredential:boolean;

  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams,
    private storage_provider:StorageProvider,
    private user_provider: UsersProvider,
    private events_manager: EventsManagerProvider) {

      this.viewCredential = false;
      
      this.user = this.storage_provider.getUser();
      if( (this.infoUser = storage_provider.getItem(KEYS_STORAGE.INFO_CRENDENTIAL)) ) {
        this.setValuesForCredential(this.infoUser,true);
      } else {
        this.user_provider.getInfoCredential(this.user.idUser)
        .subscribe( data => {
          this.setValuesForCredential(data);
        },error => {
          console.log( error );
          this.events_manager.setIsLoadingEvent(false);
          if( error.error) {
            this.showAlert(MSG_DIALOG.ERROR_TITLE,error.error.message);
          } else {
            this.showAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
          }
        },() => {});
      }

  }

  ionViewDidLoad() {
    if( !this.infoUser ) {
      this.events_manager.setIsLoadingEvent(true);
      this.timeLoading = setTimeout( () => {
        this.events_manager.setIsLoadingEvent(false);
      },10000);
    }
  }

  setValuesForCredential(data,storage = false) {
    if( !storage ) {
      this.storage_provider.saveItem(KEYS_STORAGE.INFO_CRENDENTIAL,data);
    }     
    this.name = `${Conversions.UpperAllLetter(data.name)} ${Conversions.UpperAllLetter(data.lastName)} ${Conversions.UpperAllLetter(data.mLastName)}`;
    this.numberEmployee = this.user.idUser;
    let since = new Date( data.since );
    this.since = `${since.getMonth()+1}/${since.getFullYear()}`;
    this.image = data.imageBase64;
    this.events_manager.setIsLoadingEvent(false);
    this.viewCredential = true;
    clearTimeout( this.timeLoading );
  }

  showAlert(title,message) {
    let msg = new MessageGeneral();
    msg.title = title;
    msg.msg = message;
    this.events_manager.setGeneralNotificationMessage( msg );
  }

}
