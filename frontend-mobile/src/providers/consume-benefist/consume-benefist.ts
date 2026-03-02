import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PATH_APLICATION } from '../../environments/environments';
import { Observable } from '../../../node_modules/rxjs';
import { AlertController } from 'ionic-angular';

/*
  Generated class for the ConsumeBenefistProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ConsumeBenefistProvider {

  constructor(public http: HttpClient,public alertCtrl: AlertController) {
    console.log('Hello ConsumeBenefistProvider Provider');
  }

  getDdiscountImagesCard(page:number,idUser:number,typeNotification:string,typeImage:string,typeDiscount:string,idCategory = null,idSubcatecory = null) {
    let params= new HttpParams()
    .set('page',page.toString())
    .set('idUser',idUser.toString())
    .set('typeNotification',typeNotification)
    .set('typeImage',typeImage)
    .set('typeDiscount',typeDiscount)
    .set('idCategory',idCategory.toString())
    .set('idSubcatecory',idSubcatecory.toString());
   return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.DISCOUNT_IMAGE}`,{params}).catch(this.handleError);
  }

  getDdiscountImagesInitCard(page:number,idUser:number,typeNotification:string,typeImage:string,typeDiscount:string) {
    let params= new HttpParams()
    .set('page',page.toString())
    .set('idUser',idUser.toString())
    .set('typeNotification',typeNotification)
    .set('typeImage',typeImage)
    .set('typeDiscount',typeDiscount);
   return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.DISCOUNT_IMAGE}`,{params}).catch(this.handleError);
  }

  getImagesSecundary(idDiscount:number,typeImage:string){
    let params = new HttpParams()
    .set('idDiscount',idDiscount.toString())
    .set('typeImage',typeImage);
    return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.DISCOUNT_IMAGE_SECUNDARY}`,{params}).catch(this.handleError);
  }

  getCategory(idUser:number,typeNotification:string){
    let params= new HttpParams()
    .set('idUser',idUser.toString())
    .set('typeDiscount',typeNotification);
   return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.CATEGORY_IDUSER}`,{params}).catch(this.handleError);
  }

  getSubCategory(idcategory:number,idUser:number,typeNotification:string){
    let params= new HttpParams()
    .set('idCategory',idcategory.toString())
    .set('idUser',idUser.toString())
    .set('typeDiscount',typeNotification);
    return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.SUBCATEGORY_IDUSER}`,{params}).catch(this.handleError);
}

handleError(error: any) {
    console.log('error al consumir service');
    return Observable.throw(error);
  }

getVerifyDateNotification(id:number){
  let params= new HttpParams()
    .set('id',id.toString());
  return  this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.VERIFY_EMAIL}`,{params}).catch(this.handleError);
}

 getLevelDiscount(id:number){
  let params= new HttpParams()
  .set('id',id.toString());
  return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.GET_lEVEL_DISCOUNT}`,{params}).catch(this.handleError);
} 

  showConfirmAlert(title:string,content:string) {
    let alert = this.alertCtrl.create({
      title:`<label>${title}</label>`,
      subTitle:`<h6>${content}</h6>`,
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
  
getInsuranseBenefis(id:number){
  let params= new HttpParams()
  .set('idUser',id.toString());
return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.INSURANCE_BENEFIS}`,{params}).catch(this.handleError);
}


getInsuranceBenefisDetail(id:number){
  let params= new HttpParams()
  .set('idInsurance',id.toString());
  return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.INSURANCE_DETAIL_BENEFIS}`,{params}).catch(this.handleError);
}

  getCoverage(idInsurange: number) {
    let params = new HttpParams()
      .set('idInsurance', idInsurange.toString());
    return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.GET_COVERAGE_INSURANCE}`, { params }).catch(this.handleError);
  }
  getVerifyDateNotificationInsurance( id ) {
    let params = new HttpParams()
        .set('id',id.toString());
    return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.VERIFY_INSURANCE}`,{params}).catch(this.handleError);

  }

}
