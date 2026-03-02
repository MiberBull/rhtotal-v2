import { Component } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import { ConsumeBenefistProvider } from '../../providers/consume-benefist/consume-benefist';
import { VARIABLES_PAGE_DISCOUNT, MESES } from '../../environments/environments';
import { InAppBrowser } from '@ionic-native/in-app-browser';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';

/**
 * Generated class for the DetailBenefisPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-detail-benefis',
  templateUrl: 'detail-benefis.html',
})
export class DetailBenefisPage {
  images:string[] = [];
  details:string[] = [];
  detail:string;
  endate:string;
  constructor(public navCtrl: NavController,private iab: InAppBrowser, public loading:EventsManagerProvider ,public navParams: NavParams, public discount :ConsumeBenefistProvider,public alertCtrl: AlertController) {
    let id:number = navParams.get('id');
    this.loading.setIsLoadingEvent(true);
    this.discount.getImagesSecundary(id,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_IMAGE_SECUNDARY)
    .subscribe(data=>{
      data.forEach(element => {
        this.images.push(element.value);
      });
      let dateString: Date = new  Date(data[0].idDiscount.endDate);
      let date:string  = `Válido hasta el ${dateString.getDate() >9?dateString.getDate():0+dateString.getDate()} de ${MESES[dateString.getMonth()]} del ${dateString.getFullYear()}`;   
      this.endate = date;
      this.detail = data[0].idDiscount.notificationDetail;
      this.details.push(data[0].idDiscount.title);
      this.details.push(data[0].idDiscount.description);
      this.details.push(data[0].idDiscount.linkUrl);
      this.details.push(data[0].idDiscount.termsConditions);
      this.loading.setIsLoadingEvent(false);
    });
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad DetailBenefisPage');
  }

  getBackgroundStyle(image) {
    return {
      'background-image': `url('${image}')`
    }
  }

  backDiscount(){
    this.navCtrl.pop();
    }

    openUri(){
      const browser = this.iab.create(this.details[2]);
      browser.show()
    }

    NoticeOfPrivacy(){
      let alert = this.alertCtrl.create({
        title:`<div>Términos y Condiciones</div>`,
        subTitle:`<h6>${this.details[3]}</h6>`,
        buttons:[
        {
          text: 'Aceptar',
          handler: () => {
          }
        }],
        cssClass:'alertCustomCss'
      });
      alert.present();
    }
}
