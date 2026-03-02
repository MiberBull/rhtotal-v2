import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PATH_FINTECH, KEYS_STORAGE } from '../../environments/environments';
import { Observable } from 'rxjs';
import { StorageProvider } from '../storage/storage';
import { AlertController } from 'ionic-angular';

/*
  Generated class for the PaysheetProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class PaysheetProvider {

  constructor(public http: HttpClient,private storage_provider:StorageProvider, private alertCtrl:AlertController) {
  }

  velocashService() {
    let idUser = this.storage_provider.getItem( KEYS_STORAGE.USER ).id;
    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.VELOCAHS}?idUser=${ idUser }`;
    return this.http.get( URL ).timeout(4000);
  }
    
  myAdvanceService() {
    let idUser = this.storage_provider.getItem( KEYS_STORAGE.USER ).id;
    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.MY_ADVANCE}?idUser=${ idUser }`;
    return this.http.get( URL ).timeout(4000);
  }

  loanVelocash( fintech:any ) {
    let URL = `${PATH_FINTECH.DOMAIN}/nomina/prestamo`;
    
    let customFintech = {
      user:this.storage_provider.getUser(),
      fintech:fintech
    }
    return this.http.post( URL,customFintech );
  }

  loanMyAdvanced( fintech:any ) {
    let URL = `${PATH_FINTECH.DOMAIN}/nomina/prestamo/adelanto`

    
    let customFintech = {
      user:this.storage_provider.getUser(),
      fintech:fintech
    }
    return this.http.post( URL,customFintech );
  }

  clickedInfo() {
    let email = this.storage_provider.getUser().email;
    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.VISIT_SWAP}`
    return this.http.post(URL,{user:email});
  }

  showAlertInfoSwap( platform:string ) {
    let imageStore = platform === 'ios' ? 'assets/imgs/alert/appstore.png' : 'assets/imgs/alert/playstore.png';
    let alert = this.alertCtrl.create({   
      title: `<div class="wrapper-img"><img src="assets/imgs/alert/swap.jpg" class="img-swap"/></div>`,
      subTitle:`<label>Envía y recibe pagos</label>
                <p class="text-black">Mándale dinero a cualquier persona de tu lista de contactos, sin clabes ni números de cuenta.</p>
                <p class="text-black">
                  Un contacto, tu tarjeta, Swap, y listo.
                  <br>
                  Olvidate del efectivo y de ir al cajero.
                </p>
                <div class="wrapper-img-store"><img src="${imageStore}" class="img-store"/></div>`,
      cssClass:'alertCustomCss',
      buttons:[{
        text: 'Ok',
        role: 'cancel'
      }]
    });
    alert.present();
  }

  handleError(error: any) {
    return Observable.throw(error);
  }

  

}
