import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SocialNetworkTO } from '../../models/employee.compl';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, SOCIAL_NETWORK, MSG_DIALOG } from '../../environments/environments';
import { UserTO } from '../../models/user.model';
import { UsersProvider } from '../../providers/users/users';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { MessageGeneral } from '../../iterface/create-account.interface';

/**
 * Generated class for the SocialNetworksPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-social-networks',
  templateUrl: 'social-networks.html',
})
export class SocialNetworksPage {
  facebook:boolean = false;
  twitter:boolean = false;
  linkedIn:boolean = false;
  google:boolean = false;
  instagram:boolean = false;
  snapchat:boolean = false;
  Spotify:boolean = false;
  userObejct:UserTO;
  selected:any;
  msg = new MessageGeneral();
  socialList = new Array<SocialNetworkTO>();
  socialListUpdate = new Array<SocialNetworkTO>();
  arraySocialNetwork:any[] = [];
  constructor(public navCtrl: NavController,public loading:EventsManagerProvider,public users:UsersProvider, public storage:StorageProvider, public navParams: NavParams) {
  }
  ionViewWillEnter(){
    this.arraySocialNetwork = [];
    this.arraySocialNetwork.push(
      {name:'Facebook',value:null},
      {name:'Twitter',value:null},
      {name:'LinkedIn',value:null},
      {name:'Google+',value:null},
      {name:'Instagram',value:null},
      {name:'Snapchat',value:null},
      {name:'Spotify',value:null});

    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.loading.setIsLoadingEvent(true);
    this.users.getSocialNetwork(this.userObejct.id).subscribe(data =>{
      this.arraySocialNetwork = [];
        SOCIAL_NETWORK.forEach(elementSocial => {
          data.forEach((element:SocialNetworkTO) => {
          if(elementSocial === element.nameRedSocial){
            this.arraySocialNetwork.push({name:element.nameRedSocial,value:this.setValueNumber(element.value)});
          }
        });

      });
      this.socialListUpdate = data;
      this.loading.setIsLoadingEvent(false);
    },error =>{
        console.log(error);
        this.loading.setIsLoadingEvent(false);
    });
  
  }

  saveSocialNetwork(){
    if(this.socialListUpdate.length > 0){
      for (let index = 0; index < this.arraySocialNetwork.length; index++) {
        const element = this.arraySocialNetwork[index];
        this.socialListUpdate[index].value = this.getValueNumber(element.value);
      }
       this.saveOrUpdateSocialNetwork(this.socialListUpdate);
      return;
    }

   for (let index = 0; index < this.arraySocialNetwork.length; index++) {
    const element = this.arraySocialNetwork[index];
       let social = new SocialNetworkTO();
       social.idUSer =this.userObejct.id;
       social.nameRedSocial = element.name;
       social.value = this.getValueNumber(element.value);
       social.lastUserModifier = this.userObejct.email;
       social.lastModification = null;
       social.creationUser =this.userObejct.email;
       social.creationDate = null;
       social.active = true;
       this.socialList.push(social);
   }
   this.saveOrUpdateSocialNetwork(this.socialList);
   
  }


  saveOrUpdateSocialNetwork(social :Array<SocialNetworkTO>){
    this.loading.setIsLoadingEvent(true);
    this.users.saveSocialNetwork(social).subscribe((data:boolean) =>{
      if(data){
        this.loading.setIsLoadingEvent(false);
        this.users.setTabsEvent({tab1:true,tab2:true});  
        this.msg.title = MSG_DIALOG.OK;
        this.msg.msg = MSG_DIALOG.MSG_SAVE;
        this.loading.setGeneralNotificationMessage(this.msg);
        setTimeout(()=>{
          this.navCtrl.parent.select(0);
        },2100);
       return;
      }
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
      this.loading.setIsLoadingEvent(false);
    },error=>{
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
      this.loading.setIsLoadingEvent(false);
    });
  }


  setValueNumber(value: any): any {
    if (value === '0') {
      return false;
    }
    if (value === '1') {
      return null;
    }
    if (value === '2') {
      return true;
    }
    return value;
  }

  
  getValueNumber(value):string{
    
    if(value === null){
      return '1';
     }

    if(!value){
      return '0';
    }

    if(value){
     return '2';
    }

  }


  changeState(event){
  
  }

}
