import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, Content, AlertController } from 'ionic-angular';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { Jobs } from '../../models/job.model';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { UsersProvider } from '../../providers/users/users';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { EXPRESSION, MSG_DIALOG, KEYS_STORAGE } from '../../environments/environments';
import { StorageProvider } from '../../providers/storage/storage';
import { HomePage } from '../home/home';
import { ConsumeApiProvider } from '../../providers/consume-api/consume-api';

/**
 * Generated class for the PositionHistoryPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-position-history',
  templateUrl: 'position-history.html',
})
export class PositionHistoryPage {

  @ViewChild(Content) content: Content;

  user:any;
  jobs:any[] = [];
  disabledSeondDates:boolean[] = [];
  minDates:any[] = [];
  idDelte:any[] = [];
  disabledBtnDelete:boolean = false;

  formInfo: FormArray;
  dateService:string;  
  jobInfoForm:FormGroup;
  jobsInfoForm = this.fb.group({
    formInfoGroups: this.fb.array([])
  });
  newForm:boolean = false;

  propiertiesForm = {
    idJobHistory:[''],
    employeePosition:['',Validators.compose([Validators.maxLength(50),Validators.required])],
    company:['',Validators.compose([Validators.minLength(1),Validators.maxLength(50),Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)])],
    bossName:['',[Validators.maxLength(50),Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
    bossEmail:['',[Validators.maxLength(50),Validators.pattern(EXPRESSION.EMAIL)]],
    bossTelephone:['',[Validators.minLength(10),Validators.pattern(EXPRESSION.ONLY_NUMBERS)]],
    assigmentDtartDate:['',[Validators.required]],
    assigmentEndDate:['',[Validators.required]],
    qtSalary:['',Validators.compose([Validators.max(999999999.99),Validators.minLength(1)])],
    assignmentEmail:['',[Validators.maxLength(50),Validators.pattern(EXPRESSION.EMAIL)]],
    professionalResume:['',Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)],
  };

  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams,
    private fb: FormBuilder,
    private evente_manager: EventsManagerProvider,
    private user_provider: UsersProvider,
    private storage_provider: StorageProvider,
    private consume_api: ConsumeApiProvider,
    private alertCtrl: AlertController) {
    this.user = this.storage_provider.getUser();
  }

  ionViewDidLoad() {
    this.evente_manager.setIsLoadingEvent(true);
    this.consume_api.getDateTimeService().subscribe( date => {
      this.dateService = new Date(date.dateTime).toISOString();
    },error => {
      console.log(error);
      this.evente_manager.setIsLoadingEvent(false);
      this.showAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
    },() => this.loadJobs());
  }

  back() {
    this.navCtrl.setRoot( HomePage );
  }

  save() {
    this.evente_manager.setIsLoadingEvent(true);
    let forms = this.jobsInfoForm.getRawValue();
    if( !this.hashErrorRequireds() ){
      if( this.formInfoGroups.valid ){
        if( this.jobs.length > 0 ){

          let forms = this.jobsInfoForm.getRawValue();
          this.jobs.forEach( (item,index) => {
            item.employeePosition = forms.formInfoGroups[index].employeePosition;
            item.company = forms.formInfoGroups[index].company;
            item.bossName = forms.formInfoGroups[index].bossName;
            item.bossEmail = forms.formInfoGroups[index].bossEmail;
            item.bossTelephone = forms.formInfoGroups[index].bossTelephone;
            item.assigmentDtartDate = new Date(forms.formInfoGroups[index].assigmentDtartDate);
            item.assigmentEndDate = new Date(forms.formInfoGroups[index].assigmentEndDate);
            item.qtSalary = Number.parseFloat(forms.formInfoGroups[index].qtSalary);
            item.assignmentEmail = forms.formInfoGroups[index].assignmentEmail;
            item.professionalResume = forms.formInfoGroups[index].professionalResume;
          });
  
          forms.formInfoGroups.forEach( (item:any,index) => {
            if( (index+1) > this.jobs.length  ) {
              let job:Jobs = new Jobs(this.storage_provider);
              job.employeePosition = item.employeePosition;
              job.company = item.company;
              job.bossName = item.bossName;
              job.bossEmail = item.bossEmail;
              job.bossTelephone = item.bossTelephone;
              job.assigmentDtartDate = new Date(item.assigmentDtartDate);
              job.assigmentEndDate = new Date(item.assigmentEndDate);
              job.qtSalary = Number.parseFloat(item.qtSalary);
              job.assignmentEmail = item.assignmentEmail;
              job.professionalResume = item.professionalResume;
              this.jobs.push( job );
            }              
          });
    
          this.user_provider.saveJobs( this.jobs )
              .subscribe( () => {
                this.showAlert( MSG_DIALOG.OK,MSG_DIALOG.MSG_SAVE );
              },error => {
                this.evente_manager.setIsLoadingEvent(false);
                this.showAlert( MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE );
              }, () => {
                this.loadJobs(true);
              });
    
        }else{
        let jobs:any[] = [];
        forms.formInfoGroups.forEach( (item:any) => {
          let job:Jobs = new Jobs(this.storage_provider);
          job.employeePosition = item.employeePosition;
          job.company = item.company;
          job.bossName = item.bossName;
          job.bossEmail = item.bossEmail;
          job.bossTelephone = item.bossTelephone;
          job.assigmentDtartDate = new Date(item.assigmentDtartDate);
          job.assigmentEndDate = new Date(item.assigmentEndDate);
          job.qtSalary = Number.parseFloat(item.qtSalary);
          job.assignmentEmail = item.assignmentEmail;
          job.professionalResume = item.professionalResume;
          jobs.push( job );          
        });
        this.user_provider.saveJobs( jobs )
            .subscribe( () => {
              this.showAlert( MSG_DIALOG.OK,MSG_DIALOG.MSG_SAVE );
            },error => {
              this.evente_manager.setIsLoadingEvent(false);
              this.showAlert( MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE );
            }, () => this.loadJobs(true));
        }
      }else{
        this.showAlert( MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.DATA_ERROR );
        this.evente_manager.setIsLoadingEvent(false);
      }
    } else {
      this.evente_manager.setIsLoadingEvent(false);
      this.evente_manager.setIsRequired(true);
    }
  }

  get formInfoGroups() {
    return this.jobsInfoForm.get("formInfoGroups") as FormArray;
  }

  addForm() {
    if( !this.hashErrorRequireds() ) {
      this.newForm = true;
      this.disabledSeondDates.push(true);
      this.formInfoGroups.push( this.fb.group(this.propiertiesForm) );
      setTimeout( () => {
        this.content.scrollToBottom();
      },500)
    }
  }

  hashErrorRequireds() {
    var errorRequired:boolean = false;
    let forms:any = this.jobsInfoForm.controls.formInfoGroups;
    forms.controls.forEach( (form:any) => {
      errorRequired = ( form.controls["employeePosition"].hasError('required') ||
                            form.controls["company"].hasError('required') ||
                            form.controls["assigmentDtartDate"].hasError('required') ||
                            form.controls["assigmentEndDate"].hasError('required') );
      if( errorRequired ) {
        return errorRequired;
      }
    });
    return errorRequired;
  }

  showAlert( title:string,message:string ) {
    let msg = new MessageGeneral();
    msg.msg = message;
    msg.title = title;
    this.evente_manager.setGeneralNotificationMessage(msg);
  }

  cancelForm() {
    this.navCtrl.setRoot( HomePage );
  }

  loadJobs(loadJobs=false) {

    if( this.storage_provider.getItem(KEYS_STORAGE.JOBS) && !loadJobs ) {
      this.setValuesForm(this.storage_provider.getItem(KEYS_STORAGE.JOBS));
      this.evente_manager.setIsLoadingEvent(false);
      return;
    }

    if( loadJobs ) {
      this.formInfoGroups.controls = [];
    }
    
    this.user_provider.getJobsByIdUser(this.user.idUser)
        .subscribe( (resp:any[]) => {
          this.storage_provider.saveItem( KEYS_STORAGE.JOBS,resp );
          this.setValuesForm(resp);
        },error => {
          this.disabledSeondDates.push(true)
          this.formInfoGroups.push( this.fb.group(this.propiertiesForm) );
          this.evente_manager.setIsLoadingEvent(false);
          this.showAlert( MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE );
        }, () => this.evente_manager.setIsLoadingEvent(false));
  }

  setValuesForm(resp:any) {
    try {
      this.disabledSeondDates = [];
      this.jobs = resp;
      if( resp.length == 0 ) {
        this.disabledSeondDates.push(true);
        this.formInfoGroups.push( this.fb.group(this.propiertiesForm) );
      } else {
        resp.forEach( (data,index) => {
          this.formInfoGroups.push( this.fb.group(this.propiertiesForm) );
          let startDate = new Date(data.assigmentDtartDate).toISOString().substring(0, 10);
          let endDate = new Date(data.assigmentEndDate).toISOString().substring(0, 10);
          this.disabledSeondDates[index] = false;
          this.minDates[index] = new Date(data.assigmentDtartDate).toISOString();
          this.formInfoGroups.controls[index].get("idJobHistory").setValue(data.idJobHistory);
          this.formInfoGroups.controls[index].get("employeePosition").setValue(data.employeePosition);
          this.formInfoGroups.controls[index].get("company").setValue(data.company);
          this.formInfoGroups.controls[index].get("bossName").setValue(data.bossName);
          this.formInfoGroups.controls[index].get("bossEmail").setValue(data.bossEmail);
          this.formInfoGroups.controls[index].get("bossTelephone").setValue(data.bossTelephone);
          this.formInfoGroups.controls[index].get("assigmentDtartDate").setValue(startDate);
          this.formInfoGroups.controls[index].get("assigmentEndDate").setValue(endDate);
          this.formInfoGroups.controls[index].get("qtSalary").setValue(data.qtSalary);
          this.formInfoGroups.controls[index].get("assignmentEmail").setValue(data.assignmentEmail);
          this.formInfoGroups.controls[index].get("professionalResume").setValue(data.professionalResume);
        });
      }
    } catch (errro) {
      this.loadJobs(true);
    }
  }

  deleteForm($event) {
    if( $event.form.controls.idJobHistory.value != null && $event.form.controls.idJobHistory.value != '' ) {
      if( $event.check ) {
        if( !this.idDelte[$event.form.controls.idJobHistory.value] ) {
          this.idDelte.push($event.form.controls.idJobHistory.value);
        }
      } else {
        this.idDelte = this.idDelte.filter( item => item != $event.form.controls.idJobHistory.value );
      }
    } else {
      this.disabledBtnDelete = $event.check;
    }

  }

  clickDelete() {
    this.showDeleteConfirm();
  }

  deleteJobService() {
    if( this.idDelte.length > 0 ) {
      this.evente_manager.setIsLoadingEvent(true);
      let idsForElemete:any[] = [];
      this.idDelte.forEach( item => {
        let job:any = {};
        job.idJob = item;
        idsForElemete.push(job);
      }); 
      this.user_provider
          .deleteJobsForUser(idsForElemete)
          .subscribe( () => {
            localStorage.removeItem( KEYS_STORAGE.JOBS );
            this.idDelte = [];
            this.loadJobs(true);
            this.showGeneralNotification( MSG_DIALOG.OK,MSG_DIALOG.DELETE_INFO );
          },error => {
            this.evente_manager.setIsLoadingEvent(false);
            this.showGeneralNotification( MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE );
          });
    } else if( this.newForm ) {
      this.formInfoGroups.removeAt( (this.formInfoGroups.length - 1) );
    }
  }

  showDeleteConfirm() {
    const alert = this.alertCtrl.create({
      title: '¿Desea eliminar el(los) puesto(s)?',
      buttons: [
        {
          text:'NO'
        },
        {
          text: 'SI',
          handler: () => {
            this.deleteJobService();
          }
        }
      ],
      cssClass:'center-actions'
    });
    alert.present();
  }
      
  showGeneralNotification(title,message) {
    let msg = new MessageGeneral();
    msg.title = title;
    msg.msg = message;
    this.evente_manager.setGeneralNotificationMessage(msg);
  }



}
