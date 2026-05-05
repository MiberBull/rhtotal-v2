import { Component } from "@angular/core";
import {
  NavController,
  NavParams,
  AlertController
} from "ionic-angular";
import { LoginProvider } from "../../providers/login/login";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import {
  EXPRESSION,
  MSG,
  BTN,
  MSG_DIALOG,
  PARAMETER,
  EMAIL_SOPORTE_APP,
  IMG_MAIL_HEADER
} from "../../environments/environments";
import { NewAccountTO } from "../../models/user.model";
import { CypherProvider } from "../../providers/cypher/cypher";
import { LoginPage } from "../login/login";
import { EventsManagerProvider } from "../../providers/events-manager/events-manager";
import { ConsumeSaveInfoProvider } from "../../providers/consume-save-info/consume-save-info";
import { EmailServices } from '../../providers/email-services/email-services';
import { EmailGeneric } from '../../models/emailgenric.model';

/**
 * Generated class for the CreateAccountPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

 @Component({
   selector: "page-create-account",
   templateUrl: "create-account.html"
 })
 export class CreateAccountPage {
   accountForm: FormGroup;
   MSG: any;
   BTN: any;
   code: number;
   load: any;
   codeConfirm: boolean = false;
   showInputCode: boolean = false;
   check: boolean = false;
   okEnable: boolean = false;
   okConfirmCode: boolean = true;
   emails_news: any = {}; // Destiny mails for notification
   imgEmail: string = '';
   template: string = 'Prueba de creación de usuario nuevo'; // Template que se usara para el envio de correos

   constructor(
     public navCtrl: NavController,
     public navParams: NavParams,
     private login_provider: LoginProvider,
     private alertCtrl: AlertController,
     public loading: EventsManagerProvider,
     private fb: FormBuilder,
     private cypher: CypherProvider,
     private modal:ConsumeSaveInfoProvider,
     private email_provider: EmailServices
     ) {
     this.MSG = MSG;
     this.BTN = BTN;
     this.accountForm = this.fb.group({
       email: [
       "",
       [Validators.required, Validators.email, Validators.maxLength(50)]
       ],
       access: ["", [Validators.required, Validators.maxLength(50)]],
       accessConfirmed: ["", [Validators.required, Validators.maxLength(50)]]
     });

     this.email_provider.getEmailsToSend(EMAIL_SOPORTE_APP).subscribe( (res:any) => { this.emails_news = res; });
     this.email_provider.getEmailsToSend(IMG_MAIL_HEADER).subscribe( (res:any) => { this.imgEmail = res; }); // get image
   }

   ionViewDidLoad() {}

   backLogin() {
     this.navCtrl.pop();
   }

   onSubmit() {
     let valuesForm = this.accountForm.getRawValue();

     if (!this.valitedateEmail(valuesForm.email)) {
       this.showAlert(
         MSG_DIALOG.ERROR_TITLE,
         "El correo electrónico no es válido, ingrésalo nuevamente"
         );
       return;
     }

     let pwd: any = this.valdiatePasswords(
       valuesForm.access,
       valuesForm.accessConfirmed
       );
     if (!pwd.ok) {
       this.showAlert(MSG_DIALOG.ERROR_TITLE, pwd.msg);
       return;
     }

     if (
       this.accountForm.controls["access"].value.length < 8 ||
       this.accountForm.controls["accessConfirmed"].value.length < 8
       ) {
       this.showAlert(
         MSG_DIALOG.ERROR_TITLE,
         "La contraseña debe de tener mas de 8 caracteres"
         );
   }

   if (!this.check) {
     this.showAlert(
       MSG_DIALOG.ERROR_TITLE,
       "Acepta los Términos y Condiciones para continuar"
       );
     return;
   }
   let account: NewAccountTO = new NewAccountTO();
   account.setUser(valuesForm.email.toLowerCase());
   account.setPassword(this.cypher.encryptString(valuesForm.access));
   account.setPasswordConfirmed(
     this.cypher.encryptString(valuesForm.accessConfirmed)
     );
   console.log(account);

   this.createAccountService(account);
 }

 valdiatePasswords(acces: string, accesConfirmed: string) {
   let regex = EXPRESSION.PWD;
   if (acces != accesConfirmed)
     return { ok: false, msg: "Las contraseñas no coinciden" };
   if (!regex.test(accesConfirmed)) return { ok: false, msg: MSG.INVALID_EXP };
   return { ok: true, msg: null };
 }

 valitedateEmail(email: string) {
   let regex = EXPRESSION.EMAIL;
   return regex.test(email);
 }

 showAlert(title: string, message: string, buttons = ["Aceptar"]) {
   const alert = this.alertCtrl.create({
     title,
     message,
     buttons
   });
   alert.present();
 }

 createAccountService(newAccount: NewAccountTO) {
   this.loading.setIsLoadingEvent(true);
   this.login_provider.createAccount(newAccount).subscribe(
     (x: any) => {
       this.showAlert(
         x.title,
         "Un código de confirmación se ha enviado a tu correo electrónico, captúralo para terminar tu registro",
         ["OK"]
         );
       this.showInputCode = true;
       this.okEnable = true;
       this.loading.setIsLoadingEvent(false);
     },
     error => {
       this.codeConfirm = false;
       this.showAlert(
         MSG_DIALOG.ERROR_TITLE,error.error.message === "" || error.error.message === "GENERAL" || error.error.message === undefined?MSG_DIALOG.ERROR_SERVICE :error.error.message);        this.loading.setIsLoadingEvent(false);
     },
     () => {
       this.codeConfirm = true;
     }
     );
 }

 confirmCodeService(code: string) {
   let userCode = new NewAccountTO();
   userCode.user = this.accountForm.get("email").value.toLowerCase();
   userCode.code = code;

   if (!this.check) {
     this.showAlert(
       MSG_DIALOG.ERROR_TITLE,
       "Acepta los Términos y Condiciones para continuar"
       );
     return;
   }

   this.loading.setIsLoadingEvent(true);

   this.login_provider.confirmCode(userCode).subscribe(
     () => {
       this.showAlert(
         "¡Felicidades!",
         " Has creado tu cuenta, ingresa y descubre todas las posibilidades"
         );
       /** Add mail notify of new user **/
       this.notificateNewUser(userCode.getUser());

       this.navCtrl.setRoot(LoginPage);
       this.loading.setIsLoadingEvent(false);
     },
     error => {
       this.showAlert(
         MSG_DIALOG.ERROR_TITLE,error.error.message === "" || error.error.message === "GENERAL" || error.error.message === undefined?MSG_DIALOG.ERROR_SERVICE :error.error.message);
       this.loading.setIsLoadingEvent(false);
     }
     );
 }

 /**
  * @function notificateNewUser
  * @param {any} user
  **/
  notificateNewUser(user: any){
    this.template = "<head><title>Nuevo usuario" +
        "</title></head><table style='width:100%;border-collapse:collapse' border='0' cellspacing='0' cellpadding='0'>" +
        "<tbody><tr style='font-size:14px;color:#545f75'><td>&nbsp;</td>" +
        "<td style='width:600px'><table style='width:100%;border-collapse:collapse;background-color:#ffffff' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td><div style='padding:0px 0;text-align:center;background-color:#ededed'> <img src='"+this.imgEmail+"' alt='RHTotal' width='100%!important'></div> <div style='line-height:150%;font-family:Arial,Verdana'>" +
        "<div style='padding:60px 50px'><p style='margin:0 0 45px'>&#161;Hola&#33;</p><p style='margin:20px 0'>Se ha creado un nuevo usuario:.</p>" +
        "<p style='margin:20px 0'><br><b>Usuario:  :email:" +
        "<br></b><br></p><div style='margin:45px 0;text-align:center'><p style='margin:0;line-height:150%'>Atentamente:<br> RH Total</p></div></div></div></td></tr></tbody></table></td>" +
        "<td>&nbsp;</td></tr><tr style='color:white;text-align:center'><td>&nbsp;</td><td><div style='color:#172032;line-height:1.5;margin:25px 0;font-size:14px'>&copy; 2018 RH Total. Derechos reservados.</div></td><td>&nbsp;</td></tr></tbody></table>"
        ;
   this.template = this.template.replace(':email:', user);
   this.sendMail(this.emails_news, 'Nuevo Usuario Creado', this.template);
  }


 validaLongitud() {
   if (this.code.toString().length > 3) {
     this.okConfirmCode = false;
   }
   if (this.code.toString().length <= 3) {
     this.okConfirmCode = true;
   }
 }


 showModalTerms() {
   this.modal.getParameter(PARAMETER.CONDITIONS).subscribe(data=>{

     this.NoticeOfPrivacy(data.dsValue);
   });
 }



 NoticeOfPrivacy(bodyTerminos:string){
   let alert = this.alertCtrl.create({
     title:`<div>Términos y Condiciones</div>`,
     message:`<h6>${bodyTerminos}</h6>`,
     buttons:[
     {

       text: 'Aceptar',
       handler: () => {}
     }],
     cssClass:'alertTeminos-general'
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

 }
