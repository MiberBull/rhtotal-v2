import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, Tabs } from 'ionic-angular';
import { PersonalInformationPage } from '../personal-information/personal-information';
import { SocialNetworksPage } from '../social-networks/social-networks';
import { AddressPage } from '../address/address';
import { UsersProvider } from '../../providers/users/users';

/**
 * Generated class for the MyDataPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-my-data',
  templateUrl: 'my-data.html',
})

export class MyDataPage {
  @ViewChild('myTabs') tabRef: Tabs;

  tab1Root = PersonalInformationPage;
  tab2Root = AddressPage;
  tab3Root = SocialNetworksPage;
  
  tab1: boolean = true;
  tab2: boolean = true;
  constructor(public navCtrl: NavController, public navParams: NavParams,public tabs :UsersProvider) {
    this.tabs.getTabsEvent().subscribe(data=>{
    
        this.tab1 = data.tab1;
        this.tab2 = data.tab2;
    });
  }

  returnPageMyData(){
    this.navCtrl.pop();
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MyDataPage');
  }

}
