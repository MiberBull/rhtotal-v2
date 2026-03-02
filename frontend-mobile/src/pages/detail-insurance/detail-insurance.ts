import { Component } from '@angular/core';
import { NavController, NavParams, Platform } from 'ionic-angular';
import { FormGroup, FormBuilder } from '../../../node_modules/@angular/forms';
import { ConsumeBenefistProvider } from '../../providers/consume-benefist/consume-benefist';
import { InsuranceDetailTO, CoverageTO } from '../../models/employee.compl';
import { InAppBrowser } from '../../../node_modules/@ionic-native/in-app-browser';
import { File, IWriteOptions } from '@ionic-native/file';
import { FileOpener } from '@ionic-native/file-opener';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { UsersProvider } from '../../providers/users/users';
import { MSG_DIALOG } from '../../environments/environments';
/**
 * Generated class for the DetailInsurancePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-detail-insurance',
  templateUrl: 'detail-insurance.html',
})
export class DetailInsurancePage {
  private todo: FormGroup;
  coverturaVisible:boolean = false;
  certificateVisible:boolean = false;
  typePoliceVisible:boolean = false;
  urlPageInsurange:string;
  pdfVacio:boolean  = false; 
  coverageVisibility:boolean = false
  coverageVisibilityCard = false;
  endDateVisivility = true;
  startDateVisivility = true;
  cardCoverage:Array<CoverageTO> = [];
  dataEtiquetas: string[] = [
    'Tipo de seguro',
    'Número de póliza',
    'Tipo de póliza',
    'Número de certificado (clave personal)',
    'Cobertura',
    'Url sitio del seguro',
    'Nombre de la institución aseguradora',
    'Teléfono de la institución aseguradora',
    'Fecha inicio del contrato',
    'Fecha fin del contrato'];
  constructor(public navCtrl: NavController,private fileOpener: FileOpener,public loading:EventsManagerProvider,public dialog:UsersProvider, private platform: Platform,  private file: File,private formBuilder: FormBuilder,private iab: InAppBrowser, public insurance:ConsumeBenefistProvider,public navParams: NavParams) {
   
    this.todo = this.formBuilder.group({
      insurangeType:[''],
      policy:[''],
      insuranceCarrier:[''],
      phones:[''],
      coverage:[''],
      endDate:[''],
      individualCertificate:[''],
      typePolicy:[''],
      startDate:['']
    });
    this.loading.setIsLoadingEvent(true);
    let idInsurance:number = Number.parseInt(navParams.get('idInsurance'));
    let idType:number = Number.parseInt(navParams.get('idInsurangeType'));
    
    this.typeInsuranceVisibility(idType,false);
     this.getDetailInsurance(idInsurance);
     this.coverageInsurange(idInsurance,idType);
    

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad DetailInsurancePage');
  }


  getDetailInsurance(id: number) {
    let base64: string;
    this.insurance.getInsuranceBenefisDetail(id).subscribe((data: InsuranceDetailTO) => {
      this.urlPageInsurange = data.url;
      base64 = data.contractPdf;
      this.loadDataInsurance(data);
      this.loading.setIsLoadingEvent(false);
    }, error => {
      console.log(error);
      this.loading.setIsLoadingEvent(false);
    }, () => {
      this.savePdfInsurance(base64);
    });

  }


  loadDataInsurance(data:InsuranceDetailTO){
   
    for (const key in data) {
     for (const value in this.todo.controls) {
       if(key === value){
         if(key ==='endDate' || key === 'startDate'){
          let date:Date = new Date(data[key]);
          let dateISO:string =`${date.getDate() < 9 ? `0${date.getDate()}`:date.getDate()}-${date.getMonth()+1 > 9 ?date.getMonth()+1:`0${date.getMonth()+1}`}-${date.getFullYear()}`; 
          this.todo.controls[key].setValue(dateISO);
         }else{
          this.todo.controls[key].setValue(data[key] === 'null'?'':data[key]);
         }
       }
     }
    }
    this.loading.setIsLoadingEvent(false);
  }

  typeInsuranceVisibility(typeInsurance: number,data:boolean) {
    
    if(typeInsurance === 3 && data){
      this.coverageVisibilityCard = true;
      this.endDateVisivility = false;
      this.startDateVisivility =  false;

    }

    if (typeInsurance === 1) {
      this.coverturaVisible = true;
      this.endDateVisivility = false;
      this.startDateVisivility =  false;
      return;
    }

    if (typeInsurance === 2) {
      this.certificateVisible = true;
      this.endDateVisivility = false;
      this.startDateVisivility =  false;
      return;
    }

    if (typeInsurance === 3 ) {
      this.typePoliceVisible = true;
      this.endDateVisivility = false;
      this.startDateVisivility =  false;
      return;
    }

    if(typeInsurance === 4){
      this.typePoliceVisible = true;
      return;
    }    

  }

  openUri(){
    const browser = this.iab.create(this.urlPageInsurange);
    browser.show()
  }

  savePdfInsurance(base64:string){
    
    if(base64 === 'null' || base64 === ''){
     this.pdfVacio = true;
     return;
    }

    let options: IWriteOptions = {
      replace: true,
      append: false
    };
    fetch(base64).then(data => {
      data.blob().then(blob => {
        this.file.writeFile(this.ruta(), 'poliza.pdf', blob, options).then(data => {
          console.log(data);
        }).catch(() => console.log('error file'));
      }).catch(() => console.log('error blob'));
    }).catch(() => console.log('error feche'));
  }



  ruta(){
   let phat = null;
    if( this.platform.is('ios')){
      phat = this.file.documentsDirectory;
    }
    if(this.platform.is('android'))
    {
     phat = this.file.dataDirectory;
    }
    return phat;
  }

  openFilePdf(){

    if(this.pdfVacio){
      this.dialog.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.RESPONSE_EMPTY);
      return;
    }
    this.fileOpener.open(`${this.ruta()}poliza.pdf`, 'application/pdf')
    .then(() => console.log('File is opened'))
    .catch(e => console.log('Error opening file', e));

  }
  back(){
    this.navCtrl.pop();
  }


  coverageInsurange(idInsurance: number,typeInsurance:number) {
    this.insurance.getCoverage(idInsurance).subscribe((data: Array<CoverageTO>) => {
      if (data.length > 0) {
        this.typeInsuranceVisibility(typeInsurance,true);
        this.cardCoverage = data;
        return;
      }
    },error =>{
      console.log(error);
    });
  }
}