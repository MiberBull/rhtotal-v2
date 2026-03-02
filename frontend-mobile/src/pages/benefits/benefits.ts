import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { QuotesafePage } from '../quotesafe/quotesafe';
import { BenefitsOptionPage } from '../benefits-option/benefits-option';
import { DiscountOptionPage } from '../discount-option/discount-option';
import { InsuranceBenefitsPage } from '../insurance-benefits/insurance-benefits';
import { StorageProvider } from '../../providers/storage/storage';
import { UserTO } from '../../models/user.model';
import { TYPE_USER, KEYS_STORAGE } from '../../environments/environments';

/**
 * Generated class for the BenefitsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-benefits',
  templateUrl: 'benefits.html',
})
export class BenefitsPage {
  userObejct: UserTO;
  userType:string;
  arrowIntern:string;
  dataimageDinamic:string;
  constructor(public navCtrl: NavController, 
    public storage: StorageProvider,
    public navParams: NavParams) {
      this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
      this.userType = this.userObejct.userType ;

      if(this.userObejct.userType === 'IN'){
        this.arrowIntern = 'assets/icon/arrow.svg';
        this.dataimageDinamic = 'assets/imgs/miseguros.svg';
      }else{
        this.arrowIntern = 'assets/icon/arrow_gris.svg';
        this.dataimageDinamic = 'assets/imgs/missegurosgris.svg';
      }
    
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad BenefitsPage');
  }

  goDiscountOption(){
    this.navCtrl.push( DiscountOptionPage ); 
  }
  
  goQuoteSafe() {
    this.navCtrl.push( QuotesafePage );
  }

  goBenefitsOption() {
    this.navCtrl.push( BenefitsOptionPage );
  }

  insurangeBenefis(){
    if( TYPE_USER.IN === this.userType ) {
      this.navCtrl.push( InsuranceBenefitsPage );
    }
  }

  backDiscountGeneral() {
    this.navCtrl.pop();
  }
}
