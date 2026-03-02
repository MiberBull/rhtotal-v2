import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '../../../node_modules/@angular/forms';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { UsersProvider } from '../../providers/users/users';
import { UserTO, EmployeeHitoryTO } from '../../models/user.model';
import { StorageProvider } from '../../providers/storage/storage';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { MyAccountPage } from '../my-account/my-account';

/**
 * Generated class for the LastJobPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

 @Component({
   selector: 'page-last-job',
   templateUrl: 'last-job.html',
 })
 export class LastJobPage {
   userObejct: UserTO;
   msg = new MessageGeneral();
   updateHistory:EmployeeHitoryTO =  null;
   private todo: FormGroup;
   endDateEmpleo:boolean = true;
   private minDate:any;
   constructor(public navCtrl: NavController,public users: UsersProvider,public loading:EventsManagerProvider,public storage: StorageProvider,private formBuilder: FormBuilder, public navParams: NavParams) {
     this.loading.setIsLoadingEvent(true);
     this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
     this.todo = this.formBuilder.group({
       entryDate:[''],
       endDate:[''],
       qtSalary :['',[Validators.minLength(1)]],
       benefitsLaw: [null],
       aditionalBenefits : [null],
       dsCompany:['',Validators.maxLength(50)],
       dsEmployeePosition:['',Validators.maxLength(50)],
       dsIndustry :['',Validators.maxLength(50)],
       dsArea :['',Validators.maxLength(50)],
       qtDependets :['',Validators.maxLength(4)]
     });
     this.getInfoLastJob();
   }

   
   getInfoLastJob(){
     this.users.getHistory(this.userObejct.id).subscribe((data:EmployeeHitoryTO) =>{
       
       if(data.idEmployeeHis != null && data.idEmployeeHis > 0){
         this.updateHistory = data;
         this.loadInfoLastJob(data);
         this.loading.setIsLoadingEvent(false);
       }else{
         this.loading.setIsLoadingEvent(false);
       }
     },error=>{
       console.log(error);
       this.loading.setIsLoadingEvent(false);
     });
   }

   getInfoInit(){
     if(this.updateHistory != null){
       this.updateHistory.entryDate = this.todo.get('entryDate').value,
       this.updateHistory.endDate = this.todo.get('endDate').value,
       this.updateHistory.qtSalary = this.todo.get('qtSalary').value,
       this.updateHistory.benefitsLaw = this.getValueNumber(this.todo.get('benefitsLaw').value) ,
       this.updateHistory.aditionalBenefits= this.getValueNumber(this.todo.get('aditionalBenefits').value),
       this.updateHistory.dsCompany= this.todo.get('dsCompany').value,
       this.updateHistory.dsEmployeePosition= this.todo.get('dsEmployeePosition').value,
       this.updateHistory.dsIndustry = this.todo.get('dsIndustry').value,
       this.updateHistory.dsArea = this.todo.get('dsArea').value,
       this.updateHistory.qtDependets= this.todo.get('qtDependets').value;
       this.saveInfoLastJob(this.updateHistory);
       return;
     }
     
     let saveHistotry = new EmployeeHitoryTO();
     saveHistotry.entryDate = this.todo.get('entryDate').value,
     saveHistotry.endDate = this.todo.get('endDate').value,
     saveHistotry.qtSalary = this.todo.get('qtSalary').value,
     saveHistotry.benefitsLaw = this.getValueNumber(this.todo.get('benefitsLaw').value),
     saveHistotry.aditionalBenefits= this.getValueNumber( this.todo.get('aditionalBenefits').value),
     saveHistotry.dsCompany= this.todo.get('dsCompany').value,
     saveHistotry.dsEmployeePosition= this.todo.get('dsEmployeePosition').value,
     saveHistotry.dsIndustry = this.todo.get('dsIndustry').value,
     saveHistotry.dsArea = this.todo.get('dsArea').value,
     saveHistotry.qtDependets= this.todo.get('qtDependets').value;
     saveHistotry.idEmployeeHis = 0;
     saveHistotry.idUser = this.userObejct.id;
     saveHistotry.lastUserModifier = this.userObejct.email;
     saveHistotry.lastModification = null;
     saveHistotry.creationUser = this.userObejct.email;
     saveHistotry.creationDate = null;
     saveHistotry.active = true;
     this.saveInfoLastJob(saveHistotry);

   }


   saveInfoLastJob(history:EmployeeHitoryTO,){

     this.loading.setIsLoadingEvent(true);
     this.users.saveOrUpdateEmployeeHistory(history).subscribe(data => {
       if (data) {
         this.msg.title = MSG_DIALOG.OK;
         this.msg.msg = MSG_DIALOG.MSG_SAVE;
         this.loading.setGeneralNotificationMessage(this.msg);
         
         this.loading.setIsLoadingEvent(false);
         setTimeout(()=>{
           this.navCtrl.push(MyAccountPage);
         },2100);
       } else {
         this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
         this.loading.setIsLoadingEvent(false);
       }
     }, error => {
       console.log(error);
       this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
       this.loading.setIsLoadingEvent(false);
     });
   }


   loadInfoLastJob(historyData:EmployeeHitoryTO){
     for (const key in this.todo.controls) {
       for (const prop in historyData) {
         if(prop === 'qtSalary'  && key ==='qtSalary'){
           let value :any = historyData[prop];
           this.todo.controls[key].setValue(value === 0 ? '': historyData[prop]);
         }else if(prop === 'qtDependets'  && key ==='qtDependets'){
           let value :any = historyData[prop];
           this.todo.controls[key].setValue(value === 0 ? '': historyData[prop]);
         }else{
           if (key === prop) {
             this.todo.controls[key].setValue(this.setValueNumber(historyData[prop]));
           }
         }
         
       }
     }
     this.disableInitDate(this.todo.get('entryDate').value)
     this.loading.setIsLoadingEvent(false);
   }



   getValueNumber(value): string {
     if (value === null) {
       return "1";
     }
     let element = value.toString();
     if (element === "false") {
       return "0";
     }

     if (element === "true") {
       return "2";
     }
     return element;
   }

   setValueNumber(value: any): any {
     if (value === '0') {
       return false;
     }
     if (value === '1') {
       return null;
     }
     if (value === '2') {
       return true;
     }
     return value;
   }

   back(){
     this.navCtrl.pop();
   }

   onChange(){
     this.minDate = new Date(this.todo.get('entryDate').value).toISOString();
     this.endDateEmpleo = false;
     console.log(this.minDate); // Revisión de valor
   }


   disableInitDate(dateInit:string){
     if(dateInit != '' && dateInit != null){
       this.minDate = new Date(dateInit).toISOString();
       this.endDateEmpleo = false;
       console.log(this.minDate); // Revisión de valor
     } 
   }
 }
