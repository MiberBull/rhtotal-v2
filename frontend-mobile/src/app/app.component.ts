import { Component, ViewChild, ChangeDetectorRef, NgZone } from '@angular/core';
import { Platform, Nav, LoadingController, Loading, MenuController, ToastController, Toast, AlertController } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { EventsManagerProvider } from "../providers/events-manager/events-manager";
import { ConsumeSaveInfoProvider } from '../providers/consume-save-info/consume-save-info';
import { LoginPage } from '../pages/login/login';
import { OnboardingPage } from '../pages/onboarding/onboarding';
import { NotificationsProvider } from '../providers/notifications/notifications';
import { LocalNotifications } from '@ionic-native/local-notifications';
import { Crashlytics } from '@ionic-native/fabric';
import { StorageProvider } from '../providers/storage/storage';
import { KEYS_STORAGE, UPDATE_DB_RHTOTAL, QUERY_DB_RHTOTAL, DELETE_DB_RHTOTAL, INSERT_DB_RHTOTAL, 
  //MESES, VARIABLES_PAGE_DISCOUNT, 
  MSG } from '../environments/environments';
  import { ScreenOrientation } from '@ionic-native/screen-orientation';
  import { NotificationTO } from '../models/notification.model';
  import { HelpPage } from '../pages/help/help';
  import { AboutUsPage } from '../pages/about-us/about-us';
  import { ViewEventProvider } from '../providers/view-event/view-event';
  import { DbStorageRhtotalProvider } from '../providers/db-storage-rhtotal/db-storage-rhtotal';
  import { Keyboard } from '@ionic-native/keyboard';
  import { MessageGeneral } from '../iterface/create-account.interface';
  import { HomePage } from '../pages/home/home';
  import { RestorePasswordPage } from '../pages/restore-password/restore-password';
  //import { PositionHistoryPage } from '../pages/position-history/position-history';
  //import { MyCvPage } from '../pages/my-cv/my-cv';
  import { CredentialPage } from '../pages/credential/credential';
  import { MyAccountPage } from '../pages/my-account/my-account';
  import { ConsumeBenefistProvider } from '../providers/consume-benefist/consume-benefist';
  import { DetailBenefisPage } from '../pages/detail-benefis/detail-benefis';
  import { Conversions } from '../util/conversions';
  import { DetailInsurancePage } from '../pages/detail-insurance/detail-insurance';
  import { UsersProvider } from '../providers/users/users';
  import { UserTO } from '../models/user.model';
  import { PonderationMobileTO, EmployeeComplementaryTO, EmployeePersonalTO } from '../models/employee.compl';
  import { MSG_DIALOG } from '../environments/environments';
  import { AppVersion } from '@ionic-native/app-version';
  




  @Component({
    templateUrl: 'app.html'
  })
  export class MyApp {
    updateEmpComplementary: EmployeeComplementaryTO = null;
    userObejct:UserTO;
    inQuestion: boolean = false;
    rootPage:any;
    loader: Loading;
    visivility:boolean= false;
    notificationsTop: NotificationTO[];
    @ViewChild(Nav) nav: Nav
    toast: Toast;
    nombreUser:string;
    base64:string;
    mensaje:string;
    pages = [
    //{ title: 'CV', component:MyCvPage },
    { title: 'Cuenta', component: MyAccountPage },
    //{ title: 'Historial de puestos', component: PositionHistoryPage },
    { title: 'Credencial', component: CredentialPage }
    ];

    pagesInfo = [
    {title: 'Ayuda', component:HelpPage},
    {title: 'Cambio de contraseña', component: RestorePasswordPage},
    {title: 'Quiénes somos', component:AboutUsPage},
    {title: 'Salir', component:LoginPage}
    ];

    tournamentList: any[];
    numNotifications: number;
    isLogged: boolean = false;

    appinfo: any[];
    constructor(platform: Platform, 
      statusBar: StatusBar, 
      private ng_zon:NgZone,
      splashScreen: SplashScreen,
      public menuEvent: ViewEventProvider,
      private eventsManager: EventsManagerProvider,
      public menuCtrl:MenuController,
      public discount :ConsumeBenefistProvider,
      public alertCtrl: AlertController,
      public loadingCtrl: LoadingController, 
      public dbRhtotal:DbStorageRhtotalProvider, 
      public saveInfo: ConsumeSaveInfoProvider,
      private notification: NotificationsProvider,
      private localNotifications: LocalNotifications,
      private crashlytics: Crashlytics,
      private screenOrientation: ScreenOrientation,
      private storage_provider: StorageProvider,
      private toastCtrl: ToastController,
      private users:UsersProvider,
      private board: Keyboard,
      public storage:StorageProvider,
      private cdRef: ChangeDetectorRef,
      private appVersion: AppVersion) {
      platform.ready().then(() => {
        // Okay, so the platform is ready and our plugins are available.
        // Here you can do any higher level native things you might need.

        //If platform is real device
        if(platform.is('cordova')){
          this.screenOrientation.lock(this.screenOrientation.ORIENTATIONS.PORTRAIT);
          this.dbRhtotal.initializeDatabase().then(() => {
            this.saveInfo.saveDataRhtotal();
          });
        }

        platform.resume.subscribe(x => {
          this.updateNotifications();
        });

        this.board.onKeyboardWillShow().subscribe( ()=>{
          this.visivility = true;     
        });
        
        this.board.onKeyboardWillHide().subscribe( ()=>{
          this.visivility = false;
        });

        this.storage_provider.firtsView().then( visit => {
          this.rootPage = visit ? LoginPage : OnboardingPage;
          statusBar.styleDefault();
          splashScreen.hide();
        });

        notification.getNumberNotifications()
        .subscribe(num => {
          this.numNotifications = num;
          this.cdRef.detectChanges();
        });

        eventsManager
        .getGeneralNotificationMessage()
        .subscribe( (resp:MessageGeneral) => {
          let alert = this.alertCtrl.create({   
            title:`<label>${resp.title}</label>`,
            subTitle:`<h6>${resp.msg}</h6>`,
            cssClass:'alertCustomCss'
          });
          alert.present();
          setTimeout( () => { alert.dismiss() },2000);
        }
        );

        eventsManager
        .getPresentToast()
        .subscribe( toast => {
          this.toast = this.toastCtrl.create({
            message: `${toast}`,
            duration: 3000,
          });
          this.toast.present();
        });

        eventsManager
        .getIsLoadingEvent()
        .subscribe(isLoading => {
          if(!this.loader) {
            this.loader = this.loadingCtrl.create({
              spinner:'crescent',
              content: `Espera por favor`
            });
          }
          if(isLoading){
            this.loader.present();
          }else{
            this.loader.dismiss();
            this.loader = null;
          }
        });

        eventsManager
        .getIsLogged()
        .subscribe(isLogged => {
          this.isLogged = isLogged;
        });

        eventsManager
        .getIsNewTokenEvent()
        .subscribe(() => {
          console.log('IsNewTOken');
          this.pushSetUp();
        });

        this.crashlytics.addLog("Starting");
        if(platform.is('ios')){
          this.crashlytics.recordError('Starting',0);
        }else{
          this.crashlytics.sendNonFatalCrash('Starting'); 
        }

        menuEvent.menuEvent().subscribe( side => {
          this.menuCtrl.toggle(side);
        });
      });

      platform.registerBackButtonAction(() => {
        let view = this.nav.getActive();
        console.log(view.component.name);
        if( view.component === HomePage ) {
          this.showLogoutAlert({title: 'Salir', component:LoginPage});
        }
        else if( view.component === LoginPage ) {
          platform.exitApp();
        }
        else{
          this.nav.pop();
        }
      });

      this.updatePerfilPhoto(); // Elimina bug del inicio
    }

    openMenuPrincipal(){
      this.menuEvent.menuOpen('left');
    }

    openNotifications(){
      this.menuEvent.menuOpen('right');
    }

    goHome() {
      let view = this.nav.getActive();
      if( view.component != HomePage ) {
        this.nav.setRoot( HomePage );
      }
    }

    openPage(page) {
      let view = this.nav.getActive();
      if( page.component === LoginPage ) {
        this.showLogoutAlert(page);
      } else if( view.component != page.component ){
        this.nav.push( page.component );
      }
    }

    pushSetUp() {
      this.updateNotifications();
      this.notification.onNotification()
      .subscribe((notification: any) => {
        console.log('Notification', JSON.stringify(notification));
        this.localNotifications.schedule({
          title: notification.title,
          text: notification.message
        });
        this.updateNotifications();
      });
    }

    setReadNotification(idNotification) {
      this.dbRhtotal.saveOrUpdate(UPDATE_DB_RHTOTAL.UPDATE_NOTIFICATION, [false, idNotification.idRepository])
      .then(result => {
        console.log('Notificacion Guardada');
        this.notificationsTop = this.notificationsTop.map(x => {
          if(x.idRepository === idNotification.idRepository) {
            x.unRead = false;
          }
          return x;
        });
        this.notification.setNumberNotifications(this.notificationsTop.filter(x => x.unRead).length);
      });
      if(idNotification.type !== 'D' && idNotification.type !== 'B' && idNotification.type !== 'I'){
        this.showConfirmAlert(idNotification.title, idNotification.description);
      }
    }

    showConfirmAlert(title, description) {
      let alert = this.alertCtrl.create({
        title:`<label>${title}</label>`,
        subTitle:`<h6>${description}</h6>`,
        buttons:[{
          text: 'Aceptar',
          role: 'cancel'
        }],
        cssClass:'alertCustomCss'
      });
      alert.present();
    }

    updateNotifications() {
      console.log('updateNotifications');
      let user = this.storage_provider.getItem( KEYS_STORAGE.USER);
      let userId = user.userType === 'IN' ? user.id : 0;
      this.notification.getNotifications(userId)
      .subscribe((notificationResult:any[]) => {
        this.dbRhtotal
        .getAll(QUERY_DB_RHTOTAL.QUERY_NOTIFICATIONS)
        .then((result: any[]) => {
          this.notificationsTop = notificationResult.map(noti => {
            let searchNotification = result.find(x => x.idRepository === noti.idRepository);
            if(searchNotification) {
              noti.unRead = searchNotification.unRead === 'true' ? true : false;
            }
            return noti;
          });
          this.saveNewNotificationInDb(this.notificationsTop);
          this.notification.setNumberNotifications(this.notificationsTop.filter(x => x.unRead).length);
        });
      });

      try{
        this.appVersion.getAppName().then(
          this.appinfo['appname'] = function(name){
            return name;
          }
          );
        this.appVersion.getPackageName().then(function(namepkg){
          this.appinfo['appnamepkg'] = function(namepkg){
            return namepkg;
          }
        });
        this.appVersion.getVersionCode().then(function(vcode){
          this.appinfo['appvcode'] = function(vcode){
            return vcode;
          }
        });
        this.appVersion.getVersionNumber().then(function(version){
          this.appinfo['appversion'] = function(version){
            return version;
          }
        });
      }catch(e){
        console.log('Info App error: ',e);
      }
    }

    saveNewNotificationInDb(notificationsToSave) {
      this.dbRhtotal.removeAll(DELETE_DB_RHTOTAL.DELETE_ALL_NOTIFICATIONS).then(() => {
        notificationsToSave.forEach(element => {
          let arr:any = [ element.idRepository,
          element.idNotification,
          element.type,
          element.subCategory,
          element.description,
          element.descriptionSmall,
          element.title,
          element.unRead,
          element.date];
          this.dbRhtotal.saveOrUpdate(INSERT_DB_RHTOTAL.INSERT_NOTIFICATION, arr).then(() => {
          });
        });
      })
    }

    getDiscountByid(notification){
      if( notification.type === 'D' ){
        this.discount.getVerifyDateNotification(notification.idNotification).subscribe((data:boolean)=>{
          if(!data){
            this.setReadNotification(notification);
            this.discount.showConfirmAlert(MSG.TITLE_BENEFIS_ALERT,MSG.CONTENT_BENEFIS_ALERT);
          }else{
            //console.log('Notificacion => ',JSON.stringify(notification));
            console.log('Content: ', JSON.stringify(notification), ' Mensaje: ', this.mensaje);

            this.discount.getLevelDiscount(notification.idNotification).subscribe(map=>{

              if(map != null){
                let contenido;
                let porcentaje;
                switch(map.level){
                  case 1:
                  porcentaje = 90;
                  this.mensaje = 'Sube a nivel oro.';
                  break;
                  case 2:
                  porcentaje = 50;
                  this.mensaje = 'Sube a nivel plata.';
                  break;
                  case 3:
                  porcentaje = 10;
                  this.mensaje = 'Sube a nivel Bronce.';
                  break;
                }
                contenido ='¡Y aprovecha esta promoción!';
                this.redirectDiscount(notification,porcentaje,this.mensaje,contenido);  
              }  
            });
          }
        });
      }

      if( notification.type === 'I' ) {
        this.discount.getVerifyDateNotificationInsurance(notification.idNotification)
        .subscribe( (data:any) => {
          if(!data.access){
            this.setReadNotification(notification);
            this.discount.showConfirmAlert(MSG.TITLE_BENEFIS_ALERT,MSG.CONTENT_BENEFIS_ALERT);
          }else{
            console.log('Notificacion => ',notification);
            this.setReadNotification(notification);
            this.menuCtrl.toggle('right');
            this.nav.push(DetailInsurancePage,{idInsurance:notification.idNotification,idInsurangeType:data.typeInsurance});
          }
        })
      }

    }

    redirectDiscount(notification,porcentaje,titulo,contenido){
      let promediofinalUserId :number;
      console.log('Redirect: ', JSON.stringify(notification));

      try{
            this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
            this.users.getPonderation(this.userObejct.id)
            .subscribe((ponderation: Array<PonderationMobileTO>)=>{

              console.log('Ponderation: ', JSON.stringify(ponderation));

              promediofinalUserId = ponderation[0].promedioFinal;
      
              if(promediofinalUserId >= porcentaje && promediofinalUserId <= 49){
                this.pushDiscount(notification);
                return;
              }
      
              if(promediofinalUserId >= porcentaje && promediofinalUserId <= 89 ){
                this.pushDiscount(notification);
                return;
              }
      
              if(promediofinalUserId >= porcentaje){
                this.pushDiscount(notification);
                return;
              }
      
              this.setReadNotification(notification);
              this.discount.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,`${titulo} ${contenido}`);   
            });
      } catch(error){
        console.log('Redirect Error: ', error);
      }

    }


    pushDiscount(notification){
      console.log('Notificacion => ',JSON.stringify(notification));
      try{
            this.setReadNotification(notification);
            this.menuCtrl.toggle('right');
            this.nav.push(DetailBenefisPage,{id:notification.idNotification});
      } catch(error){
        console.log('Error redirect: ', error);
      }
    }

    showLogoutAlert(page) {
      if(this.inQuestion){
        return;
      }
      const alert = this.alertCtrl.create({
        title: '¿Deseas cerrar sesión?',
        buttons: [
        {
          text:'NO',
          handler: () => {
            this.inQuestion = false;
          }
        },
        {
          text: 'SI',
          handler: () => {
            this.inQuestion = false;
            this.eventsManager.setIsLoadingEvent(true);
            this.notification.cancelNotificationRegister();
            let user = this.storage_provider.getItem( KEYS_STORAGE.USER );
            this.notification.invalidUserToken(user.id)
            .subscribe(() => {
              this.eventsManager.setIsLoadingEvent(false);
              this.nav.setRoot( page.component ).then( () => {
                this.storage_provider.clearCustomStorage();
              });
            });
          }
        }
        ],
        cssClass:'center-actions',
        enableBackdropDismiss:false
      });
      this.inQuestion = true;
      alert.present();
    }

    menuOpened(){
      let perfil =  this.storage_provider.getItem(KEYS_STORAGE.INFO_CRENDENTIAL);
      if(perfil.name != null){
        this.nombreUser = `${Conversions.UpperFirstLetter(perfil.name)} ${Conversions.UpperFirstLetter(perfil.lastName)} ${Conversions.UpperFirstLetter(perfil.mLastName)}`
      }else{
        this.nombreUser = '';
      }
      this.ng_zon.run(() => {
        this.base64 =perfil.imageBase64;// Obtiene la imagen del perfil
      });
    }

   /**
    * @function saveImage Cambia la imagen usando la foto del menu
    **/
    saveImage() {
      this.users.getImage().then(data => {
        this.base64 = `data:image/jpeg;base64,${data}`;
        this.updatePerfilPhoto();
      });
    }

   /**
    * @function updatePerfilPhoto Actualiza la foto del perfil en el storage asi como en la base de datos
    * @return {boolean} isUpdate falso o verdadero segun sea el caso del exito en la actualización
    **/
    updatePerfilPhoto(): boolean{
      let isUpdate: boolean = false;
      
      try{
        let perfil =  this.storage_provider.getItem(KEYS_STORAGE.INFO_CRENDENTIAL); // Obtener credenciales
        
        this.obtenerComplementos(perfil.numberEmployee); // Obtener Complementos mediante el id en las credenciales
        this.updateEmpComplementary.photography = this.base64; // se cambia la imagen por la obtenida en el evento de seleccionar foto
        
        let personalInformation = new EmployeePersonalTO();
        personalInformation.complementary = this.updateEmpComplementary; // Se actualiza el complemento para la foto
        
        this.saveEmployeePerson(personalInformation); // se envia a actualizar la foto a base de datos
        isUpdate = true;

      }catch(e){
        isUpdate = false;
        console.log(e);
      }


      return isUpdate;
    }

   /**
    * @function obtenerComplementos Obtiene los complementos con el id del usuario
    * @param {any} idUsuario  id de usuario o numero de empleado
    **/
    obtenerComplementos(idUsuario) {
      // se genera la suscripción para obtener los datos complementarios del empleado 
      this.users.getEmployeeById(idUsuario).subscribe(
        (data: EmployeeComplementaryTO) => {
          this.eventsManager.setIsLoadingEvent(true);
          // Si trae Datos actualiza el complemento
          if(data != null && data.employee != null && data.employee.user != null){
            this.updateEmpComplementary = data; // Complemento Actualizado
            this.eventsManager.setIsLoadingEvent(false);
          }else{
            // Si no encuentra empleado
            this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.BUSQUEDA);
            this.eventsManager.setIsLoadingEvent(false);
          }
        },
        () => {
          this.eventsManager.setIsLoadingEvent(false);
        }
        );
    }
   /**
    * @function SetViewNotify Set View and open links
    * @param {NotificationTO} NotificationTo modelo de notificacción
    **/
     SetViewNotify(notification){       
       console.log('SetViewNotify: ', notification);
       this.setReadNotification(notification);
       console.log('Get Info from notify');
       this.getDiscountByid(notification);
       console.log('End SetViewNotify');
     }

   /**
    * @function saveEmployeePerson guarda en  base de datos los datos del empleado
    * @param {EmployeePersonalTO} employeePersonal modelo de empleado
    **/
    saveEmployeePerson(employeePersonal:EmployeePersonalTO){
      // Se crea la subscripción para hacer el update de la información del emepleado en base de datos con el proveedor
      this.users.saveEmployeComplementary(employeePersonal).subscribe(()=>{
        this.updatePerfil(employeePersonal.complementary); // genera el update en el store si el evento de guardado en base de datos es exitoso
      },error =>{
        this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
      });
    }

   /**
    * @function updatePerfil actualiza los datos guardados en el storage
    * @param {EmployeeComplementaryTO} perfilNew datos actualizados en base de datos
    **/
    updatePerfil(perfilNew :EmployeeComplementaryTO){
      let perfil =  this.storage.getItem(KEYS_STORAGE.INFO_CRENDENTIAL); // Obtener storeage por medio de las credenciales
      perfil.imageBase64 = perfilNew.photography; // se actualiza unicamente la imagen
      this.storage.saveItem(KEYS_STORAGE.INFO_CRENDENTIAL,perfil); // se vuelve a guardar el storage con la nueva imagen
    }

    /**
     * @function version()
     **/
     version(){
       console.log('###############');
       console.log(JSON.stringify(this.appinfo));
       console.log('###############');
     }

  }



