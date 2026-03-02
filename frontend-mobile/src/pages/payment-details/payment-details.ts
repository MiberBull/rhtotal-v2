import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { RhProvider } from '../../providers/rh/rh';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { MSG_DIALOG } from '../../environments/environments';
import { DialogsProvider } from '../../providers/dialogs/dialogs';

@Component({
  selector: 'page-payment-details',
  templateUrl: 'payment-details.html',
})
export class PaymentDetailsPage {

  datePayment:string = '';
  infoPayment:any = {};
  dateService:Date;

  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams,
    private rh_provider: RhProvider,
    private events_provider: EventsManagerProvider,
    private dialog_provider: DialogsProvider) {
      this.dateService = new Date(this.navParams.get("dateTime"));
  }

  ionViewDidLoad() {
  }

  backDiscount() {
    this.navCtrl.pop();
  }

  searchPaymentMount() {
    if( this.datePayment.length > 0 ) {
      this.events_provider.setIsLoadingEvent(true);
      var year = this.datePayment.split('-')[0];
      var mount = this.datePayment.split('-')[1];
      this.rh_provider.getPaymentDetails(mount,year).subscribe( resp => {
        this.infoPayment = resp;
      },error => {
        this.events_provider.setIsLoadingEvent(false);
        this.dialog_provider.viewDialog(MSG_DIALOG.ERROR_TITLE,error.error.message);
        this.infoPayment.businessName = '';
        this.infoPayment.fixedIncome = '';
        this.infoPayment.nameProject = '';
        console.log(JSON.stringify(error));
      },() => this.events_provider.setIsLoadingEvent(false));
    }
  }

}
