import { Component, ChangeDetectorRef } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ConsumeBenefistProvider } from '../../providers/consume-benefist/consume-benefist';
import { InsuranceTO } from '../../models/employee.compl';
import { DetailInsurancePage } from '../detail-insurance/detail-insurance';
import { UserTO } from '../../models/user.model';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { StorageProvider } from '../../providers/storage/storage';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { UsersProvider } from '../../providers/users/users';

/**
 * Generated class for the InsuranceBenefitsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-insurance-benefits',
  templateUrl: 'insurance-benefits.html',
})
export class InsuranceBenefitsPage {
  dataInsurance:Array<InsuranceTO> = [];
  userObejct: UserTO;
  constructor(public navCtrl: NavController,private ref: ChangeDetectorRef,
    public navParams: NavParams,public storage: StorageProvider,public loading:EventsManagerProvider,public dialog:UsersProvider,public insurance:ConsumeBenefistProvider) {
    this.loading.setIsLoadingEvent(true);
      this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.getInsuranceBenefis();
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad InsuranceBenefitsPage');
  }

  getInsuranceBenefis(){
    this.insurance.getInsuranseBenefis(this.userObejct.id).subscribe((data:Array<InsuranceTO>) =>{
      this.dataInsurance = data;
      this.ref.detectChanges();
      this.loading.setIsLoadingEvent(false);
    },error=>{
      console.log(error);
      this.loading.setIsLoadingEvent(false);
      this.dialog.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.RESPONSE_EMPTY);
    });
  }


  getInsurance(id:string,typeInsurance:string){
    this.navCtrl.push(DetailInsurancePage,{idInsurance:id,idInsurangeType:typeInsurance});
  }
  
  back(){
    this.navCtrl.pop();
  }
}
