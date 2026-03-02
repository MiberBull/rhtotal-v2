import { Injectable } from '@angular/core';
import { Dialogs } from '@ionic-native/dialogs';
import { AlertController } from 'ionic-angular';
/*
  Generated class for the DialogsProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class DialogsProvider {

  constructor(private dialogs: Dialogs,private alertCtrl: AlertController) {
    console.log('Hello DialogsProvider Provider');
  }

  viewDialog(title : string, content : string){
   return this.dialogs.confirm(content,title,['OK']);
  }

  viewDialogDissBlock(){
      return this.alertCtrl.create({
        title: 'Low battery',
        subTitle: '10% of battery remaining',
        buttons: ['Dismiss'],
        enableBackdropDismiss: false 
      });
      
  }

}
