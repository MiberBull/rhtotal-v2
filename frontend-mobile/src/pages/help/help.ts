import { Component } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import { ConsumeHelpProvider } from '../../providers/consume-help/consume-help';
import { ARRAYPARAM, TIPOS_SOPORTES, EMAIL_SOPORTE_APP, KEYS_STORAGE, IMG_MAIL_HEADER } from '../../environments/environments';
import { EmailGeneric } from '../../models/emailgenric.model';
import { EmailServices } from '../../providers/email-services/email-services';
import { StorageProvider } from '../../providers/storage/storage';
import { EmployeeComplementaryTO } from "../../models/employee.compl";
import { UsersProvider } from "../../providers/users/users";


/**
 * Generated class for the HelpPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

 @Component({
   selector: 'page-help',
   templateUrl: 'help.html',
 })
 export class HelpPage {
   arrayInfo:string [] = [];
   supportType:any;
   supports: any[] = TIPOS_SOPORTES;
   selectedSupport:  string = ''
   subject: string = '';
   supportText: string = '';
   email_soporte_app: string ='';
   template: string = ''; // Template que se usara para el envio de correos
   email: string = ''; // User Email
   phone: string = ''; // User Phone
   imgEmail: string = '';
   nombres: string = '';
   datosEmpleado: EmployeeComplementaryTO = null; // Datos del empleado
   errorMessage: string = '';
   plainbody: string = '';
   isValidText: Boolean = false;
   isDisable: Boolean = false;//true;
   styleValidator: string = '';
   textValidator: string = '';


   constructor(public navCtrl: NavController, 
     public navParams: NavParams,
     public consumeHelp:ConsumeHelpProvider,
     private email_provider: EmailServices,
     public storage:StorageProvider,
     private user: UsersProvider,
     public alertCtrl: AlertController) {

     this.styleValidator = `red`
     this.textValidator = 'Necesitamos conocer más información.';

     this.consumeHelp.getParameter(ARRAYPARAM[1]).then(data => {
       this.arrayInfo =data[0].value.split('#');
     }).catch(()=> console.log('error al visualizar'));

     let perfil = this.storage.getItem(KEYS_STORAGE.INFO_CRENDENTIAL); // Credenciales del usuario firmado
     this.nombres = perfil.name;


     this.user.getEmployeeById(perfil.numberEmployee).subscribe(
       (data: EmployeeComplementaryTO) => {
         this.datosEmpleado = data; // Obtenemos la información del empleado susbscribiendo al provedor por el id
         this.phone = this.datosEmpleado.phone === undefined ? 'Telefono no disponible' : this.datosEmpleado.phone.toString();
         this.email = this.datosEmpleado.email === undefined ? 'Correo Electronico no disponible' : this.datosEmpleado.email.toString();

       },
       error => {
         this.errorMessage = "Algunos datos personales no fueron posibles obtener...<br> ";
       }, 
       ()=>{        
         console.log('Complete: getEmployeeById');
       });

     /** GET MAILS **/
     this.email_provider.getEmailsToSend(EMAIL_SOPORTE_APP).subscribe( (res:any) => { this.email_soporte_app = res; });
     this.email_provider.getEmailsToSend(IMG_MAIL_HEADER).subscribe( (res:any) => { this.imgEmail = res; }); // get image
   }

   ionViewDidLoad() {
     console.log('ionViewDidLoad HelpPage');
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

   getSupportSelected(){
     let item: number = this.supportType;
     this.selectedSupport = TIPOS_SOPORTES[this.supportType].name;

     this.subject = `Solicitud de Soporte ${this.selectedSupport}`;

     console.log("Tipo de Soporte: " + item + " mail: " + this.subject);
     return item;
   }

   sendSupport(){
     console.log(this.supportText, this.supportText.length, this.isValidText);
     this.generateTemplate();
     try{
       if(this.selectedSupport != '' && this.selectedSupport != TIPOS_SOPORTES[0].name){
         if(this.isValidText){         
           this.sendMail(this.email_soporte_app, this.subject, this.plainbody);// envio de mail 
           this.mensajeInformativo("Enviado", "Su mensaje ha sido enviado exitosamente, en breve le atenderemos.", true);
         } else {
           this.mensajeInformativo("Texto invalido", "Porfavor ingrese una descripción de más de 20 caractéres", false);
         }
       } else {
         this.mensajeInformativo("Opción no valida", "Seleccione una opción Valida", false);
       }
     }catch(error){
       console.log('error send support: ', error);
     }
   }


   backhome() {
     this.navCtrl.popToRoot();
   }

   /**
    * @function wordCount crea un mensaje para informar de un evento (debug)
    * @param {string} titulo  Titulo del mensaje
    * @param {string} mensaje Mensaje a desplegar
    **/
    wordCount() {
      if(this.supportText.length >= 20){
        this.isValidText = true;
        this.isDisable = false;
        this.styleValidator = `green`;
         this.textValidator = '¡Excelente puedes enviar tu mensaje!'
      } else {
        this.isValidText = false;
        this.isDisable = false; //true;
        this.styleValidator = `red`;
        this.textValidator = 'Necesitamos conocer más información.';
      }
      //console.log(this.styleValidator);
    }


   /**
    * @function mensajeInformativo crea un mensaje para informar de un evento (debug)
    * @param {string} titulo  Titulo del mensaje
    * @param {string} mensaje Mensaje a desplegar
    **/
    mensajeInformativo(titulo, mensaje, salir: boolean) {
      let alert = this.alertCtrl.create({
        title:`<label>${titulo}</label>`,
        subTitle:`<h6>${mensaje}</h6>`,
        enableBackdropDismiss:false,
        buttons:[
        {
          text: 'ACEPTAR',
          handler: () => {

            if(salir){
              console.log("Se ha dado aceptar");
              this.backhome();
            }
          }
        }
        ],
        cssClass:'alertCustomCss'
      });
      alert.present();
    }   
    /** 
    * @function generateTemplate Genera el template para envio de correos con datos del anticipo
    * @param {string} template
    **/
    generateTemplate() {
      let perfil = this.storage.getItem(KEYS_STORAGE.INFO_CRENDENTIAL); // Credenciales del usuario firmado
      this.plainbody = `${this.nombres} | ${this.email} | ${this.phone}`;

      this.template = "<head><title>Solicitud de " + this.getSupportSelected + 
      "</title></head><table style='width:100%;border-collapse:collapse' border='0' cellspacing='0' cellpadding='0'>" +
      "<tbody><tr style='font-size:14px;color:#545f75'><td>&nbsp;</td>" +
      "<td style='width:600px'><table style='width:100%;border-collapse:collapse;background-color:#ffffff' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td><div style='padding:0px 0;text-align:center;background-color:#ededed'> <img src='"+this.imgEmail+"' alt='RHTotal' width='100%!important'></div> <div style='line-height:150%;font-family:Arial,Verdana'>" +
      "<div style='padding:60px 50px'><p style='margin:0 0 45px'>&#161;Hola&#33;</p><p style='margin:20px 0'>El usuario con los datos a continuaci&#243;n listados ha solicitado Soporte con la aplicación relacionado a  <b>" + this.selectedSupport + "</b>.</p>" +
      "<p style='margin:20px 0'><br><b>Email: " + this.email +
      "<br>Nombre: " + perfil.name +
      "<br>Apellido Paterno: " + perfil.lastName +
      "<br>Apellido Materno: " + perfil.mLastName +
      "<br>Tel&#233;fono: " + this.phone + 
      "<br><b>" +
      "<br><b>Mensaje:</b>" +
      "<br><b>" +
      "<br><i>" + this.supportText + "</i>" +
      "<br><b>" +
      "<br><b>" + this.errorMessage + "</b>" +
      "<br></b><br></p><div style='margin:45px 0;text-align:center'><p style='margin:0;line-height:150%'>Atentamente:<br> RH Total</p></div></div></div></td></tr></tbody></table></td>"+
      "<td>&nbsp;</td></tr><tr style='color:white;text-align:center'><td>&nbsp;</td><td><div style='color:#172032;line-height:1.5;margin:25px 0;font-size:14px'>&copy; 2018 RH Total. Derechos reservados.</div></td><td>&nbsp;</td></tr></tbody></table>"

    }


  }
