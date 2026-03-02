import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { FormBuilder, FormGroup, Validators } from '../../../node_modules/@angular/forms';
import { EmployeeDomicileTO, EmployeeTO } from '../../models/employee.compl';
import { UsersProvider } from '../../providers/users/users';
import { UserTO } from '../../models/user.model';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, MSG_DIALOG, EXPRESSION } from '../../environments/environments';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { MessageGeneral } from '../../iterface/create-account.interface';

/**
 * Generated class for the AddressPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

 @Component({
   selector: 'page-address',
   templateUrl: 'address.html',
 })
 export class AddressPage {
   private todo : FormGroup;
   enables: any[] = [];
   userObejct:UserTO;
   domicileUpdate:EmployeeDomicileTO;
   msg = new MessageGeneral();
   constructor(public navCtrl: NavController, 
     public loading:EventsManagerProvider,public storage:StorageProvider, public navParams: NavParams,private users: UsersProvider,private formBuilder: FormBuilder) {

   }

   ionViewWillEnter(){

     this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
     this.blockSocoInput(
       false, // calle
       false, // num ext
       false, // num int
       false, // colonia
       false, // municipip
       false, // estado
       false); // cp

     this.todo = this.formBuilder.group({
       calle: ['', [Validators.maxLength(50),Validators.minLength(1)]],
       numeroExterior: ['', [Validators.maxLength(30),Validators.minLength(1)]],
       numeroInterior: ['', [Validators.maxLength(30),Validators.minLength(1)]],
       colonia: ['', [Validators.maxLength(50),Validators.minLength(1)]],
       municipio: ['',[Validators.maxLength(50),Validators.minLength(1)]],
       estado: ['', [Validators.maxLength(50),Validators.minLength(1)]],
       cp: ['', [Validators.maxLength(5),Validators.minLength(5),Validators.minLength(5),Validators.pattern(EXPRESSION.NUMBER)]]
     });
     this.getFormDomicile();
   }

   saveAddresRHTotal(){

     if(!this.todo.valid){
       this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.DATA_ERROR);
       return;
     }

     this.users.getEmployeeByIdUser(this.userObejct.id).subscribe((data:EmployeeTO) =>{
       if(this.domicileUpdate != null){

         this.domicileUpdate.interiorNumber =this.todo.get('numeroInterior').value;
         this.domicileUpdate.outDoorNumber =this.todo.get('numeroExterior').value;
         this.domicileUpdate.state =this.todo.get('estado').value;
         this.domicileUpdate.city=this.todo.get('municipio').value;
         this.domicileUpdate.employee = data;
         this.domicileUpdate.postalCode=this.todo.get('cp').value;
         this.domicileUpdate.colony=this.todo.get('colonia').value;
         this.domicileUpdate.street=this.todo.get('calle').value;
         this.domicileUpdate.lastUserModifier = this.userObejct.email;
         this.domicileUpdate.creationUser =this.userObejct.email;
         this.saveDireccion(this.domicileUpdate);
         return;
       }
       let domicile = new EmployeeDomicileTO();
       domicile.interiorNumber =this.todo.get('numeroInterior').value;
       domicile.outDoorNumber = this.todo.get('numeroExterior').value;
       domicile.state =this.todo.get('estado').value;
       domicile.city=this.todo.get('municipio').value;
       domicile.postalCode=this.todo.get('cp').value;
       domicile.colony=this.todo.get('colonia').value;
       domicile.street=this.todo.get('calle').value;
       domicile.lastUserModifier = this.userObejct.email;
       domicile.lastModification = null;
       domicile.employee = data;
       domicile.creationUser =this.userObejct.email;
       domicile.creationDate = null;
       domicile.active  =  true;
       this.saveDireccion(domicile);
     });
   }




   saveDireccion(domicile:EmployeeDomicileTO){

     if(!this.todo.valid){
       this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.DATA_ERROR);
       return;
     }

     this.loading.setIsLoadingEvent(true);
     this.users.saveDireccion(domicile).subscribe(data=>{
       if(data){
         this.loading.setIsLoadingEvent(false);
         this.users.setTabsEvent({tab1:true,tab2:true});
         this.msg.title = MSG_DIALOG.OK;
         this.msg.msg = MSG_DIALOG.MSG_SAVE;
         this.loading.setGeneralNotificationMessage(this.msg);
         setTimeout(()=>{
           this.navCtrl.parent.select(2);
         },2100);

       }else{
         this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
         this.loading.setIsLoadingEvent(false);
       }
     },error=>{
       this.loading.setIsLoadingEvent(false);
       this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
     });
   }


   blockSocoInput(calle:boolean,numeroExterior:boolean,numeroInterior:boolean,colonia:boolean,municipio:boolean,estado:boolean,cp:boolean) {
     this.enables = [];
     this.enables.push({
       calle:calle,
       numeroExterior:numeroExterior,
       numeroInterior:numeroInterior,
       colonia:colonia,
       municipio:municipio,
       estado:estado,
       cp:cp
     });
   }


   getFormDomicile(){
     this.loading.setIsLoadingEvent(true);
     this.users.getAddress(this.userObejct.id).subscribe((add:EmployeeDomicileTO) =>{

       this.domicileUpdate = add;
       if(add.employee.user.userType === 'IN'){
         this.todo.controls['calle'].setValue(add.street);
         this.todo.controls['numeroExterior'].setValue(add.interiorNumber);
         this.todo.controls['numeroInterior'].setValue(add.outDoorNumber);
         this.todo.controls['colonia'].setValue(add.colony);
         this.todo.controls['municipio'].setValue(add.city);
         this.todo.controls['estado'].setValue(add.state);
         this.todo.controls['cp'].setValue(add.postalCode);
         this.blockSocoInput(
           false, // calle
           false, // num ext
           false, // num int
           false, // colonia
           false, // municipip
           false, // estado
           false); // cp
         //this.blockSocoInput(true,true,true,true,true,true,true);
         this.loading.setIsLoadingEvent(false);
         return;
       }
       this.todo.controls['calle'].setValue(add.street);
       this.todo.controls['numeroExterior'].setValue(add.interiorNumber);
       this.todo.controls['numeroInterior'].setValue(add.outDoorNumber);
       this.todo.controls['colonia'].setValue(add.colony);
       this.todo.controls['municipio'].setValue(add.city);
       this.todo.controls['estado'].setValue(add.state);
       this.todo.controls['cp'].setValue(add.postalCode);
       this.blockSocoInput(false,false,false,false,false,false,false);
       this.loading.setIsLoadingEvent(false);
     },error=>{
       this.loading.setIsLoadingEvent(false);
       console.log(error);
     });
   }
   
 }
