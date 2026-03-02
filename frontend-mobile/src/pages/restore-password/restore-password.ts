import { Component } from "@angular/core";
import { NavController, NavParams, AlertController } from "ionic-angular";
import {
  FormGroup,
  FormBuilder,
  Validators
} from "../../../node_modules/@angular/forms";

import { UsersProvider } from "../../providers/users/users";
import { LoginProvider } from "../../providers/login/login";
import { UserTO } from "../../models/user.model";
import { StorageProvider } from "../../providers/storage/storage";
import { EventsManagerProvider } from "../../providers/events-manager/events-manager";
import { KEYS_STORAGE, MSG, MSG_DIALOG, EXPRESSION } from "../../environments/environments";
import { CypherProvider } from "../../providers/cypher/cypher";
import { resetPasswordTO } from "../../models/employee.compl";
import { MessageGeneral } from "../../iterface/create-account.interface";
import { HomePage } from "../home/home";


/**
 * Generated class for the RestorePasswordPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: "page-restore-password",
  templateUrl: "restore-password.html"
})
export class RestorePasswordPage {
  private todo: FormGroup;
  userObejct: UserTO;
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private formBuilder: FormBuilder,
    public users:UsersProvider,
    public login:LoginProvider,
    public storage: StorageProvider,
    public loading:EventsManagerProvider,
    public cypher:CypherProvider,
    private alertCtrl: AlertController
  ) {
   

    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.todo = this.formBuilder.group({
      contrasenaactual: ["", [Validators.required,Validators.maxLength(50)]],
      contrasenanueva: ["", [Validators.required,Validators.maxLength(50)]],
      confirmarcontrasena: ["", [Validators.required,Validators.maxLength(50)]]
    });
    
  }

  showAlert( title:string,message:string,buttons = ['Aceptar'] ){
    const alert = this.alertCtrl.create({
      title,
      message,
      buttons
    });
    alert.present()
  }

  saveContrasena(){
    this.loading.setIsLoadingEvent(true);
    let mess = new MessageGeneral();
   if(!this.valitedateEmail(this.todo.get('contrasenanueva').value) ){ 
    this.showAlert( MSG_DIALOG.ERROR_TITLE, `${MSG.INVALID_EXP}`);
    this.loading.setIsLoadingEvent(false);
    return;
    }

    

    if(this.todo.get('contrasenanueva').value === this.todo.get('confirmarcontrasena').value){
      let newPass:string = this.todo.get('contrasenanueva').value;
      let oldPass:string = this.todo.get('contrasenaactual').value;
      let body = new resetPasswordTO();
      body.idUser = this.userObejct.id;
      body.passNew=this.cypher.encryptString(newPass);
      body.passOld = this.cypher.encryptString(oldPass);
      this.login.getUserResetPass(body)
      .subscribe((data:boolean)=>{
        
        mess.title = MSG_DIALOG.OK;
        mess.msg =MSG_DIALOG.MSG_SAVE;
        if(data){
        this.loading.setGeneralNotificationMessage(mess);
        this.navCtrl.push(HomePage);
        this.loading.setIsLoadingEvent(false);
        }else{
          mess.title = MSG_DIALOG.ERROR_TITLE;
          mess.msg ='Contraseña incorrecta';
          this.loading.setGeneralNotificationMessage(mess);
          this.loading.setIsLoadingEvent(false);
        }
      },()=>{
        this.loading.setIsLoadingEvent(false);
      });    
    }else{
      mess.title = MSG_DIALOG.ERROR_TITLE;
      mess.msg ='Las contraseñas no coinciden';
      this.loading.setGeneralNotificationMessage(mess);
    }
    this.loading.setIsLoadingEvent(false);
  }

  valitedateEmail( pass:string ) {
    let regex = EXPRESSION.PWD;
    return regex.test( pass );
  }
}
