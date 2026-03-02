import { Component, ViewChild, QueryList } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '../../../node_modules/@angular/forms';
import { CompensationPackageTO } from '../../models/employee.compl';
import { UserTO } from '../../models/user.model';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { UsersProvider } from '../../providers/users/users';
import { StorageProvider } from '../../providers/storage/storage';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { BrMaskerIonic3 } from '../../../node_modules/brmasker-ionic-3';

/**
 * Generated class for the SalaryPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-salary',
  templateUrl: 'salary.html',
  providers: [BrMaskerIonic3]
})


export class SalaryPage {
  @ViewChild('inputToFocus') inputToFocus :QueryList<any> ;
  private todo: FormGroup;
  public updatepackage :Array<CompensationPackageTO> = null;
  userObejct: UserTO;
  msg = new MessageGeneral();
  constructor(public navCtrl: NavController, public navParams: NavParams,private formBuilder: FormBuilder,
    public users: UsersProvider,
    public storage: StorageProvider,
    public loading:EventsManagerProvider) {
      this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
      this.todo = this.formBuilder.group({
        Sueldo_bruto_mensual:["",[Validators.minLength(1)]],
        Ingreso_mensual_bruto_integrado:['',[Validators.minLength(1)]],
        Ingreso_anual_bruto_estimado:['',[Validators.minLength(1)]]
      });
  }

  ionViewWillEnter(){
   
    this.getpackage();
  }

  saveSalary() {
    if(this.updatepackage != null){
      this.updatepackage.forEach(element => {
        for (const key in this.todo.controls) {
          if(element.dsName === key){
            let value = this.todo.get(key).value;
            element.valor  = value;
            element.lastUserModifier = this.userObejct.email;
          }
        }
      });
      this.saveOrUpdate(this.updatepackage);
      return;
    }


    let savePackages = new Array<CompensationPackageTO>();
    for (const key in this.todo.controls) {
      let compensation = new CompensationPackageTO();
      compensation.idCompetation = null;
      compensation.idUser = this.userObejct.id;
      compensation.dsName = key;
      let value = this.todo.get(key).value;
      compensation.valor = value;
      compensation.dsEmail = this.userObejct.email;
      compensation.lastUserModifier = this.userObejct.email;
      compensation.lastModification = null;
      compensation.creationUser = this.userObejct.email;
      compensation.creationDate = null;
      compensation.active = true;
      savePackages.push(compensation);
      
    }
    console.log(savePackages);
    this.saveOrUpdate(savePackages);
  }


saveOrUpdate(update :Array<CompensationPackageTO>){
  if(this.todo.valid){
    this.loading.setIsLoadingEvent(true);
    this.users.saveConpesationPackage(update).subscribe(data=>{
      if(data){
        this.msg.title = MSG_DIALOG.OK;
        this.msg.msg = MSG_DIALOG.MSG_SAVE;
        this.loading.setGeneralNotificationMessage(this.msg);
        this.loading.setIsLoadingEvent(false);
        setTimeout(()=>{
          this.navCtrl.parent.select(0);
        },2100);
      }else{
        this.loading.setIsLoadingEvent(false);
        this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
      }
    },error=>{
      console.log(error);
      this.loading.setIsLoadingEvent(false);
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
    });
  return;
  }
  this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.DATA_ERROR);
}


  getpackage(){
    this.loading.setIsLoadingEvent(true);
    this.users.getCompensationPackage(this.userObejct.id).subscribe((data:Array<CompensationPackageTO>)=>{
      this.updatepackage = new Array<CompensationPackageTO>();
      data.forEach(element => {
          for (const key in this.todo.controls) {
            if(element.dsName === key){
            this.updatepackage.push(element);
            this.todo.controls[key].setValue(element.valor);
            }
          }
        });
        this.loading.setIsLoadingEvent(false);
        this.updatepackage = this.updatepackage.length > 0 ?this.updatepackage : null;

      },error=>{
        console.log(error);
        this.loading.setIsLoadingEvent(false);
      });
  }

  
}
