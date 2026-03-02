
import { Component, OnInit, OnDestroy, NgZone, ChangeDetectorRef } from '@angular/core';
import { EmployeeDataAssignmentTO,ClientTO,ProjectTO} from '../../../models/employee.model';
import { UserService } from '../../../services/user/user.service';
import { DataService } from '../../../services/data.service';
import { MSG, environment,EXPRESSION } from '../../../../environments/environment';
import { FormGroup, FormBuilder,Validators,FormControl } from '@angular/forms';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { UserTO } from '../../../models/user.model';
import { Router } from '@angular/router';
import { resultJob, data} from '../../../models/sico.model';
import { Location } from '@angular/common';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
@Component({
  selector: 'app-user-form-assignment-data',
  templateUrl: './user-form-assignment-data.component.html',
  styleUrls: ['./user-form-assignment-data.component.css','../../../../assets/custom.css']
})
export class UserFormAssignmentDataComponent implements OnInit {
  formInfoData: FormGroup;
  employeeAssignment: EmployeeDataAssignmentTO;
  userAdmin:UserTO;
  idUserEmployee:number;
  showSaveUpdate: boolean = true;
  datosSico: boolean = false;
  edithSico:boolean=false;
  clients:ClientTO[];
  projects:ProjectTO[];

  isDisableForm:boolean=false;

  constructor(
    private fb: FormBuilder,
    private _data: DataService,
    private _userService: UserService,
    private location: Location,
    private _localStorage: LocalStorageService,
    private router:Router,
    private cdRef: ChangeDetectorRef
  ) {

    this._userService
    .getAllClient()
    .subscribe((resp:ClientTO[]) => {
      this.clients=resp;
      console.log('clientes ui',resp);
    });

    this._userService.setTabLabel('datos-asignacion');
    this._userService.setActivePhotography(false);
    this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this.employeeAssignment = new EmployeeDataAssignmentTO(); 
    this.employeeAssignment.client="";
    this.employeeAssignment.project=" ";
    this.formInfoData = this.fb.group({
  employeePosition :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],  
  project:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
  client:[environment.EMPTY_INPUT],
	manager :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
	state:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
	city :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
	emailDirectBoss :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.EMAIL)]],
	telephoneDirectBoss :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER)]],
	startAssigment:[environment.EMPTY_INPUT],
	endAllocation :[environment.EMPTY_INPUT],
	allocationEmail :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.EMAIL)]],
	allocationSalary :[environment.EMPTY_INPUT,[]],
  evaluation :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER)]],  
  idProject:[environment.EMPTY_INPUT,[Validators.required,Validators.min(1)]],
  idClient:[environment.EMPTY_INPUT,[Validators.required,Validators.min(1)]],
   
    });

    //Desabilitacion de Formulario (Usuario Inactivo)
    this.isDisableForm = this._userService.getIsDisable();
    this.isDisableForm ? this.formInfoData.disable() : this.formInfoData.enable();    

    if(!_userService.getFormEnable())
    {
      this.formInfoData.disable();
    }

    this.idUserEmployee = _userService.getIdUserCurrently();
    this._userService.getTabs(this.idUserEmployee)
    .subscribe(resp => {  
     this._userService.setConvertRespToTabIcon(resp);
    });





    this._localStorage
    .getItem("answerLogin","user")
    .subscribe(user => {
      this.userAdmin=user;
    });


    if(_userService.getDatosSico())
    {
      this.datosSico=true;
      this.fieldsReadonly()
    }else
    {
      this.formInfoData.controls["idProject"].setValidators(Validators.maxLength(50));
      this.formInfoData.controls["idProject"].updateValueAndValidity();
      this.formInfoData.controls["idClient"].setValidators(Validators.maxLength(50));
      this.formInfoData.controls["idClient"].updateValueAndValidity();
      this.employeeAssignment.idClient=0;
      this.employeeAssignment.idProject=0;
    }


    if(this.idUserEmployee)
    {
      this._data.setIsLoadingEvent(true);
    this._userService.getAsignation(this.idUserEmployee)
    .subscribe((resp:EmployeeDataAssignmentTO)=> {  
      console.log('pinta resul',resp) ;
      this._data.setIsLoadingEvent(false);
      if(resp.idClient!=null )
      {
        this.selectClient(resp.idClient);
      }

      this.employeeAssignment=resp;

    }, err => {
      this._data.setIsLoadingEvent(false);
      if(err.error.message =='No value present')
      {

        if(this.datosSico)
        {
          this.fieldsReadonly();
          var params=_userService.getParamSico();
          this._data.setIsLoadingEvent(true);
          this._userService.getWorkInfoSico(params.curp,params.name)
                  .subscribe((resp:resultJob)=> {
                    this._data.setIsLoadingEvent(false);
                    if(resp.total>0)
                    {
                      if(resp.total>0)
                      {
                        this.edithSico=true;
                         this.employeeAssignment.employeePosition=resp.data.personal.puesto;
                         this.employeeAssignment.project=resp.data.pago[0].nombreProyecto
                         this.employeeAssignment.client=resp.data.pago[0].nombreCliente;
                      }
                    }
                  },err=>this._data.setIsLoadingEvent(false));
        }

      }
      else
      {
        this._data.setGeneralNotificationMessage( err.error.message );
        this._data.setIsLoadingEvent(false);
      }

       });
    
  }
 
   }

  ngOnInit() {

  }



  save()
  {
    this._data.setIsLoadingEvent(true);
    this.employeeAssignment.idUser=this.idUserEmployee;
    if(this.employeeAssignment.idDataAssigment==null || this.employeeAssignment.idDataAssigment>1)
    {
      this.employeeAssignment.creationUser=this.userAdmin.email;
    }

    this.employeeAssignment.lastUserModifier=this.userAdmin.email;
    this._userService.saveOrUpdateEmployeeAssignment(this.employeeAssignment)
    .subscribe( response => {
        this._data.setIsLoadingEvent(false);
        if(response){
                      this._data.setGeneralNotificationMessage(MSG.OK);
                      this.router.navigate([`home/admin-usuario/datos-personales`]);

        }else {
          this._data.setGeneralNotificationMessage("No se realizo ningun registro");

        }
    }, err => {
        this._data.setIsLoadingEvent(false);
        this._data.setGeneralNotificationMessage( err.error.message );
        console.log(err);

    });
  }
  fieldsReadonly()
  {
    this.formInfoData.get('employeePosition').disable();
    this.formInfoData.get('client').disable();
    this.formInfoData.get('project').disable();
}

selectClient(clientId:any)
{
if(!isNaN(clientId))
{
  this.employeeAssignment.idProject=null;
  this._data.setIsLoadingEvent(true);
  this._userService
  .getProyectByIdClient(clientId)
  .subscribe((resp:ProjectTO[]) => {
    this._data.setIsLoadingEvent(false);
    this.projects=resp;  
  },err=> {this._data.setIsLoadingEvent(false);});
}
 

}

selectProject(project:any)
{
console.log('select project ',project)
}

onBrokenChange(n: string) {
  var num = n.replace(/[$,]/g, "");
  return Number(num);  
}

}
