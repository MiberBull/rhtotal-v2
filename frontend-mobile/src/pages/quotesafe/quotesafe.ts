import { Component } from '@angular/core';
import { NavController, NavParams, AlertController } from 'ionic-angular';
import { MSG_DIALOG,
  TEXT_STATIC_CUOTES, 
  KEYS_STORAGE, 
  SEGURO,  
  SEGUROS,
  EMAIL_SEGURO,
  IMG_MAIL_HEADER } from '../../environments/environments';
import { StorageProvider } from '../../providers/storage/storage';
import { UserTO } from '../../models/user.model';
import { EmailGeneric } from '../../models/emailgenric.model';
import { EmailServices } from '../../providers/email-services/email-services';
import { UsersProvider } from "../../providers/users/users";
import { EmployeeComplementaryTO } from "../../models/employee.compl";


/**
 * Generated class for the QuotesafePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

 @Component({
   selector: 'page-quotesafe',
   templateUrl: 'quotesafe.html',
 })
 export class QuotesafePage {
   seguros: any[] = SEGURO; // Se agregan tipos de seguros desde environments
   seguroSeleccionado: string = '';
   tipoSeguro:any;
   textSeguro:any = [];
   userObejct:UserTO;

   imgEmail: string = '';
   email_seguro: string = ''; //Emails obtenidos desde base
   template: string = ''; // Template que se usara para el envio de correos

   data:any = {};
   nombres: any = '';
   datosEmpleado: EmployeeComplementaryTO = null; // Datos del empleado
   phone: string = ''; // User Phone
   email: string = ''; // User Email
   errorMessage: string = 'NA';
   plainbody: string = '';

   emails_auto: string = '';
   emails_hogar: string = '';
   emails_mascota: string = '';
   emails_vida: string ='';
   


   constructor(public navCtrl: NavController, 
     public alertCtrl: AlertController, 
     public navParams: NavParams, 
     public storage:StorageProvider, 
     private email_provider: EmailServices,
     private user: UsersProvider,) {
     this.textSeguro = TEXT_STATIC_CUOTES.TEXT_CONTENT.split('#');
     this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
     /** User Email and Phone number set **/
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
     this.email_provider.getEmailsToSend(SEGUROS.AUTO).subscribe( (res:any) => { this.emails_auto = res; });
     this.email_provider.getEmailsToSend(SEGUROS.HOGAR).subscribe( (res:any) => { this.emails_hogar = res; });
     this.email_provider.getEmailsToSend(SEGUROS.MASCOTA).subscribe( (res:any) => { this.emails_mascota = res; });
     this.email_provider.getEmailsToSend(SEGUROS.VIDA).subscribe( (res:any) => { this.emails_vida = res; });
     this.email_provider.getEmailsToSend(EMAIL_SEGURO).subscribe( (res:any) => { this.email_seguro = res; });

     this.email_provider.getEmailsToSend(IMG_MAIL_HEADER).subscribe( (res:any) => { this.imgEmail = res; }); // get image

   }

   backDiscount(){
     this.navCtrl.pop();
   }

   opendialog() {
     if(this.seguroSeleccionado != '' && this.seguroSeleccionado != SEGURO[0].name){
       let alert = this.alertCtrl.create({
         title:`<label>${MSG_DIALOG.QUOTE_SAFE_TITLE}</label>`,
         subTitle:`<h6>${MSG_DIALOG.QUOTE_SAFE_CONTENT}</h6>`,
         enableBackdropDismiss:false,
         buttons:[
         {
           text: 'Aceptar',
           handler: () => {
             this.generateTemplate();
             this.sendMail(this.email_seguro, `Solicitud de Seguro ${this.seguroSeleccionado}`, this.plainbody);// envio de mail 

             this.mensajeInformativo('Solicitud Enviada', `Solicitud de Seguro ${this.seguroSeleccionado} enviada`, true);
           },
         },
         {
           text: 'Cancelar',
           handler: () => {
             console.log("Se cancelo el envio del correo.");
           },
         }
         ],
         cssClass:'alertCustomCss'
       });
       alert.present();
     } else {
       this.mensajeInformativo("Opción no valida", "Seleccione una opción Valida", false);
     }
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
    * @function getSelection Obtiene el valor del seguro solicitado.
    * @return {number} Regresa el valor numerico de la seleccion del seguro
    **/
    public getSelection(): number { 
      let item: number = this.tipoSeguro;
      this.seguroSeleccionado = SEGURO[this.tipoSeguro].name;

      //console.log('MAILS: '+ this.email_seguro, this.emails_auto, this.emails_hogar, this.emails_mascota);

      switch (this.tipoSeguro) {
        case '1': //Auto
        this.email_seguro = this.emails_auto;  
        break;
        case '2': // Hogar
        this.email_seguro = this.emails_hogar;
        break;
        case '3': // Vida
        this.email_seguro = this.emails_vida;
        break;
        case '4': // Mascota
        this.email_seguro = this.emails_mascota;
        default:        
        console.log('Default: ' + this.email_seguro);
        break;
      }

      console.log("Tipo de seguro: " + item + " mail: " + this.email_seguro);
      return item;

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
              this.backDiscount();
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
      this.plainbody = `${this.seguroSeleccionado}|${this.email}|${perfil.name}|${perfil.lastName}|${perfil.mLastName}|${this.phone}|${this.errorMessage}`;
      
      this.template = "<head><title>Solicitud de " + this.seguroSeleccionado + 
      "</title></head><table style='width:100%;border-collapse:collapse' border='0' cellspacing='0' cellpadding='0'>" +
      "<tbody><tr style='font-size:14px;color:#545f75'><td>&nbsp;</td>" +
      "<td style='width:600px'><table style='width:100%;border-collapse:collapse;background-color:#ffffff' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td><div style='padding:0px 0;text-align:center;background-color:#ededed'> <img src='"+this.imgEmail+"' alt='RHTotal' width='100%!important'></div> <div style='line-height:150%;font-family:Arial,Verdana'>" +
      "<div style='padding:60px 50px'><p style='margin:0 0 45px'>&#161;Hola&#33;</p><p style='margin:20px 0'>El usuario con los datos a continuaci&#243;n listados ha solicitado un Seguro <b>" + this.seguroSeleccionado + "</b>.</p>" +
      "<p style='margin:20px 0'><br><b>Email: " + this.email +
      "<br>Nombre: " + perfil.name +
      "<br>Apellido Paterno: " + perfil.lastName +
      "<br>Apellido Materno: " + perfil.mLastName +
      "<br>Tel&#233;fono: " + this.phone + 
      "<br><b>" + this.errorMessage + "</b>" +
      "<br></b><br></p><div style='margin:45px 0;text-align:center'><p style='margin:0;line-height:150%'>Atentamente:<br> RH Total</p></div></div></div></td></tr></tbody></table></td>"+
      "<td>&nbsp;</td></tr><tr style='color:white;text-align:center'><td>&nbsp;</td><td><div style='color:#172032;line-height:1.5;margin:25px 0;font-size:14px'>&copy; 2018 RH Total. Derechos reservados.</div></td><td>&nbsp;</td></tr></tbody></table>"

    }
  }
