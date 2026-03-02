import { Component, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DECLARATION, BREADCRUMB, BUTTON, MSG,routesWeb, EXPRESSION, environment } from '../../../environments/environment';
import { NotificationTO } from '../../models/notification.model';
import { Information } from '../../util/date';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { NotificationService } from '../../services/notification/notification.service';
import { MatDialogConfig, MatDialog, MatChipInputEvent } from '@angular/material';
import { DialogConfirmComponent } from '../../components/dialog-confirm/dialog-confirm.component';
import { DataService } from '../../services/data.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common'
import { TreeService } from '../../services/tree/tree.service';


const dialogConfig = new MatDialogConfig();

@Component({
  selector: 'app-notifications-admin',
  templateUrl: './notificationsadmin.component.html',
  styleUrls: ['./notifications-admin.component.css','../../../assets/custom.css']
})
export class NotificationsAdminComponent implements OnInit,OnDestroy {


  jsonTreeNotification:any={};

  minDate = new Date();
  confirm:boolean = false;
  count = 0;
  jsonTree:EventEmitter<any> = new EventEmitter();
  disabledTree:boolean;

  notificationFrom: FormGroup;
  statusOption = [
    { id:'A',status:'Activo'},
    { id:'I',status:'Inactivo'}
  ];

  editButton:string = BUTTON.EDIT;
  saveButton:string = BUTTON.SAVE;
  enableButton:boolean = false;
  disabledButton:boolean = false;
  id:string;
  notification:NotificationTO;

  updateStatus:boolean;

  showSaveUpdate: boolean = true;

  showEditNotification:boolean = true;

  constructor(
    private router:Router,
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _notificationS: NotificationService,
    private _dataS: DataService,
    private location: Location,
    private _treeService:TreeService,
    private dialog: MatDialog,
    private _localStorage: LocalStorageService,
    private _fb: FormBuilder) {

    this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;

    this.notificationFrom =  this._fb.group({
      title:[DECLARATION.EMPTY_INPUT,[Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      startDate:[DECLARATION.EMPTY_INPUT,[Validators.required]],
      notificationTime:[DECLARATION.EMPTY_INPUT,[Validators.required]],
      notificationText:[DECLARATION.EMPTY_INPUT,[Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      notificationTextLarge:[DECLARATION.EMPTY_INPUT,[Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      internalComments:[DECLARATION.EMPTY_INPUT,[Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      status:[DECLARATION.EMPTY_INPUT],
      creationUser:[DECLARATION.EMPTY_INPUT],
      notificationFrom:[DECLARATION.EMPTY_INPUT]
    })

    this._toolbar.setVisible( this.router.url.toString() );

    this.notification = this._notificationS.getNotification();
    
    
    if ( this.notification ) {
      this.notificationFrom.disable();
      this._notificationS
          .queryNotificationById( this.notification.idNotification )
          .subscribe( (resp:NotificationTO) => {
            
            this.showEditNotification = resp.status == 'E' ? false : true;
            let a = resp.status == 'A' ? 'Programadas' : 'Enviadas'
            
            this._breadcrumb.setRouteText({title:`${BREADCRUMB.DETAIL_NOTIFICATION} | ${a}`,arrow:true});
            this.setValuesInForm(resp);
            
          });
    } else {
      this.disabledTree = false;
      this._breadcrumb.setRouteText({title:BREADCRUMB.NEW_NOTIFICATION,arrow:true});
    }

     let idNotification = this.notification ? this.notification.idNotification : '0';
     this._treeService.getAllTree(  idNotification,'N').subscribe( treeJson => {
              this.jsonTreeNotification=treeJson;
     });

  }

  ngOnInit() {
  }

  setValuesInForm( values:NotificationTO ){
    this.disabledTree = true;
    values.startDate = Information.getDateForDateTimePicker( values.startDate );
    values.creationUser=values.lastUserModifier;
    this.notificationFrom.patchValue( values );
  }

  onSubmit() {

    if( this.notification ) {
      let status = this.notificationFrom.get('status').value=='A'?'Activo':'Inactivo';
      if (this.notification.status != status ) { 
        let label = status == 'Activo' ? 'activar' : 'inactivar';
        dialogConfig.data = {title:`¿Está seguro de ${label} la Notificación?`,success:'UPDATE'};
        dialogConfig.width = '250px;';
        let dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
        dialogRef.afterClosed().subscribe( (resp:any) => {
          if( resp == 'UPDATE' ){
            this.update(true);
          }
        });
        
      }
      else{
        this.update(false);
      }
    } else {
      this.save();
    }

  }
  
  update(updateStatus:boolean) {

    this.notification.title = this.notificationFrom.get('title').value;
    this.notification.startDate = this.notificationFrom.get('startDate').value;
    this.notification.notificationTime = this.notificationFrom.get('notificationTime').value;
    this.notification.notificationText = this.notificationFrom.get('notificationText').value;
    this.notification.notificationText = this.notificationFrom.get('notificationText').value;
    this.notification.notificationTextLarge = this.notificationFrom.get('notificationTextLarge').value;
    this.notification.internalComments = this.notificationFrom.get('internalComments').value;
    this.notification.status = this.notificationFrom.get('status').value;
    this.notification.creationUser = this.notification.creationUser;
    this.notification.lastUserModifier = this._localStorage.getUser();

    this.saveOrUpdateService( this.notification, updateStatus );
  }

  save() {
    let informationForm = this.notificationFrom.getRawValue();

    let a = new Date(informationForm.startDate);
    let dateConvert = new Date(`${(a.getMonth()+1)}-${a.getDate()}-${a.getFullYear()} ${informationForm.notificationTime}:00`);
    let currentDate = Information.getCurrentDate();

    if( dateConvert < currentDate ) {
      this._dataS.setGeneralNotificationMessage('La hora seleccionada no es válida');
      return;
    }

    let newNotification = new NotificationTO();
      newNotification.idNotification = null;
      newNotification.title = informationForm.title;
      newNotification.startDate = informationForm.startDate;
      newNotification.notificationTime = informationForm.notificationTime;
      newNotification.notificationText = informationForm.notificationText;
      newNotification.notificationTextLarge = informationForm.notificationTextLarge;
      newNotification.internalComments = informationForm.internalComments;
      newNotification.status = 'A';
      newNotification.lastUserModifier = this._localStorage.getUser();
      newNotification.lastModification = Information.getCurrentDate();
      newNotification.creationDate = Information.getCurrentDate();
      newNotification.endDate = Information.getCurrentDate();
      newNotification.creationUser = this._localStorage.getUser();
      this.saveOrUpdateService( newNotification, false );
  }



  saveOrUpdateService( notification:NotificationTO, updateStatus:boolean ){
    this._dataS.setIsLoadingEvent(true);
    this._notificationS
        .newNotification( notification,this.jsonTreeNotification )
        .subscribe( resp => {
          if( resp  ) {
            this._dataS.setIsLoadingEvent(false);
            if(!updateStatus){
              this._dataS.setGeneralNotificationMessage( MSG.OK );
            }else{
              this._dataS.setGeneralNotificationMessage( 'Se ha modificado el estatus de la Notificación' );
            }

            setTimeout(() => {
              this.router.navigate([`${routesWeb.HOME}/${routesWeb.NOTIFICATION}`]);
            }, 3900);
          }
        }, err => {
          this._dataS.setGeneralNotificationMessage( err.error.message );
          this._dataS.setIsLoadingEvent(false);
        }, () => {this.disabledButton = true;
          this._dataS.setIsLoadingEvent(false);});
  }
  
  editForm() {
    if( this.notification.status == 'E' ){
      dialogConfig.data = {title:`Lo sentimos una notificación enviada no puede ser actualizada`,success:null};
      dialogConfig.width = '250px;';
      this.dialog.open( DialogConfirmComponent,dialogConfig );
      return;
    }
    this.disabledTree = false;
    this.notificationFrom.enable();
    this.enableButton = true;
    this.notificationFrom.get('creationUser').disable();
  }

  saveJson(event) {
    if( this.count > 0 ) {
      this.jsonTree = event;

    }
    this.count += 1;
  }

  cancel() {
    this.location.back();
  }

  ngOnDestroy() {
    this._notificationS.setNotifitacion( null );
  }



}
