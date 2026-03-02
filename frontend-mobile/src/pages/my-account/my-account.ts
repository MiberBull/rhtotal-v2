import { Component, NgZone } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { CompensationPackagePage } from '../compensation-package/compensation-package';
import { MyDataPage } from '../my-data/my-data';
import { HiringDataPage } from '../hiring-data/hiring-data';
import { AsignationDataPage } from '../asignation-data/asignation-data';
import { LastJobPage } from '../last-job/last-job';
import { UsersProvider } from '../../providers/users/users';
import { KEYS_STORAGE } from '../../environments/environments';
import { StorageProvider } from '../../providers/storage/storage';
import { UserTO } from '../../models/user.model';
import { PonderationMobileTO } from '../../models/employee.compl';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';

/**
 * Generated class for the MyAccountPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-my-account',
  templateUrl: 'my-account.html',
})
export class MyAccountPage {
  doughnutChartData:number[] = [];
  doughnutChartDataMisDatos:number[] = [];
  doughnutChartDataPaquete:number[] = [];
  doughnutChartDataContratacion:number[] = [];
  doughnutChartDataAsignation:number[] = [];
  doughnutChartDataEmpleo:number[] = [];

  labelMisdatos: string;
  labelDataPaquete: string;
  labelDataContratacion: string;
  labelDataAsignation: string;
  labelDataEmpleo: string;
  labelGeneral:string;

  enableDatos:Boolean[] = [false,false,false,false,false];
  enableMisdatos: boolean =  true;
  enableDataPaquete: boolean = true;
  enableDataContratacion: boolean = true;
  enableDataAsignation: boolean = true;
  enableDataEmpleo: boolean = true;

  valueChars: number = 700;
  doughnutChartType: string = 'doughnut';

  image:string;

  imageSection:string  = 'assets/imgs/palomaok.svg';

  userObejct: UserTO;
  options: any = { cutoutPercentage: 90, legend: { display: false, }, tooltips: { enabled: false } 
};
  public lineChartColorsTotalSection: Array<any> = [{
      backgroundColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)'],
      borderColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)'],
      hoverBackgroundColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)'],
      hoverBorderColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)'],
      pointBackgroundColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)'],
      pointBorderColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)'],
      pointHoverBackgroundColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)'],
      pointHoverBorderColor: ['rgba(214, 151, 20, 1.0)', 'rgba(214, 218, 219, 1.0)']
    }
  ];


  public lineChartColorsSection: Array<any> = [
    
    [{
      backgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      borderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)']
    }],
    [{
      backgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      borderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)']
    }],
    [{
      backgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      borderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)']
    }],
    [{
      backgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      borderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)']
    }],
    [{
      backgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      borderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)']
    }],

    [{
      backgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      borderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      hoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBackgroundColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)'],
      pointHoverBorderColor: ['rgba(52, 141, 210, 1.0)', 'rgba(255, 255, 255, 1.0)']
    }]


  ];

  constructor(public navCtrl: NavController,private ng_zon:NgZone, public loading:EventsManagerProvider,public navParams: NavParams, public storage: StorageProvider, public users: UsersProvider) {
    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    
  }

  ionViewCanEnter(){
    this.loading.setIsLoadingEvent(true);
    this.loadPonderationChart();
    return;
  }

  colorsDinamicosSecciones(ponderation: number) {
    this.lineChartColorsTotalSection.forEach(element => {

      if (ponderation < 10) {
        element.backgroundColor[0] = 'rgba(16, 153, 214, 1.0)';
        element.borderColor[0] = 'rgba(16, 153, 214, 1.0)';
        element.hoverBackgroundColor[0] = 'rgba(16, 153, 214, 1.0)';
        element.hoverBorderColor[0] = 'rgba(16, 153, 214, 1.0)';
        element.pointBackgroundColor[0] = 'rgba(16, 153, 214, 1.0)';
        element.pointBorderColor[0] = 'rgba(16, 153, 214, 1.0)';
        element.pointHoverBackgroundColor[0] = 'rgba(16, 153, 214, 1.0)';
        element.pointHoverBorderColor[0] = 'rgba(16, 153, 214, 1.0)';
        this.image = 'assets/imgs/defecto.svg';
      }

      if (ponderation < 50 && ponderation >=10) {
        element.backgroundColor[0] = 'rgba(214, 151, 20, 1.0)';
        element.borderColor[0] = 'rgba(214, 151, 20, 1.0)';
        element.hoverBackgroundColor[0] = 'rgba(214, 151, 20, 1.0)';
        element.hoverBorderColor[0] = 'rgba(214, 151, 20, 1.0)';
        element.pointBackgroundColor[0] = 'rgba(214, 151, 20, 1.0)';
        element.pointBorderColor[0] = 'rgba(214, 151, 20, 1.0)';
        element.pointHoverBackgroundColor[0] = 'rgba(214, 151, 20, 1.0)';
        element.pointHoverBorderColor[0] = 'rgba(214, 151, 20, 1.0)';
        this.image = 'assets/imgs/bronce.svg';
      }

      if (ponderation >= 50 && ponderation < 90) {
        element.backgroundColor[0] = 'rgba(138, 149, 151, 1.0)';
        element.borderColor[0] = 'rgba(138, 149, 151, 1.0)';
        element.hoverBackgroundColor[0] = 'rgba(138, 149, 151, 1.0)';
        element.hoverBorderColor[0] = 'rgba(138, 149, 151, 1.0)';
        element.pointBackgroundColor[0] = 'rgba(138, 149, 151, 1.0)';
        element.pointBorderColor[0] = 'rgba(138, 149, 151, 1.0)';
        element.pointHoverBackgroundColor[0] = 'rgba(138, 149, 151, 1.0)';
        element.pointHoverBorderColor[0] = 'rgba(138, 149, 151, 1.0)';
        this.image ='assets/imgs/plata.svg';
      }

      if (ponderation >= 90) {
        element.backgroundColor[0] = 'rgba(255, 191, 0, 1.0)';
        element.borderColor[0] = 'rgba(255, 191, 0, 1.0)';
        element.hoverBackgroundColor[0] = 'rgba(255, 191, 0, 1.0)';
        element.hoverBorderColor[0] = 'rgba(255, 191, 0, 1.0)';
        element.pointBackgroundColor[0] = 'rgba(255, 191, 0, 1.0)';
        element.pointBorderColor[0] = 'rgba(255, 191, 0, 1.0)';
        element.pointHoverBackgroundColor[0] = 'rgba(255, 191, 0, 1.0)';
        element.pointHoverBorderColor[0] = 'rgba(255, 191, 0, 1.0)';
        this.image ='assets/imgs/oro.svg';
      }

    });

  }


  loadPonderationChart() {
    this.users.getPonderation(this.userObejct.id).subscribe((data: Array<PonderationMobileTO>) => {
    let count = 0;
      data.forEach(element => {
      count++;
        if (element.nameNivel === 'Datos de Asigancion') {
          let asignacionNegativo = element.porcentajeGrafica - this.valueChars;
          this.doughnutChartDataAsignation = [element.porcentajeGrafica, asignacionNegativo];
          this.labelDataAsignation = `${element.porcentaje}%`;
          this.replaceColorOk(element.porcentaje,3);
        }

        if (element.nameNivel === 'Datos de Contratacion') {
          let contratacionNegativo = element.porcentajeGrafica - this.valueChars;
          this.doughnutChartDataContratacion = [element.porcentajeGrafica, contratacionNegativo];
          this.labelDataContratacion = `${element.porcentaje}%`;
          this. replaceColorOk(element.porcentaje,2);
        }

        if (element.nameNivel === 'Mis Datos') {
          let misDatosNegativo = element.porcentajeGrafica - this.valueChars;
          this.doughnutChartDataMisDatos = [element.porcentajeGrafica, misDatosNegativo];
          this.labelMisdatos = `${element.porcentaje}%`;
          this. replaceColorOk(element.porcentaje,0);
        }

        if (element.nameNivel === 'Paquete de Compensacion') {
          let compensacionNegativo = element.porcentajeGrafica - this.valueChars;
          this.doughnutChartDataPaquete = [element.porcentajeGrafica, compensacionNegativo];
          this.labelDataPaquete = `${element.porcentaje}%`;
          this. replaceColorOk(element.porcentaje,1);
        }
        if (element.nameNivel === 'Ultimo Empleo') {
          let empleoNegativo = element.porcentajeGrafica - this.valueChars;
          this.doughnutChartDataEmpleo = [element.porcentajeGrafica, empleoNegativo];
          this.labelDataEmpleo = `${element.porcentaje}%`;
        this.replaceColorOk(element.porcentaje,4);
        }

        if(count === 1){
          let generalNegativo = element.promedioFinalGrafica - this.valueChars;
          this.doughnutChartData = [element.promedioFinalGrafica,generalNegativo];
          this.labelGeneral = `${element.promedioFinal}%`;
          this.colorsDinamicosSecciones(element.promedioFinal);
        }

      });
      this.loading.setIsLoadingEvent(false);
    },error=>{
      console.log(error);
      this.loading.setIsLoadingEvent(false);
    });
  }


  replaceColorOk(porcentaje:number,position:number){
    if(porcentaje === 100){
      this.lineChartColorsSection[position].forEach(element => {
        element.backgroundColor[0] = 'rgba(51, 204, 0, 1.0)';
        element.borderColor[0] = 'rgba(51, 204, 0, 1.0)';
        element.hoverBackgroundColor[0] = 'rgba(51, 204, 0, 1.0)';
        element.hoverBorderColor[0] = 'rgba(51, 204, 0, 1.0)';
        element.pointBackgroundColor[0] = 'rgba(51, 204, 0, 1.0)';
        element.pointBorderColor[0] = 'rgba(51, 204, 0, 1.0)';
        element.pointHoverBackgroundColor[0] = 'rgba(51, 204, 0, 1.0)';
        element.pointHoverBorderColor[0] = 'rgba(51, 204, 0, 1.0)';
        
   });
      this.ng_zon.run(() => {
        this.enableDatos[position] = true;
      });

    }
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MyAccountPage');
  }

  packageConpensation() {
    this.navCtrl.push(CompensationPackagePage);
  }

  myData() {
    this.navCtrl.push(MyDataPage);
  }


  hirinData() {
    this.navCtrl.push(HiringDataPage);
  }

  asignationData() {
    this.navCtrl.push(AsignationDataPage);
  }

  lastJob() {
    this.navCtrl.push(LastJobPage);
  }

  goHome(){
    this.navCtrl.popToRoot();
  }

  
}
