import { Component } from '@angular/core';
import { NavController, NavParams, Loading } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserTO, LoginTO } from '../../models/user.model';
import { LoginProvider } from '../../providers/login/login';
import { CreateAccountPage } from '../create-account/create-account';
import { EXPRESSION, KEYS_STORAGE, LINKS, MSG_DIALOG } from '../../environments/environments';
import { HomePage } from '../home/home';
import { StorageProvider } from '../../providers/storage/storage';
import { NotificationsProvider } from '../../providers/notifications/notifications';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { InAppBrowser } from '@ionic-native/in-app-browser';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { ImagesApiProvider } from '../../providers/images-api/images-api';
import { UsersProvider } from '../../providers/users/users';
import { SplashScreen } from '@ionic-native/splash-screen';
import { EmailGeneric } from '../../models/emailgenric.model';
import { EmailServices } from '../../providers/email-services/email-services';
import { TenantProvider } from '../../providers/tenant/tenant';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {
  loginForm: FormGroup;
  loading: Loading;
  intents: number;

  template: string = ''; // Template que se usara para el envio de correos

  constructor(
    public navCtrl: NavController,
    private localProvider: StorageProvider,
    public navParams: NavParams,
    private _loginProvider: LoginProvider,
    private user_provider: UsersProvider,
    private storage_provider: StorageProvider,
    private fb: FormBuilder,
    private notification: NotificationsProvider,
    private eventManager: EventsManagerProvider,
    private iab: InAppBrowser,
    private imagesApi: ImagesApiProvider,
    private splashScreen: SplashScreen,
    private email_provider: EmailServices,
    private tenantProvider: TenantProvider
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]],
    });

    this._loginProvider.getParameter('numberIntent').subscribe((resp: any) => {
      this.intents = resp;
      this._loginProvider.setNumberIntent(resp);
    });
  }

  ionViewCanEnter() {
    if (this.storage_provider.getItem(KEYS_STORAGE.USER) != null) {
      this.onLoginReopen();
    }
  }

  ionViewDidEnter() {
    if ((<any>window).cordova) {
      this.splashScreen.hide();
    }
  }

  ionViewDidLoad() {
    this.eventManager.setIsLogged(false);
  }

  private resolveTenant(email: string): string {
    const domain = (email.split('@')[1] || '').toLowerCase();
    if (domain === 'dch.mx' || domain.endsWith('dchkw.mx')) return 'demo-corp';
    if (domain.includes('aeisa')) return 'aeisa';
    if (domain.includes('rga')) return 'rga';
    if (domain.includes('staffing')) return 'staffing';
    return 'demo-corp';
  }

  onLogin() {
    this.eventManager.setIsLoadingEvent(true);
    if (this.validateEmail(this.loginForm.value.email)) {
      const email = this.loginForm.value.email;
      const tenantId = this.resolveTenant(email);
      localStorage.setItem('tenantId', tenantId);

      const userTO: UserTO = this.loginForm.getRawValue();
      const loginTO: LoginTO = new LoginTO();
      loginTO.user = userTO;
      loginTO.user.email = loginTO.user.email.toLocaleLowerCase();
      this._loginProvider.login(loginTO).subscribe(
        (resp) => {
          this.optionsResponse(resp);
        },
        (error) => {
          this.eventManager.setIsLoadingEvent(false);
          this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
        }
      );
    } else {
      this.eventManager.setIsLoadingEvent(true);
    }
  }

  optionsResponse(options: LoginTO) {
    switch (options.flag) {
      case 0:
        this.localProvider.saveItem(KEYS_STORAGE.USER, options.user);
        if ((<any>window).cordova) {
          this.notification.configToken();
          this.notification.onRegistration().subscribe((registration: any) => {
            console.log('Registration', registration.registrationId);
            this.notification.setNotificationToken(registration.registrationId);
            this.notification.setUserToken(options.user.id).subscribe(() => {
              this.getDataPerfil(options.user.id);
              this.getImagesForUser();
            });
          });
        } else {
          // Browser mode: skip push notifications, navigate directly
          this.getDataPerfil(options.user.id);
          this.getImagesForUser();
        }
        this.eventManager.setIsLoadingEvent(false);
        break;
      case 1:
        this.showAlert(MSG_DIALOG.ERROR_TITLE, options.message);
        break;
      case 2:
        if (this.decreaseIntents() <= 0) {
          this.blockUser(options);
        } else {
          this.showAlert(MSG_DIALOG.ERROR_TITLE, options.message);
        }
        break;
      case 3:
        this.showAlert(MSG_DIALOG.ERROR_TITLE, options.message);
        break;
      case 4:
        this.showAlert('Usuario', 'Nuevo usuario');
        break;
    }
  }

  blockUser(reposne: LoginTO) {
    const userBlock: UserTO = new UserTO();
    userBlock.email = this.loginForm.value.email;
    userBlock.password = this.loginForm.value.password;

    reposne.block = true;
    reposne.user = userBlock;

    this._loginProvider.blockingUserByIntents(reposne).subscribe(
      (result: any) => {
        this.intents = this._loginProvider.getNumberIntent();
        this.showAlert(MSG_DIALOG.ERROR_TITLE, result.message);
      },
      (error) => {
        this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
      }
    );
  }

  resetIntents() {
    if (this.validateEmail(this.loginForm.value.email)) {
      this.intents = this._loginProvider.getNumberIntent();
    }
  }

  validateEmail(email: string) {
    const emailRegex = EXPRESSION.EMAIL;
    return emailRegex.test(email);
  }

  decreaseIntents() {
    return (this.intents -= 1);
  }

  showAlert(title: string, subTitle: string, buttons = ['Aceptar']) {
    this.eventManager.setIsLoadingEvent(false);
    const message = new MessageGeneral();
    message.msg = subTitle;
    message.title = title;
    this.eventManager.setGeneralNotificationMessage(message);
  }

  goCreateAccount() {
    this.navCtrl.push(CreateAccountPage);
  }

  goRecoveryPassword() {
    this.iab.create(LINKS.RECOVERY_PWD).show();
  }

  getImagesForUser() {
    this.imagesApi.getImagesForHome().subscribe(
      (resp: any[]) => {
        if (resp.length > 0) {
          this.localProvider.saveItem(KEYS_STORAGE.IMAGES_BANNER, resp);
          this.eventManager.setIsNewTokenEvent(true);
          this.navCtrl.setRoot(HomePage);
          this.eventManager.setIsLoadingEvent(false);
          this.eventManager.setIsLogged(true);
        } else {
          const images = ['assets/imgs/default.png'];
          this.localProvider.saveItem(KEYS_STORAGE.IMAGES_BANNER, images);
          this.eventManager.setIsNewTokenEvent(true);
          this.navCtrl.setRoot(HomePage);
          this.eventManager.setIsLoadingEvent(false);
          this.eventManager.setIsLogged(true);
        }
      },
      (error) => {
        const images = ['assets/imgs/default.png'];
        this.localProvider.saveItem(KEYS_STORAGE.IMAGES_BANNER, images);
        this.eventManager.setIsNewTokenEvent(true);
        this.navCtrl.setRoot(HomePage);
        this.eventManager.setIsLoadingEvent(false);
        this.eventManager.setIsLogged(true);
      }
    );
  }

  getDataPerfil(id: number) {
    this.user_provider.getInfoCredential(id).subscribe(
      (data) => {
        // Patch temporaly for users with ñ

        data.lastName = data.lastName === 'ZUNIGA' ? 'ZUÑIGA' : data.lastName;
        data.lastMName = data.lastMName === 'ZUNIGA' ? 'ZUÑIGA' : data.lastMName;
        data.lastName = data.lastName === 'YANEZ' ? 'YAÑEZ' : data.lastName;
        data.lastMName = data.lastMName === 'YANEZ' ? 'YAÑEZ' : data.lastMName;
        data.lastName = data.lastName === 'CASTANON' ? 'CASTAÑON' : data.lastName;
        data.lastMName = data.lastMName === 'CASTANON' ? 'CASTAÑON' : data.lastMName;

        this.storage_provider.saveItem(KEYS_STORAGE.INFO_CRENDENTIAL, data);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  onLoginReopen() {
    this.eventManager.setIsLoadingEvent(true);
    const userTO: UserTO = this.loginForm.getRawValue();
    const loginTO: LoginTO = new LoginTO();
    loginTO.user = this.storage_provider.getItem(KEYS_STORAGE.USER);
    loginTO.user.email = this.storage_provider
      .getItem(KEYS_STORAGE.USER)
      ['email'].toLocaleLowerCase();
    loginTO.flag = 0;
    loginTO.block = false;

    if ((<any>window).cordova) {
      this.notification.configToken();
      this.notification.onRegistration().subscribe((registration: any) => {
        console.log('Registration', registration.registrationId);
        this.notification.setNotificationToken(registration.registrationId);
        this.notification.setUserToken(loginTO.user.id).subscribe(() => {
          this.getDataPerfil(loginTO.user.id);
          this.getImagesForUserReload();
        });
      });
    } else {
      this.getDataPerfil(loginTO.user.id);
      this.getImagesForUserReload();
    }
    this.eventManager.setIsLoadingEvent(false);
  }

  getImagesForUserReload() {
    this.imagesApi.getImagesForHomeReload().subscribe(
      (resp: any[]) => {
        if (resp.length > 0) {
          this.localProvider.saveItem(KEYS_STORAGE.IMAGES_BANNER, resp);
          this.eventManager.setIsNewTokenEvent(true);
          this.navCtrl.setRoot(HomePage);
          this.eventManager.setIsLoadingEvent(false);
          this.eventManager.setIsLogged(true);
        } else {
          const images = ['assets/imgs/default.png'];
          this.localProvider.saveItem(KEYS_STORAGE.IMAGES_BANNER, images);
          this.eventManager.setIsNewTokenEvent(true);
          this.navCtrl.setRoot(HomePage);
          this.eventManager.setIsLoadingEvent(false);
          this.eventManager.setIsLogged(true);
        }
      },
      (error) => {
        const images = ['assets/imgs/default.png'];
        this.localProvider.saveItem(KEYS_STORAGE.IMAGES_BANNER, images);
        this.eventManager.setIsNewTokenEvent(true);
        this.navCtrl.setRoot(HomePage);
        this.eventManager.setIsLoadingEvent(false);
        this.eventManager.setIsLogged(true);
      }
    );
  }

  /**
   * @function sendMail enviar correo electronico
   * @param {string} destiny corredo destino
   **/
  sendMail(destiny: string, subject: string, body: string) {
    const emailbody = new EmailGeneric();
    emailbody.to = destiny.toString();
    emailbody.subject = subject.toString();
    emailbody.body = body.toString();
    emailbody.html = this.template;
    this.email_provider.sendEmail(emailbody);
  }
}
