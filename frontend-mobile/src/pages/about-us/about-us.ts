import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ConsumeHelpProvider } from '../../providers/consume-help/consume-help';
import { ARRAYPARAM } from '../../environments/environments';
import { DialogsProvider } from '../../providers/dialogs/dialogs';
import { UsersProvider } from '../../providers/users/users';

/**
 * Generated class for the AboutUsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-about-us',
  templateUrl: 'about-us.html',
})
export class AboutUsPage {
  info:string;
  constructor(public navCtrl: NavController,public users: UsersProvider, public navParams: NavParams,public consumeHelp:ConsumeHelpProvider, public dialogs: DialogsProvider) {
  this.consumeHelp.getParameter(ARRAYPARAM[0]).then(data =>{
     this.info = data[0].value;
  });
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AboutUsPage');
  }

  NoticeOfPrivacy(){
    this.consumeHelp.getParameter(ARRAYPARAM[2]).then(data =>{
    this.users.showConfirmAlertCenter('Aviso de privacidad',data[0].value);
   });
    }
}
