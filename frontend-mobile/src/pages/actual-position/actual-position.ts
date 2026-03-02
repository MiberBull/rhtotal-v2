import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

/**
 * Generated class for the ActualPositionPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-actual-position',
  templateUrl: 'actual-position.html',
})
export class ActualPositionPage {

  info:any = {};

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.info = navParams.get("data");
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ActualPositionPage');
  }

  backDiscount() {
    this.navCtrl.pop();
  }

}
