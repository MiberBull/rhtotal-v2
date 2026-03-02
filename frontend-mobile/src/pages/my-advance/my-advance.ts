import { Component, NgZone } from '@angular/core';
import { NavController, NavParams, AlertController, Platform } from 'ionic-angular';
import { PaysheetProvider } from '../../providers/paysheet/paysheet';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { Conversions } from '../../util/conversions';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { MSG_LOAN_BLOCK, MSG_DIALOG, EMAIL_LOANS, KEYS_STORAGE, EMAIL_SUPPORT, IMG_MAIL_HEADER, TEMPLATES_FINTECH } from '../../environments/environments';
import { EmailGeneric } from '../../models/emailgenric.model';
import { BankInfo } from '../../models/bankinfo.model';
import { StorageProvider } from "../../providers/storage/storage";
import { UsersProvider } from "../../providers/users/users";
import { EmployeeComplementaryTO } from "../../models/employee.compl";
import { MathPrecision } from "../../util/math-precision";
import { EmailServices } from '../../providers/email-services/email-services';
import { Subscription } from 'rxjs/Subscription';
import { ServiceValidator } from '../../providers/service-validator/service-validator'
import * as moment from 'moment';

/**
 * Generated class for the MyAdvancePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

 @Component({
   selector: 'page-my-advance',
   templateUrl: 'my-advance.html',
 })
 export class MyAdvancePage {
   private checkData: Subscription;

   emails_loans: any = {}; // Destiny mails for loans
   emails_support: any = {}; // Destiny mails for support
   user_bank_data:BankInfo = null; // User data bank
   phone: string = ''; // User Phone
   email: string = ''; // User Email
   messageLoan: string = ''; // error message
   messageSupport: string = '';

   tipoAnticipo: string = "Mí Adelanto"; // Tipo de anticipo
   template: string = ''; // Template que se usara para el correo
   datosEmpleado: EmployeeComplementaryTO = null; // Datos del empleado

   data:any = {}; 
   correspondingAmount:any;
   acomulatedAmount:any;
   wayToPay:any;
   nextPayment:any;
   nextPaymentAmount:any;
   commission:any;
   requestedAmount:any;
   namePlatform:string;
   porcentage:number = 0;
   percentage_selected:string;

   company:any;
   imgEmail: string = '';
   plainbody: string = '';

   rfc: any = '';
   curp: any = '';
   birthDate: any;
   nombres: any = '';

   constructor(
     public navCtrl: NavController, 
     public navParams: NavParams,
     private events_provider: EventsManagerProvider,
     private alertCtrl: AlertController,
     private paysheet_provier: PaysheetProvider,
     private ng_zone: NgZone,
     private platform: Platform,
     private storage: StorageProvider, // Obtener datos
     private user: UsersProvider,
     private email_provider: EmailServices,
     private serviceValidator: ServiceValidator
     ) {
     this.namePlatform = this.platform.is('ios') ? 'ios' : 'android';
     console.log(this.namePlatform);
     this.serviceValidator.checkServiceStatus();

     this.data = this.navParams.get("data");
     this.selectPorcentage(0);

     //this.company = this.data.paysheetEmployee.nomina[0].nombreCliente === undefined ? "No encontrado" : this.data.paysheetEmployee.nomina[0].nombreCliente;
     /** Email for cases **/
     this.email_provider.getEmailsToSend(EMAIL_LOANS).subscribe( (res:any) => { this.emails_loans = res; });
     this.email_provider.getEmailsToSend(EMAIL_SUPPORT).subscribe( (res:any) => { this.emails_support = res; });
     this.email_provider.getEmailsToSend(IMG_MAIL_HEADER).subscribe( (res:any) => { this.imgEmail = res; }); // get image

     /** User Email and Phone number set **/
     let perfil = this.storage.getItem(KEYS_STORAGE.INFO_CRENDENTIAL); // Credenciales del usuario firmado
     this.nombres = perfil.name;

     this.user.getEmployeeById(perfil.numberEmployee).subscribe(
       (data: EmployeeComplementaryTO) => {
         this.datosEmpleado = data; // Obtenemos la información del empleado susbscribiendo al provedor por el id
         this.phone = this.datosEmpleado.phone === undefined ? 'Telefono no disponible' : this.datosEmpleado.phone.toString();
         this.email = this.datosEmpleado.email === undefined ? 'Correo Electronico no disponible' : this.datosEmpleado.email.toString();

         this.curp = this.datosEmpleado.curp;
         this.rfc = this.datosEmpleado.rfc;
         this.birthDate = moment(this.datosEmpleado.birthDate).format('DD/MM/YYYY');
       },
       error => {
         this.messageLoan = "Algunos datos personales no fueron posibles obtener...<br> ";
       }, 
       ()=>{        
         console.log('Complete: getEmployeeById');
       });

     /** User Bank information data set **/
     this.user.getBankInfoByEmployeeId(perfil.numberEmployee).subscribe( (res: any) => {
       this.user_bank_data = res;
       this.user_bank_data.ds_bank = res.ds_bank === undefined ? 'Nombre Banco no disponible' : res.ds_bank.toString();
       this.user_bank_data.ds_bank_account = res.ds_bank_account === undefined ? 'Cuenta no disponible' : res.ds_bank_account.toString();
       this.user_bank_data.ds_clabe = res.ds_clabe === undefined ? 'CLABE no disponible' : res.ds_clabe.toString();
     },
     error => {
       this.messageSupport = "Los datos bancarios no fueron posibles obtener...<br> ";
     }, 
     () => {
       console.log('Complete: Get Bank Info From DB.', this.user_bank_data);
     });

   }

   ionViewDidLoad() {
     console.log('ionViewDidLoad MyAdvancePage');
     this.serviceValidator.checkServiceStatus();
   }

   // Star service on enter page to constantly detect the status of network
   ionViewDidEnter(){
     if(!this.serviceValidator.isActiveService){
       this.showALert(this.serviceValidator.titleDeactivated, this.serviceValidator.messageDeactivated);
     }
   }

   backDiscount() {
     this.navCtrl.pop();
   }

   backhome() {
     this.navCtrl.popToRoot();
   }
   
  /**
   * @function getBankAndPay get from sico the bank data an payment operator
   **/
   getBankAndPay(){
     this.checkData = this.user.getEmployeeInfoSico(this.curp, this.nombres, this.birthDate, this.rfc).subscribe(
       (res: any) => {
         this.user_bank_data.ds_bank = res.data.personal.datosBancarios.nombreBanco === undefined ? 'Dato no disponible' : res.data.personal.datosBancarios.nombreBanco;
         this.user_bank_data.ds_bank_account = res.data.personal.datosBancarios.cuentaBancaria === undefined ? 'Dato no disponible' : res.data.personal.datosBancarios.cuentaBancaria;
         this.user_bank_data.ds_card = res.data.personal.datosBancarios.numeroTarjeta === undefined ? 'Dato no disponible' : res.data.personal.datosBancarios.numeroTarjeta;
         this.user_bank_data.ds_clabe = res.data.personal.datosBancarios.cuentaCLABE === undefined ? 'Dato no disponible' : res.data.personal.datosBancarios.cuentaCLABE;
         this.company = res.data.nomina[0].pagadora.nombrePagadoraSA === undefined ? 'Dato no disponible' : res.data.nomina[0].pagadora.nombrePagadoraSA;
       }, 
       error => {
         this.messageSupport = this.messageSupport + "Los datos Bancarios y Pagadora no fueron posibles obtener...<br> "
       }, 
       () => {
         console.log('Done extract bank info');
         console.log(this.user_bank_data, this.company);
       });
   }

   selectPorcentage( position:number ) {
     if( !this.data.paymentProperties[position] ) {
       let message = new MessageGeneral();
       message.msg = MSG_LOAN_BLOCK[position];
       message.title = MSG_DIALOG.ERROR_TITLE;
       this.events_provider.setGeneralNotificationMessage(message);
       return;
     }
     this.porcentage = position;
     this.ng_zone.run( () => {
       this.correspondingAmount = this.data.paymentProperties[position].correspondingAmount;
       this.acomulatedAmount = this.data.paymentProperties[position].acomulatedAmount;
       this.nextPaymentAmount = this.data.paymentProperties[position].nextAmount;
       this.wayToPay = Conversions.UpperFirstLetter(this.data.paysheetEmployee.nomina[0].periodo);
       this.nextPayment = this.dateFormat(this.data.paymentProperties[position].siguientePago);
       this.commission = this.data.paymentProperties[position].commission;
       this.requestedAmount = this.data.paymentProperties[position].commisiones.montoDeposito; 
     });

     switch(this.porcentage){
       case 0:
       this.percentage_selected = '20%';
       break;
       case 1:
       this.percentage_selected = '40%';
       break;
       case 2:
       this.percentage_selected = '60%';
       case 3:
       this.percentage_selected = '80%';
       break;
     }
   }

   showConfirmAlert() {

     this.getBankAndPay();
     let alert = this.alertCtrl.create({
       title:`<label>Recibirás en tu cuenta en menos de 120 minutos</label>`,
       subTitle:`<h4>$${this.requestedAmount}</h4><h6>*Comisión del ${this.commission}% IVA incluido sobre el monto acumulado.</h6>`,
       buttons:[{
         text: 'Cancelar',
         role: 'cancel'
       },
       {
         text: 'Aceptar',
         handler: () => {
           let loanPropierties = this.data.paymentProperties[this.porcentage];
           delete this.data.paymentProperties;
           this.data.paymentProperties = [];
           this.data.paymentProperties.push(loanPropierties);
           this.events_provider.setIsLoadingEvent(true);
           this.paysheet_provier.loanMyAdvanced(this.data).subscribe( () => {
             this.events_provider.setIsLoadingEvent(false);                         
             try{
               /** Template for loans request **/
               this.generateTemplate(TEMPLATES_FINTECH.REQUEST); // generamos el template con los datos del anticipo
               this.sendMail(this.emails_loans, `Solicitud de ${this.tipoAnticipo}`, this.plainbody);// envio de mail por cada correo 

               /** Template for support help **/
               this.generateTemplate(TEMPLATES_FINTECH.SUPPORT); // generamos el template con los datos del anticipo
               this.sendMail(this.emails_support, `Apoyo dispersiones de ${this.tipoAnticipo}`, this.plainbody);// envio de mail por cada correo 
             } catch(e){
               console.log('Error Envio Mail ', e);
             }
             this.showConfirmRequest();
           },error => {
             this.events_provider.setIsLoadingEvent(false);

             let titulo: string;
             let content: string;

             if(error.status === 500){
               content = error.error.message;
             }else {
               content = MSG_DIALOG.ERROR_SERVICE;
             }

             titulo = MSG_DIALOG.ERROR_TITLE;
             console.log('Error on paysheetprovider: ', error);
             this.showALert(titulo, content);

           },() => this.events_provider.setIsLoadingEvent(false));
         }
       }],
       cssClass:'alertCustomCss'
     });
     alert.present();
   }

   showConfirmRequest() {
     let alert = this.alertCtrl.create({
       title:`<label>¡Hemos recibido tu solicitud!</label>`,
       subTitle:`<h6>Recibirás una notificación para conocer el resultado de tu solicitud.</h6>`,
       buttons:[
       {
         text: 'Aceptar',
         handler: () => {
           this.navCtrl.pop();          
         }
       }],
       cssClass:'alertCustomCss',
       enableBackdropDismiss:false
     });
     alert.present();
   }

   dateFormat(date:string) {
     let info = date.split('-');
     return `${info[2]}/${info[1]}/${info[0]}`;
   }

   showInfoSwap() {
     this.events_provider.setIsLoadingEvent(true);
     this.paysheet_provier.clickedInfo().subscribe( () => {
       this.paysheet_provier.showAlertInfoSwap(this.namePlatform);
     },error => {
       this.events_provider.setIsLoadingEvent(false);
     },() => {
       this.events_provider.setIsLoadingEvent(false);
     });
   }

   showALert(title, subTitle) {
     let alert = this.alertCtrl.create({
       title,
       subTitle,
       enableBackdropDismiss : false, // cant close
       buttons: [
       {
         text: 'ACEPTAR',
         handler: () => {
           alert.dismiss();
           if(!this.serviceValidator.isActiveService){
             this.backhome();
           }
         }
       }],
       cssClass: 'alertonfirmCustomCss'
     });
     alert.present();
   }

  /**
   * @function sendMail enviar correo electronico
   * @param {string} destiny corredo destino
   **/
   sendMail(destiny: string, subject: string, body: string){
     let emailbody = new EmailGeneric();
     emailbody.to = destiny.toString();
     emailbody.subject = subject.toString();
     emailbody.body = body.toString();
     emailbody.html = this.template;
     this.email_provider.sendEmail(emailbody);
   }

   /** 
    * @function generateTemplate Genera el template para envio de correos con datos del anticipo
    * @param {string} template
    **/
    generateTemplate(template) {
      let perfil = this.storage.getItem(KEYS_STORAGE.INFO_CRENDENTIAL); // Credenciales del usuario firmado
      let commission_ammount = MathPrecision.round(this.acomulatedAmount - this.requestedAmount, 2);
      
      this.plainbody = `${this.tipoAnticipo}|${this.email}|${perfil.name}|${perfil.lastName}|${perfil.mLastName}|${this.user_bank_data.ds_bank}|${this.user_bank_data.ds_bank_account}|${this.user_bank_data.ds_clabe}|${this.acomulatedAmount}|${this.requestedAmount}|${this.commission}|${commission_ammount}|${this.company}`;
      console.log(this.plainbody);
      switch(template) {
        case TEMPLATES_FINTECH.REQUEST:        
        this.template = "<head><title>Solicitud de " + this.tipoAnticipo + 
        "</title></head><table style='width:100%;border-collapse:collapse' border='0' cellspacing='0' cellpadding='0'>" +
        "<tbody><tr style='font-size:14px;color:#545f75'><td>&nbsp;</td>" +
        "<td style='width:600px'><table style='width:100%;border-collapse:collapse;background-color:#ffffff' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td><div style='padding:0px 0;text-align:center;background-color:#ededed'> <img src='"+this.imgEmail+"' alt='RHTotal' width='100%!important'></div> <div style='line-height:150%;font-family:Arial,Verdana'>" +
        "<div style='padding:60px 50px'><p style='margin:0 0 45px'>&#161;Hola&#33;</p><p style='margin:20px 0'>El usuario con los datos a continuaci&#243;n listados ha solicitado un " + this.tipoAnticipo + ".</p>" +
        "<p style='margin:20px 0'><br><b>Email: " + this.email +
        "<br>Nombre: " + perfil.name +
        "<br>Apellido Paterno: " + perfil.lastName +
        "<br>Apellido Materno: " + perfil.mLastName +
        "<br>Tel&#233;fono: " + this.phone + 
        "<br>Porcentaje Solicitado: " + this.percentage_selected +
        "<br>Total solicitado: " + this.acomulatedAmount + 
        "<br><b>Total a Depositar: $</b> " + this.requestedAmount + 
        "<br>Comisi&#243;n:  (%</b> " + this.commission + ") $ " + commission_ammount + 
        "<br><b>" + this.messageLoan + "</b>" +
        "<br></b><br></p><div style='margin:45px 0;text-align:center'><p style='margin:0;line-height:150%'>Atentamente:<br> RH Total</p></div></div></div></td></tr></tbody></table></td>"+
        "<td>&nbsp;</td></tr><tr style='color:white;text-align:center'><td>&nbsp;</td><td><div style='color:#172032;line-height:1.5;margin:25px 0;font-size:14px'>&copy; 2018 RH Total. Derechos reservados.</div></td><td>&nbsp;</td></tr></tbody></table>"
        break;
        case TEMPLATES_FINTECH.SUPPORT:
        let account_info = this.user_bank_data.ds_clabe === undefined ? 
        "<br><b>CUENTA BANCARIA:</b> " + this.user_bank_data.ds_bank_account : 
        "<br><b>CUENTA CLABE:</b> " + this.user_bank_data.ds_clabe;
        

        this.template = "<head><title>Disperion de " + this.tipoAnticipo + 
        "</title></head><table style='width:100%;border-collapse:collapse' border='0' cellspacing='0' cellpadding='0'>" +
        "<tbody><tr style='font-size:14px;color:#545f75'><td>&nbsp;</td>" +
        "<td style='width:600px'><table style='width:100%;border-collapse:collapse;background-color:#ffffff' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td><div style='padding:0px 0;text-align:center;background-color:#ededed'> <img src='"+this.imgEmail+"' alt='RHTotal' width='100%!important'></div> <div style='line-height:150%;font-family:Arial,Verdana'>" +
        "<div style='padding:60px 50px'><p style='margin:0 0 45px'>Buen d&iacute;a Equipo,</p><p style='margin:20px 0'>Solicitamos su apoyo con las siguientes dispersiones por concepto de Solicitud de Pr&eacute;stamo (" + this.tipoAnticipo + ").</p>" +
        "<br><b>Nombre: " + perfil.name + " " + perfil.lastName + " " + perfil.mLastName +
        "<br><b>BANCO:</b> " + this.user_bank_data.ds_bank + 
        account_info  + 
        "<br>Porcentaje Solicitado: " + this.percentage_selected +
        "<br><b>Monto Solicitado $</b> " + this.acomulatedAmount +
        "<br><b>Total a Depositar: $</b> " + this.requestedAmount + 
        "<br><b>Comisi&#243;n: (%</b> " + this.commission + ") $ " + commission_ammount +

        "<br><b>Empresa Pagadora:</b> " + this.company +
        "<br><b>Concepto del pago:</b> Otros pagos por transferencia" +
        "<br><b>" + this.messageSupport + "</b>" +

        "<br><br><br>Gracias por su atenci&oacute;n y apoyo." +
        "<br></b><br></p><div style='margin:45px 0;text-align:center'><p style='margin:0;line-height:150%'>Atentamente:<br> Equipo RHTotal</p></div></div></div></td></tr></tbody></table></td>"+
        "<td>&nbsp;</td></tr><tr style='color:white;text-align:center'><td>&nbsp;</td><td><div style='color:#172032;line-height:1.5;margin:25px 0;font-size:14px'>&copy; 2018 RH Total. Derechos reservados.</div></td><td>&nbsp;</td></tr></tbody></table>"
        break;
      }
      this.checkData.unsubscribe();
    }


  }
