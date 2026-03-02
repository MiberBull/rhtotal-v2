import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, Tabs } from 'ionic-angular';
import { EmploymentBenefitsPage } from '../employment-benefits/employment-benefits';
import { InsurancePage } from '../insurance/insurance';
import { BonusesPage } from '../bonuses/bonuses';
import { SalaryPage } from '../salary/salary';
import { UsersProvider } from '../../providers/users/users';

/**
 * Generated class for the CompensationPackagePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-compensation-package',
  templateUrl: 'compensation-package.html',
})
export class CompensationPackagePage {
  @ViewChild('myTabsCompensation') tabRef: Tabs;

  tab1Root = EmploymentBenefitsPage;
  tab2Root = InsurancePage;
  tab3Root = BonusesPage;
  tab4Root = SalaryPage;

  constructor(public navCtrl: NavController,public navParams: NavParams,public tabs :UsersProvider) {

  }

  returnPageCompensation(){
    this.navCtrl.pop();
  }
  ionViewDidLoad() {
    console.log('ionViewDidLoad CompensationPackagePage');
  }

}
