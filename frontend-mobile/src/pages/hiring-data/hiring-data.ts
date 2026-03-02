import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '../../../node_modules/@angular/forms';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { UsersProvider } from '../../providers/users/users';
import { UserTO } from '../../models/user.model';
import { StorageProvider } from '../../providers/storage/storage';
import { ContratingDataTO, EmployeeComplementaryTO } from '../../models/employee.compl';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { MyAccountPage } from '../my-account/my-account';
import { BrMaskerIonic3 } from 'brmasker-ionic-3';

/**
 * Generated class for the HiringDataPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-hiring-data',
  templateUrl: 'hiring-data.html',
  providers: [BrMaskerIonic3]
})
export class HiringDataPage {
  private todo: FormGroup;
  userObejct: UserTO;
  salaryEnable: boolean = false;
  jobEnable: boolean = false;
  msg = new MessageGeneral();
  contratingData: ContratingDataTO = null;
  constructor(public navCtrl: NavController, public storage: StorageProvider , private formBuilder: FormBuilder
    , public navParams: NavParams, public users: UsersProvider,public loading:EventsManagerProvider) {
    this.loading.setIsLoadingEvent(true);
    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.todo = this.formBuilder.group({
      qtSalary: ['', [ Validators.minLength(1)]],
      dsArea: ['', [Validators.maxLength(50), Validators.min(1)]],
      job: ['', [Validators.maxLength(50), Validators.min(1)]],
      endOfContract: ['']
    });
    this.validateTypeUSers();
   this.geContratingDataRHTotal();
  }



geContratingDataRHTotal(){
  this.users.getContratingByIdUser(this.userObejct.id).subscribe((data:ContratingDataTO) =>{
    this.contratingData = new ContratingDataTO();
    this.loadFormInput(data);
    this.contratingData = data;
    this.loading.setIsLoadingEvent(false);
  },error=>{
    this.getEployeName();
  console.log(error);
  });

}

getSicoContratingData(curp:string, name:string){
  name = name.replace(' ', '+'); // Patch for complex names
  this.users.getWorkInfoSico(curp,name).subscribe(data=>{
  let contrect = new ContratingDataTO();
  contrect.qtSalary = data.data.pago[0].salarioRealPercibidoMes
  contrect.job = data.data.personal.puesto
  this.loadFormInput(contrect);
  },error=>{
    console.log(error);
  });
}

  loadFormInput(contract: ContratingDataTO) {
    for (const key in this.todo.controls) {
      for (const prop in contract) {
        if(prop === 'qtSalary'  && key ==='qtSalary' ){
          let value :any = contract[prop];
          this.todo.controls[key].setValue(value === 0 ?'':contract[prop]);
        }else{
          if(key === prop){
            this.todo.controls[key].setValue(contract[prop]);
          }
      }
    }
  }
  }
getEployeName(){
  this.users.getEmployeeById(this.userObejct.id).subscribe((data:EmployeeComplementaryTO)=>{
   let nameCompouse:string = data.employee.name.trim().replace(' ','+');

    this.getSicoContratingData(data.curp,nameCompouse); 
    this.loading.setIsLoadingEvent(false);
  },error=>{
    console.log(error);
    this.loading.setIsLoadingEvent(false);
  });
}

getInfoForm(){

if(this.contratingData != null){
  this.contratingData.qtSalary = this.todo.get('qtSalary').value;
  this.contratingData.dsArea =  this.todo.get('dsArea').value;
  this.contratingData.cv =  null;
  this.contratingData.job = this.todo.get('job').value;
  this.contratingData.contract = null;
  this.contratingData.endOfContract = new Date(this.todo.get('endOfContract').value);
  this.saveContratingData(this.contratingData);
  return;
}

  let contract = new ContratingDataTO();
  contract.idUser =this.userObejct.id;
  contract.skill = 1;
  contract.qtSalary = this.todo.get('qtSalary').value;
  contract.dsArea =  this.todo.get('dsArea').value;
  contract.cv =  null;
  contract.job = this.todo.get('job').value;
  contract.contract = null;
  contract.endOfContract = new Date(this.todo.get('endOfContract').value);
  contract.dsLastUserModifier =  this.userObejct.email;
  contract.lastModification =  null;
  contract.creationUser =  this.userObejct.email;
  contract.creationDate = null;
  contract.active = true;
  this.saveContratingData(contract);
}

saveContratingData(contratingData:ContratingDataTO){
  this.loading.setIsLoadingEvent(true);
 this.users.saveOrUpdateEmployeeContrating(contratingData).subscribe(data=>{
   if(data){
    this.msg.title = MSG_DIALOG.OK;
    this.msg.msg = MSG_DIALOG.MSG_SAVE;
     this.loading.setGeneralNotificationMessage(this.msg);
     
     this.loading.setIsLoadingEvent(false);
     setTimeout(()=>{
      this.navCtrl.push(MyAccountPage);
    },2100);

   }else{
    this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);  
    this.loading.setIsLoadingEvent(false);
  }
 },error=>{
   console.log(error);
  this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
  this.loading.setIsLoadingEvent(false);
 });
}


validateTypeUSers(){
  if(this.userObejct.userType === 'IN'){
    this.salaryEnable =  true;
    //this.jobEnable = true; 
  }
}


back(){
  this.navCtrl.pop();
}
}
