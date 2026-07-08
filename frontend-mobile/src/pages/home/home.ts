import {
  Component,
  ViewChild,
  //ChangeDetectorRef,
  // NgZone
} from '@angular/core';
import {
  Platform,
  NavController,
  MenuController,
  Nav,
  Slides,
  AlertController,
} from 'ionic-angular';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { ConsumeApiProvider } from '../../providers/consume-api/consume-api';
import { MyAccountPage } from '../my-account/my-account';
import { ViewEventProvider } from '../../providers/view-event/view-event';
import { ImagesApiProvider } from '../../providers/images-api/images-api';
import { BenefitsPage } from '../benefits/benefits';
import { RhPage } from '../rh/rh';
import { AsistenciaPage } from '../asistencia/asistencia';
import { MisVacacionesPage } from '../mis-vacaciones/mis-vacaciones';
import { MisTicketsPage } from '../mis-tickets/mis-tickets';
import { MisEncuestasPage } from '../mis-encuestas/mis-encuestas';
import { StorageProvider } from '../../providers/storage/storage';
import { TYPE_USER, KEYS_STORAGE } from '../../environments/environments';
//import { Observable } from 'rxjs';
import { SplashScreen } from '@ionic-native/splash-screen';

import { EmployeeComplementaryTO } from '../../models/employee.compl';
import { UsersProvider } from '../../providers/users/users';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {
  @ViewChild('nav') nav: Nav;
  @ViewChild('slides') public slides: Slides;

  slidesArray: any[];

  myAccount: MyAccountPage;
  images: string[] = [];
  imagesAsync: any;
  autoplay: any;
  user: any;
  benefitsIntern = 'assets/icon/beneficios.svg';
  rhIntern = 'assets/icon/rh_gris.svg';
  arrowIntern = 'assets/icon/arrow_gris.svg';
  userType: string = '';

  pages: Array<{ title: string; component: any }>;
  pagesInfo: Array<{ title: string; component: any }>;
  tournamentList: any[];

  email: string = ''; // User Email
  datosEmpleado: EmployeeComplementaryTO = null; // Datos del empleado

  constructor(
    public navCtrl: NavController,
    public menuCtrl: MenuController,
    public menuEvent: ViewEventProvider,
    public eventsManager: EventsManagerProvider,
    private imagesApi: ImagesApiProvider,
    public consumeApi: ConsumeApiProvider,
    //private ref: ChangeDetectorRef,
    private storage_provider: StorageProvider,
    //private ng_zone: NgZone,
    private splashScreen: SplashScreen,
    public alertCtrl: AlertController,
    private users: UsersProvider,
    public platform: Platform
  ) {
    this.platform = platform;
    //this.imagesAsync = this.imagesApi.getImagesForHome();

    this.user = this.storage_provider.getUser();
    this.userType = this.user.userType;
    if (this.userType == TYPE_USER.IN) {
      this.rhIntern = 'assets/icon/rh.svg';
      this.arrowIntern = 'assets/icon/arrow.svg';
    } else {
      this.rhIntern = 'assets/icon/rh_gris.svg';
      this.arrowIntern = 'assets/icon/arrow_gris.svg';
    }
  }

  ionViewDidEnter() {
    if ((<any>window).cordova) {
      this.splashScreen.hide();
    }
    this.blockService();
  }

  ionViewDidLoad() {
    const imagesStorage = this.storage_provider.getItem(KEYS_STORAGE.IMAGES_BANNER);
    if (imagesStorage) {
      this.slidesArray = imagesStorage;
      this.slides.update();
    } else {
      this.imagesAsync = this.imagesApi.getImagesForHome().subscribe(
        (resp: any[]) => {
          this.slidesArray = [];
          this.slidesArray = resp;
          this.slides.update();
          this.storage_provider.saveItem(KEYS_STORAGE.IMAGES_BANNER, resp);
          //this.ref.detectChanges();
        },
        (error) => {
          this.slidesArray = [];
          const defaultImage = ['assets/imgs/default.png'];
          this.slidesArray = defaultImage;
          this.slides.update();
          //this.ref.detectChanges();
          console.log(JSON.stringify(error));
        }
      );
    }
  }

  showLoading() {
    this.eventsManager.setIsLoadingEvent(true);
    //timeout simula una operacion que espera un tiempo
    setTimeout(() => {
      this.eventsManager.setIsLoadingEvent(false);
    }, 3000);
  }

  getInfoApi() {
    this.eventsManager.setIsLoadingEvent(true);
    this.consumeApi.getTournaments().subscribe((result) => {
      this.tournamentList = result;
      this.eventsManager.setIsLoadingEvent(false);
    });
  }

  goSection(section: string) {
    switch (section) {
      case 'benefits':
        this.navCtrl.push(BenefitsPage);
        break;
      case 'rh':
        if (TYPE_USER.IN === this.userType) {
          this.navCtrl.push(RhPage);
        }
        break;
      case 'asistencia':
        if (TYPE_USER.IN === this.userType) {
          this.navCtrl.push(AsistenciaPage);
        }
        break;
      case 'vacaciones':
        if (TYPE_USER.IN === this.userType) {
          this.navCtrl.push(MisVacacionesPage);
        }
        break;
      case 'tickets':
        if (TYPE_USER.IN === this.userType) {
          this.navCtrl.push(MisTicketsPage);
        }
        break;
      case 'encuestas':
        if (TYPE_USER.IN === this.userType) {
          this.navCtrl.push(MisEncuestasPage);
        }
        break;
    }
  }

  getBackgroundStyle(image) {
    return {
      'background-image': `url('${image}')`,
    };
  }

  doRefresh(refresher) {
    this.imagesApi.getImagesForHome().subscribe(
      (resp: any[]) => {
        this.slidesArray = [];
        this.slidesArray = resp;
        this.slides.update();
        this.storage_provider.saveItem(KEYS_STORAGE.IMAGES_BANNER, resp);
        refresher.complete();
        //this.ref.detectChanges();
      },
      (error) => {
        console.log(JSON.stringify(error));
        this.slidesArray = [];
        const defaultImage = ['assets/imgs/default.png'];
        this.slidesArray = defaultImage;
        this.slides.update();
        refresher.complete();
        //this.ref.detectChanges();
      }
    );
  }

  showALert(title, subTitle) {
    const alert = this.alertCtrl.create({
      title,
      subTitle,
      buttons: [
        {
          text: 'OK',
          handler: () => {
            try {
              alert.dismiss();
              this.platform.exitApp();
              console.log('cerrando app');
            } catch (e) {
              console.log('error al cerrar: ', e);
            }
          },
        },
      ],
      enableBackdropDismiss: false, // just press ok
      cssClass: 'alertonfirmCustomCss',
    });
    alert.present();

    alert.onDidDismiss((res) => {
      console.log('on did dimiss ');
    });

    alert.onWillDismiss((res) => {
      console.log('on will dimiss ');
    });
  }

  showALertIOS(title, subTitle) {
    const alert = this.alertCtrl.create({
      title,
      subTitle,
      enableBackdropDismiss: false, // cant close
      cssClass: 'alertonfirmCustomCss',
    });
    alert.present();

    alert.onDidDismiss((res) => {
      console.log('on did dimiss ');
    });

    alert.onWillDismiss((res) => {
      console.log('on will dimiss ');
    });
  }

  blockService() {
    this.email = this.storage_provider.getItem(KEYS_STORAGE.USER)['email'].toLocaleLowerCase();

    this.users.employeesByProject('INNOHUB').subscribe((data) => {
      const list = data.employee;
      const isBlackList = list
        .toString()
        .toLocaleLowerCase()
        .trim()
        .includes(this.email.toString().toLocaleLowerCase());
      //console.log('isBlackList: ', isBlackList);

      if (this.email === undefined || this.email === '') {
        console.log('Email vacio');
        return;
      }

      if (isBlackList) {
        if (this.platform.is('ios')) {
          this.showALertIOS(
            'FUERA DE SERVICIO',
            'RH Total está fuera de servicio y en caso de requerirse comunicarse con contactobeneficios@intelplan.mx'
          );
        } else if (this.platform.is('android')) {
          this.showALert(
            'FUERA DE SERVICIO',
            'RH Total está fuera de servicio y en caso de requerirse comunicarse con contactobeneficios@intelplan.mx'
          );
        } else {
          this.showALertIOS(
            'FUERA DE SERVICIO',
            'RH Total está fuera de servicio y en caso de requerirse comunicarse con contactobeneficios@intelplan.mx'
          );
        }
      }
    });
  }
}
