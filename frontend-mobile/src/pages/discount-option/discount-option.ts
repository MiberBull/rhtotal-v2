import { Component, ChangeDetectorRef } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import { ConsumeBenefistProvider } from '../../providers/consume-benefist/consume-benefist';
import { UserTO } from '../../models/user.model';
import { KEYS_STORAGE, VARIABLES_PAGE_DISCOUNT, MSG_DIALOG } from '../../environments/environments';
import { StorageProvider } from '../../providers/storage/storage';
import { DetailBenefisPage } from '../detail-benefis/detail-benefis';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { PonderationMobileTO } from '../../models/employee.compl';
import { UsersProvider } from '../../providers/users/users';

/**
 * Generated class for the DiscountOptionPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-discount-option',
  templateUrl: 'discount-option.html',
})
export class DiscountOptionPage {

  categoryOptions:any[] = [];
  subCategoryOptions:any[] = []
  cards:any[] = [];
  idCategory:number;
  idSubCategory:number;  
  userObejct:UserTO;
  countInit:number= 1;
  countPerzonal:number= 1;
  scrollInit:boolean = true;
  subCategoryEnabled = true;
  selectedSubcategory:string;
  porcentaje:number = 0;
  promediofinalUserId:number = 0;
  imageCandado:string;
  constructor(public navCtrl: NavController,public alertCtrl: AlertController, 
    public users: UsersProvider, public navParams: NavParams,
    public ref: ChangeDetectorRef,public loading:EventsManagerProvider ,
    public optionBenefist:ConsumeBenefistProvider, public storage:StorageProvider) {
     this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    
     this.users.getPonderation(this.userObejct.id).subscribe((data: Array<PonderationMobileTO>) => {
      this.promediofinalUserId = data[0].promedioFinal;
     });

     this.loading.setIsLoadingEvent(true);
     this.optionBenefist.getCategory(this.userObejct.id,"D").subscribe(data=>{
      data.forEach(element => {
        this.categoryOptions.push({name:element.category,id:element.idCategory});
      });
    },error =>{
      this.loading.setIsLoadingEvent(false);
    },()=>{
      this.loading.setIsLoadingEvent(false);
      this.getInitImage();
    });


  }

   getIdSubcategory(id:number){
    this.scrollInit = false;
    this.subCategoryEnabled = false;
    this.countInit = 1;
    this.idCategory = id;
    this.loading.setIsLoadingEvent(true);
    this.optionBenefist.getSubCategory(id,this.userObejct.id,'D').subscribe(data =>{
    this.subCategoryOptions = [];
    this.selectedSubcategory =  null;
    this.cards =[];
    data.forEach(element => {
      this.subCategoryOptions.push({name:element.subcategory,id:element.idSubCategory});
    });
    this.loading.setIsLoadingEvent(false);
  },error=>{
    this.loading.setIsLoadingEvent(false);
  });
  }

  
  getCardImages(idSubcategory:number){
    this.idSubCategory = idSubcategory;
    this.loading.setIsLoadingEvent(true);
    this.optionBenefist.getDdiscountImagesCard(VARIABLES_PAGE_DISCOUNT.VAR_INDEX_INIT_PAGE,this.validateUserEx(),VARIABLES_PAGE_DISCOUNT.VAR_TYPE_NOTIFICATION,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_IMAGE,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_DISCOUNT_DISCOUNT,this.idCategory,this.idSubCategory).subscribe(data => {
      this.cards = [];
      data.forEach(element => {
        this.filterByLevel(element);
      });
      this.loading.setIsLoadingEvent(false);
    },error =>{
      this.loading.setIsLoadingEvent(false);
      this.showConfirmAlert();
    });
  }

  getInitImage(){
    this.optionBenefist.getDdiscountImagesInitCard(VARIABLES_PAGE_DISCOUNT.VAR_INDEX_INIT_PAGE,this.validateUserEx(),VARIABLES_PAGE_DISCOUNT.VAR_TYPE_NOTIFICATION,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_IMAGE,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_DISCOUNT_DISCOUNT).subscribe(data => {
      data.forEach(element => {
        this.filterByLevel(element);
      });
    },error =>{
      console.log('error en la consulta');
      this.showConfirmAlert();
      },()=>{
        this.ref.detectChanges();
      });
    }


  doInfinite(infiniteScroll) {

    if(this.scrollInit){
      this.optionBenefist.getDdiscountImagesInitCard(this.countInit,this.validateUserEx(),VARIABLES_PAGE_DISCOUNT.VAR_TYPE_NOTIFICATION,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_IMAGE,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_DISCOUNT_DISCOUNT).subscribe(data => {
        if(data.length > 0){
          data.forEach(element => {
            this.filterByLevel(element);
          });  
        }
      },error =>{
        console.log('error en la consulta');
      });
  
      setTimeout(() => {
        infiniteScroll.complete();
        this.countInit++;
      }, 500);    
      return;
    }
    this.optionBenefist.getDdiscountImagesCard(this.countPerzonal,this.validateUserEx(),VARIABLES_PAGE_DISCOUNT.VAR_TYPE_NOTIFICATION,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_IMAGE,VARIABLES_PAGE_DISCOUNT.VAR_TYPE_DISCOUNT_DISCOUNT,this.idCategory,this.idSubCategory).subscribe(data => {
      if(data.length > 0){
        console.log(data);
        data.forEach(element => {
          this.filterByLevel(element);
        });  
      }
    },error =>{
      console.log('error en la consulta');
    });

    setTimeout(() => {
      infiniteScroll.complete();
      this.countPerzonal++;
    }, 500);
  }


  filterByLevel(element: any ){
     let enable:boolean = false;
     let etiqueta:string[] =[];
     let color:string;
    switch(element.idDiscount.levelRh){
      case 1:
      this.porcentaje = 90;
      etiqueta.push('Sube a nivel oro.');
      color = '#ffbf00';
      this.imageCandado = 'assets/imgs/candadooro.svg';
      enable = (this.promediofinalUserId >= this.porcentaje);
      break;
      case 2:
      etiqueta.push('Sube a nivel plata.');
      color = '#8a9597';
      this.imageCandado = 'assets/imgs/candadoplata.svg';
      this.porcentaje = 50;
      enable = ( this.promediofinalUserId >= this.porcentaje);
      break;
      case 3:
      etiqueta.push('Sube a nivel Bronce.');  
      color = '#763c28';
      this.imageCandado = 'assets/imgs/candadobronce.svg';
      this.porcentaje = 10;
      enable = ( this.promediofinalUserId >= this.porcentaje);
       break;
     }
      etiqueta.push('¡Y aprovecha esta promoción!');
    this.cards.push({id:element.idDiscount.idDiscount,enabled:enable,base64:element.value,descripcion:element.idDiscount.descriptionPreview,levelName:etiqueta,color:color,notificationDetail:element.idDiscount.notificationDetail,endDate:element.idDiscount.endDate,porcentaje:this.porcentaje,imageCandado:this.imageCandado});        
    }

  backDiscount() {
    this.navCtrl.pop();
  }


  showConfirmAlert() {
    let alert = this.alertCtrl.create({
      title:`<label></label>`,
      subTitle:`<h6>No se encontraron resultados</h6>`,
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

    getDiscountByid(id:number,porcentaje:number){
      

      if (this.promediofinalUserId >= porcentaje && this.promediofinalUserId <= 49) {
        this.navCtrl.push(DetailBenefisPage,{id:id});
        return; 
      }

      if (this.promediofinalUserId >= porcentaje && this.promediofinalUserId <= 89 ) {
        this.navCtrl.push(DetailBenefisPage,{id:id});
        return;
      }

      if (this.promediofinalUserId >= porcentaje) {
        this.navCtrl.push(DetailBenefisPage,{id:id});
        return;
      }
    }

    validateUserEx(){
      if(this.userObejct.userType === "IN"){
        return this.userObejct.id;
      }
      return 0;
    
    }


    accedeNivel(title,subTitle){
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,`${title} ${subTitle}`);
    }
}
